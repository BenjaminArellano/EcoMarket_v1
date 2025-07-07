package EcoMarket.EcoMarket.Productos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto producto;

    @BeforeEach
    void setUp() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1);
        proveedor.setNombre("Proveedor Test");
        proveedor.setContacto("contacto@test.com");
        proveedor.setTelefono("123456789");

        producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Eco");
        producto.setDescripcion("Descripción del producto");
        producto.setCategoria("Ecológico");
        producto.setPrecio(1000);
        producto.setProveedor(proveedor);
    }

    @Test
    public void testGetAllProductos() throws Exception {
        when(productoService.obtenerTodos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Producto Eco"))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción del producto"))
                .andExpect(jsonPath("$[0].categoria").value("Ecológico"))
                .andExpect(jsonPath("$[0].precio").value(1000))
                .andExpect(jsonPath("$[0].proveedor.nombre").value("Proveedor Test"));
    }

    @Test
    public void testGetProductoById() throws Exception {
        when(productoService.obtenerPorId(1)).thenReturn(producto);

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto Eco"))
                .andExpect(jsonPath("$.descripcion").value("Descripción del producto"))
                .andExpect(jsonPath("$.categoria").value("Ecológico"))
                .andExpect(jsonPath("$.precio").value(1000))
                .andExpect(jsonPath("$.proveedor.nombre").value("Proveedor Test"));
    }

    @Test
    public void testCreateProducto() throws Exception {
        when(productoService.guardar(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto Eco"))
                .andExpect(jsonPath("$.descripcion").value("Descripción del producto"))
                .andExpect(jsonPath("$.categoria").value("Ecológico"))
                .andExpect(jsonPath("$.precio").value(1000))
                .andExpect(jsonPath("$.proveedor.nombre").value("Proveedor Test"));
    }

    @Test
    public void testUpdateProducto() throws Exception {
        when(productoService.guardar(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put("/api/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto Eco"))
                .andExpect(jsonPath("$.descripcion").value("Descripción del producto"))
                .andExpect(jsonPath("$.categoria").value("Ecológico"))
                .andExpect(jsonPath("$.precio").value(1000))
                .andExpect(jsonPath("$.proveedor.nombre").value("Proveedor Test"));
    }

    @Test
    public void testDeleteProducto() throws Exception {
        doNothing().when(productoService).eliminar(1);

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado con éxito"));

        verify(productoService, times(1)).eliminar(1);
    }
}
