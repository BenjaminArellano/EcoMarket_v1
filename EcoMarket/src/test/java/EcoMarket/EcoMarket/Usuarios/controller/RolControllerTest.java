package EcoMarket.EcoMarket.Usuarios.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Usuarios.model.Rol;
import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.service.RolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(RolController.class)
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rol;

    @BeforeEach
    void setUp() {
        Permiso permiso = new Permiso();
        permiso.setId(1);
        permiso.setNombre("LEER");
        permiso.setDescripcion("Permiso para leer datos");

        rol = new Rol();
        rol.setId(1);
        rol.setNombre("ADMIN");
        rol.setPermisos(List.of(permiso));
    }

    @Test
    public void testGetAllRoles() throws Exception {
        when(rolService.obtenerTodos()).thenReturn(List.of(rol));

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("ADMIN"));
    }

    @Test
    public void testGetRolById() throws Exception {
        when(rolService.ObtenerPorId(1)).thenReturn(rol);

        mockMvc.perform(get("/api/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("ADMIN"));
    }

    @Test
    public void testCreateRol() throws Exception {
        when(rolService.guardar(any(Rol.class))).thenReturn(rol);

        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("ADMIN"));
    }

    @Test
    public void testUpdateRol() throws Exception {
        when(rolService.guardar(any(Rol.class))).thenReturn(rol);

        mockMvc.perform(put("/api/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("ADMIN"));
    }

    @Test
    public void testDeleteRol() throws Exception {
        doNothing().when(rolService).eliminar(1);

        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("rol eliminado con éxito"));

        verify(rolService, times(1)).eliminar(1);
    }
}
