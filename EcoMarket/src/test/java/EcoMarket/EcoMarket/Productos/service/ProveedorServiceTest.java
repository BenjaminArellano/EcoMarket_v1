package EcoMarket.EcoMarket.Productos.service;

import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.repository.ProveedorRepository;
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
public class ProveedorServiceTest {

    @Autowired
    private ProveedorService proveedorService;

    @MockBean
    private ProveedorRepository proveedorRepository;

    @Test
    public void testObtenerTodos() {
        Proveedor proveedor = crearProveedor();
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedor));

        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        assertNotNull(proveedores);
        assertEquals(1, proveedores.size());
        assertEquals(proveedor.getId(), proveedores.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Proveedor proveedor = crearProveedor();
        when(proveedorRepository.findById(id)).thenReturn(Optional.of(proveedor));

        Proveedor found = proveedorService.obtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(proveedorRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            proveedorService.obtenerPorId(id);
        });
        assertEquals("Proveedor no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Proveedor proveedor = crearProveedor();
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        Proveedor saved = proveedorService.guardar(proveedor);
        assertNotNull(saved);
        assertEquals("Proveedor Eco", saved.getNombre());
        assertEquals("contacto@eco.com", saved.getContacto());
        assertEquals("123456789", saved.getTelefono());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(proveedorRepository.existsById(id)).thenReturn(true);
        doNothing().when(proveedorRepository).deleteById(id);

        proveedorService.eliminar(id);
        verify(proveedorRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(proveedorRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            proveedorService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Proveedor con ID " + id + " no existe.", exception.getMessage());
    }

    private Proveedor crearProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1);
        proveedor.setNombre("Proveedor Eco");
        proveedor.setContacto("contacto@eco.com");
        proveedor.setTelefono("123456789");

        return proveedor;
    }
}
