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



@RestController
@RequestMapping("/api/detallepedidos")

public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @GetMapping
    public List<DetallePedido> obtenerTodos() {
        return detallePedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public DetallePedido obtenerPorId(@PathVariable int id) {
        return detallePedidoService.ObtenerPorId(id);
    }

    @PostMapping
    public DetallePedido guardar(@RequestBody DetallePedido detalle) {
        return detallePedidoService.guardar(detalle);
    }

    @PutMapping("/{id}")
    public DetallePedido actualizar(@PathVariable int id, @RequestBody DetallePedido detalle) {
        detalle.setId(id);
        return detallePedidoService.guardar(detalle);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        detallePedidoService.eliminar(id);
        return "Detalle de Pedido eliminado con éxito";
    }

}
