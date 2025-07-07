package EcoMarket.EcoMarket.Productos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ProveedorController.class)
public class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProveedorService proveedorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Proveedor proveedor;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor();
        proveedor.setId(1);
        proveedor.setNombre("Proveedor Test");
        proveedor.setContacto("contacto@test.com");
        proveedor.setTelefono("123456789");
    }

    @Test
    public void testGetAllProveedores() throws Exception {
        when(proveedorService.obtenerTodos()).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Proveedor Test"))
                .andExpect(jsonPath("$[0].contacto").value("contacto@test.com"))
                .andExpect(jsonPath("$[0].telefono").value("123456789"));
    }

    @Test
    public void testGetProveedorById() throws Exception {
        when(proveedorService.obtenerPorId(1)).thenReturn(proveedor);

        mockMvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Proveedor Test"))
                .andExpect(jsonPath("$.contacto").value("contacto@test.com"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    public void testCreateProveedor() throws Exception {
        when(proveedorService.guardar(any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Proveedor Test"))
                .andExpect(jsonPath("$.contacto").value("contacto@test.com"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    public void testUpdateProveedor() throws Exception {
        when(proveedorService.guardar(any(Proveedor.class))).thenReturn(proveedor);

        mockMvc.perform(put("/api/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Proveedor Test"))
                .andExpect(jsonPath("$.contacto").value("contacto@test.com"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    public void testDeleteProveedor() throws Exception {
        doNothing().when(proveedorService).eliminar(1);

        mockMvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Proveedor eliminado con éxito"));

        verify(proveedorService, times(1)).eliminar(1);
    }
}
