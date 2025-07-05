package EcoMarket.EcoMarket.Pedidos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.repository.PedidoRepository;




@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido ObtenerPorId(int id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + id));
    }

    public Pedido guardar(Pedido pedido) {

        if (pedido.getFecha() == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaFactura = pedido.getFecha().toLocalDate();

        if (fechaFactura.isBefore(hoy)) {
            throw new IllegalArgumentException("La fecha no puede ser anterior al día de hoy.");
        }

        if (pedido.getEstado() == null || pedido.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }

        if (pedido.getTotal() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }

        if (pedido.getCliente() == null) {
            throw new IllegalArgumentException("El pedido debe tener un cliente.");
        }

        if (pedido.getDetalle() == null || pedido.getDetalle().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener un detalle.");
        }


        return pedidoRepository.save(pedido);
    }

    public void eliminar(int id) {

        if (!pedidoRepository.existsById(id)) {
        throw new IllegalArgumentException("No se puede eliminar. Pedido con ID " + id + " no existe.");
        }

        pedidoRepository.deleteById(id);
    }
}
