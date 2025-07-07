package EcoMarket.EcoMarket.Pedidos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.model.Cupon;
import EcoMarket.EcoMarket.Pedidos.model.DetallePedido;
import EcoMarket.EcoMarket.Pedidos.service.PedidoService;
import EcoMarket.EcoMarket.Usuarios.model.Cliente;
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

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan Pérez");
        cliente.setCorreo("juan@email.com");
        cliente.setDireccionEnvio("Calle 123");
        cliente.setTelefono("123456789");

        DetallePedido detalle = new DetallePedido();
        detalle.setId(1);
        detalle.setCantidad(2);
        detalle.setPrecio(500);

        Cupon cupon = new Cupon();
        cupon.setId(1);
        cupon.setCodigo("DESC10");
        cupon.setDescuento(10);

        pedido = new Pedido();
        pedido.setId(1);
        pedido.setFecha(LocalDateTime.of(2024, 12, 25, 10, 0));
        pedido.setEstado("Pendiente");
        pedido.setTotal(1000);
        pedido.setCliente(cliente);
        pedido.setDetalle(List.of(detalle));
        pedido.setCupon(cupon);
    }

    @Test
    public void testGetAllPedidos() throws Exception {
        when(pedidoService.obtenerTodos()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("Pendiente"))
                .andExpect(jsonPath("$[0].total").value(1000))
                .andExpect(jsonPath("$[0].cliente.nombre").value("Juan Pérez"));
    }

    @Test
    public void testGetPedidoById() throws Exception {
        when(pedidoService.ObtenerPorId(1)).thenReturn(pedido);

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("Pendiente"))
                .andExpect(jsonPath("$.total").value(1000))
                .andExpect(jsonPath("$.cliente.nombre").value("Juan Pérez"));
    }

    @Test
    public void testCreatePedido() throws Exception {
        when(pedidoService.guardar(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("Pendiente"))
                .andExpect(jsonPath("$.total").value(1000))
                .andExpect(jsonPath("$.cliente.nombre").value("Juan Pérez"));
    }

    @Test
    public void testUpdatePedido() throws Exception {
        when(pedidoService.guardar(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("Pendiente"))
                .andExpect(jsonPath("$.total").value(1000))
                .andExpect(jsonPath("$.cliente.nombre").value("Juan Pérez"));
    }

    @Test
    public void testDeletePedido() throws Exception {
        doNothing().when(pedidoService).eliminar(1);

        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido eliminado con éxito"));

        verify(pedidoService, times(1)).eliminar(1);
    }
}
