package EcoMarket.EcoMarket.Productos.service;

import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.repository.ProductoRepository;
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
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @Test
    public void testObtenerTodos() {
        Producto producto = crearProducto();
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> productos = productoService.obtenerTodos();
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals(producto.getId(), productos.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Producto producto = crearProducto();
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        Producto found = productoService.obtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.obtenerPorId(id);
        });
        assertEquals("Producto no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Producto producto = crearProducto();
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto saved = productoService.guardar(producto);
        assertNotNull(saved);
        assertEquals("Producto Eco", saved.getNombre());
        assertEquals("Descripción del producto", saved.getDescripcion());
        assertEquals("Ecológico", saved.getCategoria());
        assertEquals(1000, saved.getPrecio());
    }

    @Test
    public void testGuardarConNombreVacio() {
        Producto producto = crearProducto();
        producto.setNombre("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.guardar(producto);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConNombreNulo() {
        Producto producto = crearProducto();
        producto.setNombre(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.guardar(producto);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConDescripcionVacia() {
        Producto producto = crearProducto();
        producto.setDescripcion("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.guardar(producto);
        });
        assertEquals("La descripcion no puede estar vacía.", exception.getMessage());
    }

    @Test
    public void testGuardarConCategoriaVacia() {
        Producto producto = crearProducto();
        producto.setCategoria("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.guardar(producto);
        });
        assertEquals("La categoria no puede estar vacía.", exception.getMessage());
    }

    @Test
    public void testGuardarConPrecioInvalido() {
        Producto producto = crearProducto();
        producto.setPrecio(0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.guardar(producto);
        });
        assertEquals("El Precio debe ser mayor a 0", exception.getMessage());
    }

    @Test
    public void testGuardarSinProveedor() {
        Producto producto = crearProducto();
        producto.setProveedor(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.guardar(producto);
        });
        assertEquals("El producto debe tener un proveedor.", exception.getMessage());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(productoRepository.existsById(id)).thenReturn(true);
        doNothing().when(productoRepository).deleteById(id);

        productoService.eliminar(id);
        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(productoRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Producto con ID " + id + " no existe.", exception.getMessage());
    }

    private Producto crearProducto() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1);
        proveedor.setNombre("Proveedor Test");
        proveedor.setContacto("contacto@test.com");
        proveedor.setTelefono("123456789");

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Eco");
        producto.setDescripcion("Descripción del producto");
        producto.setCategoria("Ecológico");
        producto.setPrecio(1000);
        producto.setProveedor(proveedor);

        return producto;
    }
}
