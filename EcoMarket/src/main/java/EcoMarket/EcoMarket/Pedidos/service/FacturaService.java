package EcoMarket.EcoMarket.Pedidos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Pedidos.model.Factura;
import EcoMarket.EcoMarket.Pedidos.repository.FacturaRepository;



@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public List<Factura> obtenerTodos() {
        return facturaRepository.findAll();
    }

    public Factura ObtenerPorId(int id) {
        return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Factura no encontrado con ID: " + id));
    }

    public Factura guardar(Factura factura) {

        if (factura.getTotal() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }

        if (factura.getFecha() == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate fechaFactura = factura.getFecha().toLocalDate();

        if (fechaFactura.isBefore(hoy)) {
            throw new IllegalArgumentException("La fecha no puede ser anterior al día de hoy.");
        }

        if (factura.getPedido() == null) {
            throw new IllegalArgumentException("El envio debe tener un pedido.");
        }

        return facturaRepository.save(factura);
    }

    public void eliminar(int id) {

        if (!facturaRepository.existsById(id)) {
        throw new IllegalArgumentException("No se puede eliminar. Factura con ID " + id + " no existe.");
        }

        facturaRepository.deleteById(id);
    }
}
