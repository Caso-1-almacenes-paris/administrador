package cl.paris.administrador.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdministradorRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}