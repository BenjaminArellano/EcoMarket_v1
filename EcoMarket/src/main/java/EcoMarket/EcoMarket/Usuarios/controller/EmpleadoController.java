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

import EcoMarket.EcoMarket.Usuarios.model.Empleado;
import EcoMarket.EcoMarket.Usuarios.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Empleados", description = "Operaciones relacionadas con la gestión de empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping
    @Operation(summary = "Obtener todos los empleados", description = "Obtiene una lista de todos los empleados registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Empleado> obtenerTodos() {
        return empleadoService.obtenerTodos();
    }

    @PostMapping
    @Operation(summary = "Crear nuevo empleado", description = "Crea un nuevo empleado en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Empleado guardar(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Obtiene un empleado específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Empleado obtenerPorId(@PathVariable int id) {
        return empleadoService.ObtenerPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado", description = "Actualiza la información de un empleado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Empleado actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoService.guardar(empleado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        empleadoService.eliminar(id);
        return "Empleado eliminado con éxito";
    }
}
