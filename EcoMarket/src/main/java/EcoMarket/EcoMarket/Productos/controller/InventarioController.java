package EcoMarket.EcoMarket.Productos.controller;

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

import EcoMarket.EcoMarket.Productos.model.Inventario;
import EcoMarket.EcoMarket.Productos.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventarios")
@Tag(name = "Inventarios", description = "Operaciones relacionadas con la gestión de inventarios de productos")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los inventarios", description = "Obtiene una lista de todos los registros de inventario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inventarios obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Inventario> obtenerTodos() {
        return inventarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener inventario por ID", description = "Obtiene un registro de inventario específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Inventario obtenerPorId(@PathVariable int id) {
        return inventarioService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo inventario", description = "Crea un nuevo registro de inventario en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Inventario guardar(@RequestBody Inventario inventario) {
        return inventarioService.guardar(inventario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inventario", description = "Actualiza la información de un registro de inventario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Inventario actualizar(@PathVariable int id, @RequestBody Inventario inventario) {
        inventario.setId(id);
        return inventarioService.guardar(inventario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un registro de inventario del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        inventarioService.eliminar(id);
        return "Inventario eliminado con éxito";
    }
}
