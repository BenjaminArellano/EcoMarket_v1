package EcoMarket.EcoMarket.Pedidos.service;

import EcoMarket.EcoMarket.Pedidos.model.Cupon;
import EcoMarket.EcoMarket.Pedidos.model.DetallePedido;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.repository.PedidoRepository;
import EcoMarket.EcoMarket.Usuarios.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @MockBean
    private PedidoRepository pedidoRepository;

    @Test
    public void testObtenerTodos() {
        Pedido pedido = crearPedido();
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> pedidos = pedidoService.obtenerTodos();
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        assertEquals(pedido.getId(), pedidos.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Pedido pedido = crearPedido();
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        Pedido found = pedidoService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.ObtenerPorId(id);
        });
        assertEquals("Pedido no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Pedido pedido = crearPedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido saved = pedidoService.guardar(pedido);
        assertNotNull(saved);
        assertEquals("Pendiente", saved.getEstado());
        assertEquals(1000, saved.getTotal());
    }

    @Test
    public void testGuardarConFechaNula() {
        Pedido pedido = crearPedido();
        pedido.setFecha(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.guardar(pedido);
        });
        assertEquals("La fecha no puede ser nula.", exception.getMessage());
    }

    @Test
    public void testGuardarConFechaAnterior() {
        Pedido pedido = crearPedido();
        pedido.setFecha(LocalDateTime.now().minusDays(1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.guardar(pedido);
        });
        assertEquals("La fecha no puede ser anterior al día de hoy.", exception.getMessage());
    }

    @Test
    public void testGuardarConEstadoVacio() {
        Pedido pedido = crearPedido();
        pedido.setEstado("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.guardar(pedido);
        });
        assertEquals("El estado no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConTotalInvalido() {
        Pedido pedido = crearPedido();
        pedido.setTotal(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.guardar(pedido);
        });
        assertEquals("El total debe ser mayor a 0", exception.getMessage());
    }

    @Test
    public void testGuardarSinCliente() {
        Pedido pedido = crearPedido();
        pedido.setCliente(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.guardar(pedido);
        });
        assertEquals("El pedido debe tener un cliente.", exception.getMessage());
    }

    @Test
    public void testGuardarSinDetalle() {
        Pedido pedido = crearPedido();
        pedido.setDetalle(new ArrayList<>());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.guardar(pedido);
        });
        assertEquals("El pedido debe tener un detalle.", exception.getMessage());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(pedidoRepository.existsById(id)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(id);

        pedidoService.eliminar(id);
        verify(pedidoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(pedidoRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Pedido con ID " + id + " no existe.", exception.getMessage());
    }

    private Pedido crearPedido() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan Pérez");
        cliente.setCorreo("juan@email.com");
        cliente.setDireccionEnvio("Calle 123");
        cliente.setTelefono("123456789");

        DetallePedido detalle = new DetallePedido();
        detalle.setId(1);
        detalle.setCantidad(2);
        detalle.setPrecio(500);

        Cupon cupon = new Cupon();
        cupon.setId(1);
        cupon.setCodigo("DESC10");
        cupon.setDescuento(10);

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setFecha(LocalDateTime.now().plusDays(1));
        pedido.setEstado("Pendiente");
        pedido.setTotal(1000);
        pedido.setCliente(cliente);
        pedido.setDetalle(List.of(detalle));
        pedido.setCupon(cupon);

        return pedido;
    }
}
