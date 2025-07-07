package EcoMarket.EcoMarket.Productos.service;

import EcoMarket.EcoMarket.Productos.model.Inventario;
import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.repository.InventarioRepository;
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
public class InventarioServiceTest {

    @Autowired
    private InventarioService inventarioService;

    @MockBean
    private InventarioRepository inventarioRepository;

    @Test
    public void testObtenerTodos() {
        Inventario inventario = crearInventario();
        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));

        List<Inventario> inventarios = inventarioService.obtenerTodos();
        assertNotNull(inventarios);
        assertEquals(1, inventarios.size());
        assertEquals(inventario.getId(), inventarios.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Inventario inventario = crearInventario();
        when(inventarioRepository.findById(id)).thenReturn(Optional.of(inventario));

        Inventario found = inventarioService.obtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventarioService.obtenerPorId(id);
        });
        assertEquals("Inventario no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Inventario inventario = crearInventario();
        when(inventarioRepository.save(inventario)).thenReturn(inventario);

        Inventario saved = inventarioService.guardar(inventario);
        assertNotNull(saved);
        assertEquals(100, saved.getStock());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(inventarioRepository.existsById(id)).thenReturn(true);
        doNothing().when(inventarioRepository).deleteById(id);

        inventarioService.eliminar(id);
        verify(inventarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(inventarioRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventarioService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Inventario con ID " + id + " no existe.", exception.getMessage());
    }

    private Inventario crearInventario() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Test");

        Inventario inventario = new Inventario();
        inventario.setId(1);
        inventario.setStock(100);
        inventario.setProducto(producto);

        return inventario;
    }
}
