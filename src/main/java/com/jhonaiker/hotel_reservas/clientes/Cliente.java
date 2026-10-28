package com.jhonaiker.hotel_reservas.clientes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CLIENTES")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @NotBlank
    @Column(name = "RUT", nullable = false, unique = true, length = 12)
    private String rut;

    @NotBlank
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Email
    @Column(name = "EMAIL", length = 120)
    private String email;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;
}
