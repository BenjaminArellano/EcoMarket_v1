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

import EcoMarket.EcoMarket.Pedidos.model.DetallePedido;
import EcoMarket.EcoMarket.Pedidos.service.DetallePedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/detallepedidos")
@Tag(name = "Detalle de Pedidos", description = "Operaciones relacionadas con la gestión de detalles de pedidos")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    @Operation(summary = "Obtener todos los detalles de pedidos", description = "Obtiene una lista de todos los detalles de pedidos registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de detalles de pedidos obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<DetallePedido> obtenerTodos() {
        return detallePedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de pedido por ID", description = "Obtiene un detalle de pedido específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public DetallePedido obtenerPorId(@PathVariable int id) {
        return detallePedidoService.ObtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo detalle de pedido", description = "Crea un nuevo detalle de pedido en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public DetallePedido guardar(@RequestBody DetallePedido detalle) {
        return detallePedidoService.guardar(detalle);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar detalle de pedido", description = "Actualiza la información de un detalle de pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public DetallePedido actualizar(@PathVariable int id, @RequestBody DetallePedido detalle) {
        detalle.setId(id);
        return detallePedidoService.guardar(detalle);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar detalle de pedido", description = "Elimina un detalle de pedido del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Detalle de pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        detallePedidoService.eliminar(id);
        return "Detalle de Pedido eliminado con éxito";
    }

}
