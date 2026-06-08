package cl.paris.administrador.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.paris.administrador.dto.AdministradorRequest;
import cl.paris.administrador.dto.AdministradorResponse;
import cl.paris.administrador.service.AdministradorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    public List<AdministradorResponse> obtenerTodos() {
        return administradorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponse> obtenerPorId(@PathVariable UUID id) {
        AdministradorResponse dto = administradorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AdministradorResponse> crearAdmin(@Valid @RequestBody AdministradorRequest dto) {
        AdministradorResponse nuevo = administradorService.guardar(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponse> actualizarAdmin(@PathVariable UUID id, @Valid @RequestBody AdministradorRequest dto) {
        return administradorService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAdmin(@PathVariable UUID id) {
        if (administradorService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}