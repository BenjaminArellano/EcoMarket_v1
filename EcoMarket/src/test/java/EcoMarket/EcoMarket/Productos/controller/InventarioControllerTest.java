package EcoMarket.EcoMarket.Productos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Productos.model.Inventario;
import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.service.InventarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Test");

        inventario = new Inventario();
        inventario.setId(1);
        inventario.setStock(100);
        inventario.setProducto(producto);
    }

    @Test
    public void testGetAllInventarios() throws Exception {
        when(inventarioService.obtenerTodos()).thenReturn(List.of(inventario));

        mockMvc.perform(get("/api/inventarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].stock").value(100))
                .andExpect(jsonPath("$[0].producto.nombre").value("Producto Test"));
    }

    @Test
    public void testGetInventarioById() throws Exception {
        when(inventarioService.obtenerPorId(1)).thenReturn(inventario);

        mockMvc.perform(get("/api/inventarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stock").value(100))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testCreateInventario() throws Exception {
        when(inventarioService.guardar(any(Inventario.class))).thenReturn(inventario);

        mockMvc.perform(post("/api/inventarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stock").value(100))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testUpdateInventario() throws Exception {
        when(inventarioService.guardar(any(Inventario.class))).thenReturn(inventario);

        mockMvc.perform(put("/api/inventarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stock").value(100))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testDeleteInventario() throws Exception {
        doNothing().when(inventarioService).eliminar(1);

        mockMvc.perform(delete("/api/inventarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventario eliminado con éxito"));

        verify(inventarioService, times(1)).eliminar(1);
    }
}
