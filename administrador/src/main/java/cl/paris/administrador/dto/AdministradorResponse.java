package cl.paris.administrador.dto;

import java.util.UUID;
 
import lombok.Data; 

@Data
public class AdministradorResponse {
    private UUID id; 
    private String username;
    private String nombre;
    private String rol;
}