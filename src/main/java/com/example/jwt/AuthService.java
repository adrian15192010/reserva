package com.example.jwt;


import com.example.mail.IEmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TaskExecutor taskExecutor;
    private final IEmailService emailService;



    public String register(final RegisterRequest request) {
        final User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .habilitado(false)
                .role(Role.USER)
                .build();

        final User savedUser = repository.save(user);
        final String jwtToken = jwtService.generateToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);

        taskExecutor.execute(()->{

            String email[] = new String[1];
            email[0] = request.email();
            emailService.sendEmail(email, "Verificacion", "http://localhost:8080/html/habilitate?jwt="+jwtToken);

        });

        return "Hemos enviado un enlace de verificacion a tu correo";
    }

    public ResponseEntity<?> authenticate(final AuthRequest request) {


            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );


        final User user = repository.findByEmail(request.email())
                .orElseThrow();
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        if (!user.getHabilitado()){

            taskExecutor.execute(()->{

                String email[] = new String[1];
                email[0] = request.email();
                emailService.sendEmail(email, "Verificacion login", "http://localhost:8080/html/habilitate?jwt="+accessToken);

            });

            return ResponseEntity.ok(Map.of("message", "inhabilitado, Hemos enviado un enlace de verificacion a tu correo"));
            //throw new RuntimeException("inhabilitado, Hemos enviado un enlace de verificacion a tu correo");
        }

        return ResponseEntity.ok( new TokenResponse(accessToken, refreshToken));
    }

    private void saveUserToken(User user, String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            return null;
        }

        final User user = this.repository.findByEmail(userEmail).orElseThrow();
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        if (!user.getHabilitado()) throw new RuntimeException("inhabilitado");

        return new TokenResponse(accessToken, refreshToken);
    }

    public String getUsername() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // Extraer el token JWT directamente del header
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extrae el token JWT
            return jwtService.extractUsername(token);
        }
        return "No token found";
    }

    public String habilitarUser(String authentication){
        String token = authentication.substring(7);

        String username = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(username).get();
        user.setHabilitado(true);
        userRepository.save(user);

        return "cuenta habilitada";
    }

    public String redireccion(String email){

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()){

            taskExecutor.execute(()->{

                String token = jwtService.generateToken(userOptional.get());

                String u[] = new String[1];
                u[0] = email;
                emailService.sendEmail(u, "recuperacion de contraseña",
                        "http://localhost:8080/html/clave?jwt="+token);

            });
            return "se envio un enlace a tu correo para que recuperes tu contraseña";
        }
            return "el correo no existe en nuestra base de dato";
    }

    public String claveNueva(String clave){

        Optional<User> userOptional = userRepository.findByEmail(getUsername());

        if (userOptional.isPresent()){

            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(clave));
            userRepository.save(user);
            return "se ha cambiado su contraseña";
        }
        return "error";
    }

}
