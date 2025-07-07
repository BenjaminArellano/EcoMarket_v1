package EcoMarket.EcoMarket.Pedidos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Pedidos.model.Factura;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.service.FacturaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Factura factura;

    @BeforeEach
    void setUp() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        factura = new Factura();
        factura.setId(1);
        factura.setFecha(LocalDateTime.of(2024, 12, 25, 10, 0));
        factura.setTotal(1000);
        factura.setPedido(pedido);
    }

    @Test
    public void testGetAllFacturas() throws Exception {
        when(facturaService.obtenerTodos()).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].total").value(1000));
    }

    @Test
    public void testGetFacturaById() throws Exception {
        when(facturaService.ObtenerPorId(1)).thenReturn(factura);

        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(1000));
    }

    @Test
    public void testCreateFactura() throws Exception {
        when(facturaService.guardar(any(Factura.class))).thenReturn(factura);

        mockMvc.perform(post("/api/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(1000));
    }

    @Test
    public void testUpdateFactura() throws Exception {
        when(facturaService.guardar(any(Factura.class))).thenReturn(factura);

        mockMvc.perform(put("/api/facturas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.total").value(1000));
    }

    @Test
    public void testDeleteFactura() throws Exception {
        doNothing().when(facturaService).eliminar(1);

        mockMvc.perform(delete("/api/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Factura eliminado con éxito"));

        verify(facturaService, times(1)).eliminar(1);
    }
}
