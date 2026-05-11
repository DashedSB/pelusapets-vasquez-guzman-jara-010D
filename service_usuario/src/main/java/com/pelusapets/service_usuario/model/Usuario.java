package com.pelusapets.service_usuario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 4, max = 20, message = "El nombre debe tener entre 4 y 20 caracteres")
    @Column(nullable = false, unique = true)
    private String nombreUsusario;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un formato de email válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;
}
