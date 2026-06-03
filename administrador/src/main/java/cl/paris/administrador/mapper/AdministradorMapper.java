package cl.paris.administrador.mapper;

import org.springframework.stereotype.Component;

import cl.paris.administrador.dto.AdministradorRequest;
import cl.paris.administrador.dto.AdministradorResponse;
import cl.paris.administrador.model.Administrador;

@Component
public class AdministradorMapper {

    public Administrador toEntity(AdministradorRequest dto) {
        if (dto == null) return null;

        Administrador admin = new Administrador();
        admin.setUsername(dto.getUsername());
        admin.setPassword(dto.getPassword()); 
        admin.setNombre(dto.getNombre());
        admin.setRol(dto.getRol());
        return admin;
    }

    public AdministradorResponse toResponseDto(Administrador entidad) {
        if (entidad == null) return null;
        AdministradorResponse dto = new AdministradorResponse();
        dto.setId(entidad.getId());
        dto.setUsername(entidad.getUsername());
        dto.setNombre(entidad.getNombre());
        dto.setRol(entidad.getRol());
        return dto;
    }
}