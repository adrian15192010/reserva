package com.example.dto;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDto {

    private Long id;

    private String usuario;

    private String eventoName;

    private Long sillaId;

}
