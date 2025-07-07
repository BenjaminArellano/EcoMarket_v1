package EcoMarket.EcoMarket.Pedidos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Pedidos.model.DetallePedido;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.service.DetallePedidoService;
import EcoMarket.EcoMarket.Productos.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(DetallePedidoController.class)
public class DetallePedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DetallePedidoService detallePedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private DetallePedido detallePedido;

    @BeforeEach
    void setUp() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Test");

        Pedido pedido = new Pedido();
        pedido.setId(1);

        detallePedido = new DetallePedido();
        detallePedido.setId(1);
        detallePedido.setCantidad(2);
        detallePedido.setPrecio(500);
        detallePedido.setProducto(producto);
        detallePedido.setPedido(pedido);
    }

    @Test
    public void testGetAllDetallePedidos() throws Exception {
        when(detallePedidoService.obtenerTodos()).thenReturn(List.of(detallePedido));

        mockMvc.perform(get("/api/detallepedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2))
                .andExpect(jsonPath("$[0].precio").value(500))
                .andExpect(jsonPath("$[0].producto.nombre").value("Producto Test"));
    }

    @Test
    public void testGetDetallePedidoById() throws Exception {
        when(detallePedidoService.ObtenerPorId(1)).thenReturn(detallePedido);

        mockMvc.perform(get("/api/detallepedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precio").value(500))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testCreateDetallePedido() throws Exception {
        when(detallePedidoService.guardar(any(DetallePedido.class))).thenReturn(detallePedido);

        mockMvc.perform(post("/api/detallepedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detallePedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precio").value(500))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testUpdateDetallePedido() throws Exception {
        when(detallePedidoService.guardar(any(DetallePedido.class))).thenReturn(detallePedido);

        mockMvc.perform(put("/api/detallepedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detallePedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precio").value(500))
                .andExpect(jsonPath("$.producto.nombre").value("Producto Test"));
    }

    @Test
    public void testDeleteDetallePedido() throws Exception {
        doNothing().when(detallePedidoService).eliminar(1);

        mockMvc.perform(delete("/api/detallepedidos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Detalle de Pedido eliminado con éxito"));

        verify(detallePedidoService, times(1)).eliminar(1);
    }
}
