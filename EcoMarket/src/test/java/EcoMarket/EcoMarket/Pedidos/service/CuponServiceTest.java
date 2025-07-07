package EcoMarket.EcoMarket.Pedidos.service;

import EcoMarket.EcoMarket.Pedidos.model.Cupon;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.repository.CuponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CuponServiceTest {

    @Autowired
    private CuponService cuponService;

    @MockBean
    private CuponRepository cuponRepository;

    @Test
    public void testObtenerTodos() {
        Cupon cupon = crearCupon();
        when(cuponRepository.findAll()).thenReturn(List.of(cupon));

        List<Cupon> cupones = cuponService.obtenerTodos();
        assertNotNull(cupones);
        assertEquals(1, cupones.size());
        assertEquals(cupon.getId(), cupones.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Cupon cupon = crearCupon();
        when(cuponRepository.findById(id)).thenReturn(Optional.of(cupon));

        Cupon found = cuponService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(cuponRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.ObtenerPorId(id);
        });
        assertEquals("Cupon no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Cupon cupon = crearCupon();
        when(cuponRepository.save(cupon)).thenReturn(cupon);

        Cupon saved = cuponService.guardar(cupon);
        assertNotNull(saved);
        assertEquals("DESC10", saved.getCodigo());
        assertEquals(10, saved.getDescuento());
    }

    @Test
    public void testGuardarConCodigoVacio() {
        Cupon cupon = crearCupon();
        cupon.setCodigo("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.guardar(cupon);
        });
        assertEquals("El codigo no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConCodigoNulo() {
        Cupon cupon = crearCupon();
        cupon.setCodigo(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.guardar(cupon);
        });
        assertEquals("El codigo no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConDescuentoInvalido() {
        Cupon cupon = crearCupon();
        cupon.setDescuento(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.guardar(cupon);
        });
        assertEquals("El descuento debe ser un valor entre 1 y 100.", exception.getMessage());
    }

    @Test
    public void testGuardarConDescuentoMayorA100() {
        Cupon cupon = crearCupon();
        cupon.setDescuento(101);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.guardar(cupon);
        });
        assertEquals("El descuento debe ser un valor entre 1 y 100.", exception.getMessage());
    }

    @Test
    public void testGuardarSinPedidos() {
        Cupon cupon = crearCupon();
        cupon.setPedidos(new ArrayList<>());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.guardar(cupon);
        });
        assertEquals("El cupon debe tener un pedido.", exception.getMessage());
    }

    @Test
    public void testGuardarConPedidosNulos() {
        Cupon cupon = crearCupon();
        cupon.setPedidos(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.guardar(cupon);
        });
        assertEquals("El cupon debe tener un pedido.", exception.getMessage());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(cuponRepository.existsById(id)).thenReturn(true);
        doNothing().when(cuponRepository).deleteById(id);

        cuponService.eliminar(id);
        verify(cuponRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(cuponRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cuponService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Cupon con ID " + id + " no existe.", exception.getMessage());
    }

    private Cupon crearCupon() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        Cupon cupon = new Cupon();
        cupon.setId(1);
        cupon.setCodigo("DESC10");
        cupon.setDescuento(10);
        cupon.setPedidos(List.of(pedido));

        return cupon;
    }
}
