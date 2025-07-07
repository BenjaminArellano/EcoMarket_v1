package EcoMarket.EcoMarket.Pedidos.service;

import EcoMarket.EcoMarket.Pedidos.model.DetallePedido;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.repository.DetallePedidoRepository;
import EcoMarket.EcoMarket.Productos.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DetallePedidoServiceTest {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @MockBean
    private DetallePedidoRepository detallePedidoRepository;

    @Test
    public void testObtenerTodos() {
        DetallePedido detalle = crearDetallePedido();
        when(detallePedidoRepository.findAll()).thenReturn(List.of(detalle));

        List<DetallePedido> detalles = detallePedidoService.obtenerTodos();
        assertNotNull(detalles);
        assertEquals(1, detalles.size());
        assertEquals(detalle.getId(), detalles.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        DetallePedido detalle = crearDetallePedido();
        when(detallePedidoRepository.findById(id)).thenReturn(Optional.of(detalle));

        DetallePedido found = detallePedidoService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(detallePedidoRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            detallePedidoService.ObtenerPorId(id);
        });
        assertEquals("DetallePedido no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        DetallePedido detalle = crearDetallePedido();
        when(detallePedidoRepository.save(detalle)).thenReturn(detalle);

        DetallePedido saved = detallePedidoService.guardar(detalle);
        assertNotNull(saved);
        assertEquals(2, saved.getCantidad());
        assertEquals(500, saved.getPrecio());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(detallePedidoRepository.existsById(id)).thenReturn(true);
        doNothing().when(detallePedidoRepository).deleteById(id);

        detallePedidoService.eliminar(id);
        verify(detallePedidoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(detallePedidoRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            detallePedidoService.eliminar(id);
        });
        assertEquals("No se puede eliminar. DetallePedido con ID " + id + " no existe.", exception.getMessage());
    }

    private DetallePedido crearDetallePedido() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Test");
        producto.setDescripcion("Descripción test");
        producto.setCategoria("Categoría test");
        producto.setPrecio(500);

        Pedido pedido = new Pedido();
        pedido.setId(1);

        DetallePedido detalle = new DetallePedido();
        detalle.setId(1);
        detalle.setCantidad(2);
        detalle.setPrecio(500);
        detalle.setProducto(producto);
        detalle.setPedido(pedido);

        return detalle;
    }
}
