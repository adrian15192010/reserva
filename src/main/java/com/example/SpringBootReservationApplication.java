package com.example;

import com.example.entity.Estadio;
import com.example.entity.Silla;
import com.example.jwt.Role;
import com.example.jwt.User;
import com.example.jwt.UserRepository;
import com.example.repository.EstadioRepository;
import com.example.repository.SillaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class SpringBootReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReservationApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(EstadioRepository estadioRepository, SillaRepository sillaRepository, UserRepository userRepository,
								  PasswordEncoder passwordEncoder){
		return args -> {

			userRepository.save(User.builder()
					.name("ADMIN")
					.email("admin")
					.password(passwordEncoder.encode("1234"))
					.role(Role.ADMIN)
					.build());

            int capacidad = 10;

			Estadio estadio = Estadio.builder()
					.id(3L)
					.nombre("Luis Aparicio")
                    .direccion("Maracaibo, Estado Zulia")
                    .capacidad(capacidad)
					.build();

			Estadio estadio2 = estadioRepository.save(estadio);

            for (int i =0; i<capacidad; i++){

                Silla silla = Silla.builder()
                        .estadio(estadio2)
                        .build();

                sillaRepository.save(silla);

            }



		};
	}

}
