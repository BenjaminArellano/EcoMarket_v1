package EcoMarket.EcoMarket.Pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Pedidos.model.DetallePedido;
import EcoMarket.EcoMarket.Pedidos.repository.DetallePedidoRepository;




@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> obtenerTodos() {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido ObtenerPorId(int id) {
        return detallePedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Detalle de Pedido no encontrado con ID: " + id));
    }

    public DetallePedido guardar(DetallePedido detalle) {

        if (detalle.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 1");
        }

        if (detalle.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (detalle.getPedido() == null) {
            throw new IllegalArgumentException("El Detalle debe tener un pedido.");
        }

        if (detalle.getProducto() == null) {
            throw new IllegalArgumentException("El Detalle debe tener un pedido.");
        }

        return detallePedidoRepository.save(detalle);
    }

    public void eliminar(int id) {

    if (!detallePedidoRepository.existsById(id)) {
        throw new IllegalArgumentException("No se puede eliminar. Detalle con ID " + id + " no existe.");
    }

    detallePedidoRepository.deleteById(id);
    }
}
