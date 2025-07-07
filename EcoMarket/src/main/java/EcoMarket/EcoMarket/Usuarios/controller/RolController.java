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

import EcoMarket.EcoMarket.Usuarios.model.Rol;
import EcoMarket.EcoMarket.Usuarios.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Operaciones relacionadas con la gestión de roles y permisos de usuarios")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    @Operation(summary = "Obtener todos los roles", description = "Obtiene una lista de todos los roles disponibles en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Rol> obtenerTodos() {
        return rolService.obtenerTodos();
    }

    @PostMapping
    @Operation(summary = "Crear nuevo rol", description = "Crea un nuevo rol en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Rol guardar(@RequestBody Rol rol) {
        return rolService.guardar(rol);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Obtiene un rol específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Rol obtenerPorId(@PathVariable int id) {
        return rolService.ObtenerPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "Actualiza la información de un rol existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Rol actualizar(@PathVariable int id, @RequestBody Rol rol) {
        rol.setId(id);
        return rolService.guardar(rol);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        rolService.eliminar(id);
        return "rol eliminado con éxito";
    }

}
