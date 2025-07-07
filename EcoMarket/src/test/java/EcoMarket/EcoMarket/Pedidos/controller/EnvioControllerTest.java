package EcoMarket.EcoMarket.Pedidos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Pedidos.model.Envio;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.service.EnvioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Envio envio;

    @BeforeEach
    void setUp() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        envio = new Envio();
        envio.setId(1);
        envio.setDireccion("Calle 123, Ciudad");
        envio.setEstado("En tránsito");
        envio.setPedido(pedido);
    }

    @Test
    public void testGetAllEnvios() throws Exception {
        when(envioService.obtenerTodos()).thenReturn(List.of(envio));

        mockMvc.perform(get("/api/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].direccion").value("Calle 123, Ciudad"))
                .andExpect(jsonPath("$[0].estado").value("En tránsito"));
    }

    @Test
    public void testGetEnvioById() throws Exception {
        when(envioService.ObtenerPorId(1)).thenReturn(envio);

        mockMvc.perform(get("/api/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Calle 123, Ciudad"))
                .andExpect(jsonPath("$.estado").value("En tránsito"));
    }

    @Test
    public void testCreateEnvio() throws Exception {
        when(envioService.guardar(any(Envio.class))).thenReturn(envio);

        mockMvc.perform(post("/api/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Calle 123, Ciudad"))
                .andExpect(jsonPath("$.estado").value("En tránsito"));
    }

    @Test
    public void testUpdateEnvio() throws Exception {
        when(envioService.guardar(any(Envio.class))).thenReturn(envio);

        mockMvc.perform(put("/api/envios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(envio)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.direccion").value("Calle 123, Ciudad"))
                .andExpect(jsonPath("$.estado").value("En tránsito"));
    }

    @Test
    public void testDeleteEnvio() throws Exception {
        doNothing().when(envioService).eliminar(1);

        mockMvc.perform(delete("/api/envios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Envio eliminado con éxito"));

        verify(envioService, times(1)).eliminar(1);
    }
}
