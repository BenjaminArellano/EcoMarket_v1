package EcoMarket.EcoMarket.Pedidos.controller;

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

import EcoMarket.EcoMarket.Pedidos.model.Factura;
import EcoMarket.EcoMarket.Pedidos.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturas", description = "Operaciones relacionadas con la gestión de facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    @Operation(summary = "Obtener todas las facturas", description = "Obtiene una lista de todas las facturas registradas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Factura> obtenerTodos() {
        return facturaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por ID", description = "Obtiene una factura específica mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Factura obtenerPorId(@PathVariable int id) {
        return facturaService.ObtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nueva factura", description = "Crea una nueva factura en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Factura guardar(@RequestBody Factura factura) {
        return facturaService.guardar(factura);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar factura", description = "Actualiza la información de una factura existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Factura actualizar(@PathVariable int id, @RequestBody Factura factura) {
        factura.setId(id);
        return facturaService.guardar(factura);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar factura", description = "Elimina una factura del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        facturaService.eliminar(id);
        return "Factura eliminado con éxito";
    }

}
