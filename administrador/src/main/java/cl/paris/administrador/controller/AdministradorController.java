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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/api/v1/administradores")
@Tag(name = "Administradores", description = "Endpoints para la gestión de administradores de Paris")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Operation(summary = "Obtener todos los administradores")
    @ApiResponse(responseCode = "200", description = "Lista de administradores obtenida correctamente")
    @GetMapping
    public List<AdministradorResponse> obtenerTodos() {
        return administradorService.listarTodos();
    }

    @Operation(summary = "Obtener un administrador por su ID")
    @ApiResponse(responseCode = "200", description = "Administrador encontrado")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorResponse> obtenerPorId(@PathVariable UUID id) {
        AdministradorResponse dto = administradorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Crear un nuevo administrador",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos requeridos para crear un administrador",
            content = @Content(
                examples = @ExampleObject(
                    name = "Ejemplo de Creación",
                    value = "{\n  \"username\": \"jperez\",\n  \"password\": \"secreta123\",\n  \"nombre\": \"Juan Pérez\",\n  \"rol\": \"ADMIN\"\n}"
                )
            )
        )
    )
    @ApiResponse(responseCode = "201", description = "Administrador creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @PostMapping
    public ResponseEntity<AdministradorResponse> crearAdmin(@Valid @RequestBody AdministradorRequest dto) {
        AdministradorResponse nuevo = administradorService.guardar(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Actualizar un administrador existente",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Nuevos datos para actualizar al administrador",
            content = @Content(
                examples = @ExampleObject(
                    name = "Ejemplo de Actualización",
                    value = "{\n  \"username\": \"jperez_mod\",\n  \"password\": \"nueva_clave\",\n  \"nombre\": \"Juan Pérez Modificado\",\n  \"rol\": \"SUPER_ADMIN\"\n}"
                )
            )
        )
    )
    @ApiResponse(responseCode = "200", description = "Administrador actualizado")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<AdministradorResponse> actualizarAdmin(@PathVariable UUID id, @Valid @RequestBody AdministradorRequest dto) {
        return administradorService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un administrador por ID")
    @ApiResponse(responseCode = "204", description = "Administrador eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAdmin(@PathVariable UUID id) {
        if (administradorService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}