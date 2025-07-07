package EcoMarket.EcoMarket.Usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/permisos")
@Tag(name = "Permisos", description = "Operaciones relacionadas con la gestión de permisos del sistema")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping
    @Operation(summary = "Obtener todos los permisos", description = "Obtiene una lista de todos los permisos disponibles en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Permiso> obtenerTodos() {
        return permisoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener permiso por ID", description = "Obtiene un permiso específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Permiso obtenerPorId(@PathVariable int id) {
        return permisoService.ObtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo permiso", description = "Crea un nuevo permiso en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Permiso guardar(@RequestBody Permiso permiso) {
        return permisoService.guardar(permiso);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar permiso", description = "Actualiza la información de un permiso existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Permiso actualizar(@PathVariable int id, @RequestBody Permiso permiso) {
        permiso.setId(id);
        return permisoService.guardar(permiso);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar permiso", description = "Elimina un permiso del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        permisoService.eliminar(id);
        return "Permiso eliminado con éxito";
    }
}
