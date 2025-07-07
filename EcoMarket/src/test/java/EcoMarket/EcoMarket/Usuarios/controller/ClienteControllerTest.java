package EcoMarket.EcoMarket.Usuarios.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Usuarios.model.Cliente;
import EcoMarket.EcoMarket.Usuarios.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan Pérez");
        cliente.setCorreo("juan@email.com");
        cliente.setDireccionEnvio("Calle 123");
        cliente.setTelefono("123456789");
    }

    @Test
    public void testGetAllClientes() throws Exception {
        when(clienteService.obtenerTodos()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].correo").value("juan@email.com"))
                .andExpect(jsonPath("$[0].direccionEnvio").value("Calle 123"))
                .andExpect(jsonPath("$[0].telefono").value("123456789"));
    }

    @Test
    public void testGetClienteById() throws Exception {
        when(clienteService.ObtenerPorId(1)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.correo").value("juan@email.com"))
                .andExpect(jsonPath("$.direccionEnvio").value("Calle 123"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    public void testCreateCliente() throws Exception {
        when(clienteService.guardar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.correo").value("juan@email.com"))
                .andExpect(jsonPath("$.direccionEnvio").value("Calle 123"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    public void testUpdateCliente() throws Exception {
        when(clienteService.guardar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.correo").value("juan@email.com"))
                .andExpect(jsonPath("$.direccionEnvio").value("Calle 123"))
                .andExpect(jsonPath("$.telefono").value("123456789"));
    }

    @Test
    public void testDeleteCliente() throws Exception {
        doNothing().when(clienteService).eliminar(1);

        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente eliminado con éxito"));

        verify(clienteService, times(1)).eliminar(1);
    }
}
