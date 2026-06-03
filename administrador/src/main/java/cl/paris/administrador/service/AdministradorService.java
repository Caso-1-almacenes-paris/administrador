package cl.paris.administrador.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.paris.administrador.dto.AdministradorRequest;
import cl.paris.administrador.dto.AdministradorResponse;
import cl.paris.administrador.exception.ResourceNotFoundException;
import cl.paris.administrador.mapper.AdministradorMapper;
import cl.paris.administrador.model.Administrador;
import cl.paris.administrador.repository.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private AdministradorMapper administradorMapper;

    // Listar todos
    public List<AdministradorResponse> listarTodos() {
        return administradorRepository.findAll()
                .stream()
                .map(administradorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Buscar por UUID seguro
    public AdministradorResponse buscarPorId(UUID id) {
        return administradorRepository.findById(id)
                .map(administradorMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado con el ID seguro: " + id));
    }

    // Guardar administrador
    public AdministradorResponse guardar(AdministradorRequest dto) {
        Administrador admin = administradorMapper.toEntity(dto);
        // Nota: Aquí se aplicará BCrypt para la contraseña en el futuro
        Administrador guardado = administradorRepository.save(admin);
        return administradorMapper.toResponseDto(guardado);
    }

    // Actualizar datos
    public Optional<AdministradorResponse> actualizar(UUID id, AdministradorRequest dto) {
        return administradorRepository.findById(id)
                .map(existente -> {
                    existente.setUsername(dto.getUsername());
                    existente.setPassword(dto.getPassword()); // Temporal sin encriptar
                    existente.setNombre(dto.getNombre());
                    existente.setRol(dto.getRol());
                    return administradorMapper.toResponseDto(administradorRepository.save(existente));
                });
    }

    // Eliminar por UUID
    public boolean eliminar(UUID id) {
        return administradorRepository.findById(id)
                .map(admin -> {
                    administradorRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}