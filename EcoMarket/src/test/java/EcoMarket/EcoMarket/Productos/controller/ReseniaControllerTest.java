package EcoMarket.EcoMarket.Productos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Productos.model.Resenia;
import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.service.ReseniaService;
import EcoMarket.EcoMarket.Usuarios.model.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ReseniaController.class)
public class ReseniaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReseniaService reseniaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Resenia resenia;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Cliente Test");

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Test");

        resenia = new Resenia();
        resenia.setId(1);
        resenia.setComentario("Excelente producto");
        resenia.setCalificacion(5);
        resenia.setCliente(cliente);
        resenia.setProducto(producto);
    }

    @Test
    public void testGetAllResenias() throws Exception {
        when(reseniaService.obtenerTodos()).thenReturn(List.of(resenia));

        mockMvc.perform(get("/api/resenias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].comentario").value("Excelente producto"))
                .andExpect(jsonPath("$[0].calificacion").value(5))
                .andExpect(jsonPath("$[0].cliente.nombre").value("Cliente Test"))
                .andExpect(jsonPath("$[0].producto.nombre").value("Producto Test"));
    }

    @Test
    public void testGetReseniaById() throws Exception {
        when(reseniaService.obtenerPorId(1)).thenReturn(resenia);

        mockMvc.perform(get("/api/resenias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comentario").value("Excelente producto"))
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.cliente.nombre").value("Cliente Test"))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testCreateResenia() throws Exception {
        when(reseniaService.guardar(any(Resenia.class))).thenReturn(resenia);

        mockMvc.perform(post("/api/resenias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resenia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comentario").value("Excelente producto"))
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.cliente.nombre").value("Cliente Test"))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testUpdateResenia() throws Exception {
        when(reseniaService.guardar(any(Resenia.class))).thenReturn(resenia);

        mockMvc.perform(put("/api/resenias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resenia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comentario").value("Excelente producto"))
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.cliente.nombre").value("Cliente Test"))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testDeleteResenia() throws Exception {
        doNothing().when(reseniaService).eliminar(1);

        mockMvc.perform(delete("/api/resenias/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reseña eliminada con éxito"));

        verify(reseniaService, times(1)).eliminar(1);
    }
}
