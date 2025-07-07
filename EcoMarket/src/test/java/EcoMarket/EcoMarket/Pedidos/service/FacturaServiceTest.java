package EcoMarket.EcoMarket.Pedidos.service;

import EcoMarket.EcoMarket.Pedidos.model.Factura;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.repository.FacturaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class FacturaServiceTest {

    @Autowired
    private FacturaService facturaService;

    @MockBean
    private FacturaRepository facturaRepository;

    @Test
    public void testObtenerTodos() {
        Factura factura = crearFactura();
        when(facturaRepository.findAll()).thenReturn(List.of(factura));

        List<Factura> facturas = facturaService.obtenerTodos();
        assertNotNull(facturas);
        assertEquals(1, facturas.size());
        assertEquals(factura.getId(), facturas.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Factura factura = crearFactura();
        when(facturaRepository.findById(id)).thenReturn(Optional.of(factura));

        Factura found = facturaService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(facturaRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.ObtenerPorId(id);
        });
        assertEquals("Factura no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Factura factura = crearFactura();
        when(facturaRepository.save(factura)).thenReturn(factura);

        Factura saved = facturaService.guardar(factura);
        assertNotNull(saved);
        assertEquals(1000, saved.getTotal());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(facturaRepository.existsById(id)).thenReturn(true);
        doNothing().when(facturaRepository).deleteById(id);

        facturaService.eliminar(id);
        verify(facturaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(facturaRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            facturaService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Factura con ID " + id + " no existe.", exception.getMessage());
    }

    private Factura crearFactura() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        Factura factura = new Factura();
        factura.setId(1);
        factura.setFecha(LocalDateTime.now());
        factura.setTotal(1000);
        factura.setPedido(pedido);

        return factura;
    }
}
