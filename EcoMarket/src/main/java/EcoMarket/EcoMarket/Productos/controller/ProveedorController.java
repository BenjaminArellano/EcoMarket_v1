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

import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores", description = "Operaciones relacionadas con la gestión de proveedores de productos ecológicos")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Obtiene una lista de todos los proveedores registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Proveedor> obtenerTodos() {
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID", description = "Obtiene un proveedor específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Proveedor obtenerPorId(@PathVariable int id) {
        return proveedorService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo proveedor", description = "Crea un nuevo proveedor en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proveedor", description = "Actualiza la información de un proveedor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Proveedor actualizar(@PathVariable int id, @RequestBody Proveedor proveedor) {
        proveedor.setId(id);
        return proveedorService.guardar(proveedor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        proveedorService.eliminar(id);
        return "Proveedor eliminado con éxito";
    }
}
