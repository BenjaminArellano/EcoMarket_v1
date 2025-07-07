package EcoMarket.EcoMarket.Usuarios.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Usuarios.model.Empleado;
import EcoMarket.EcoMarket.Usuarios.model.Usuario;
import EcoMarket.EcoMarket.Usuarios.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(EmpleadoController.class)
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@email.com");

        empleado = new Empleado();
        empleado.setId(1);
        empleado.setNombre("Juan Pérez");
        empleado.setCorreo("juan@email.com");
        empleado.setCargo("Gerente");
        empleado.setUsuario(usuario);
    }

    @Test
    public void testGetAllEmpleados() throws Exception {
        when(empleadoService.obtenerTodos()).thenReturn(List.of(empleado));

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].correo").value("juan@email.com"))
                .andExpect(jsonPath("$[0].cargo").value("Gerente"));
    }

    @Test
    public void testGetEmpleadoById() throws Exception {
        when(empleadoService.ObtenerPorId(1)).thenReturn(empleado);

        mockMvc.perform(get("/api/empleados/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.correo").value("juan@email.com"))
                .andExpect(jsonPath("$.cargo").value("Gerente"));
    }

    @Test
    public void testCreateEmpleado() throws Exception {
        when(empleadoService.guardar(any(Empleado.class))).thenReturn(empleado);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.correo").value("juan@email.com"))
                .andExpect(jsonPath("$.cargo").value("Gerente"));
    }

    @Test
    public void testUpdateEmpleado() throws Exception {
        when(empleadoService.guardar(any(Empleado.class))).thenReturn(empleado);

        mockMvc.perform(put("/api/empleados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.correo").value("juan@email.com"))
                .andExpect(jsonPath("$.cargo").value("Gerente"));
    }

    @Test
    public void testDeleteEmpleado() throws Exception {
        doNothing().when(empleadoService).eliminar(1);

        mockMvc.perform(delete("/api/empleados/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Empleado eliminado con éxito"));

        verify(empleadoService, times(1)).eliminar(1);
    }
}
