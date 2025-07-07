package EcoMarket.EcoMarket.Usuarios.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.service.PermisoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(PermisoController.class)
public class PermisoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermisoService permisoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Permiso permiso;

    @BeforeEach
    void setUp() {
        permiso = new Permiso();
        permiso.setId(1);
        permiso.setNombre("LEER");
        permiso.setDescripcion("Permiso para leer datos");
    }

    @Test
    public void testGetAllPermisos() throws Exception {
        when(permisoService.obtenerTodos()).thenReturn(List.of(permiso));

        mockMvc.perform(get("/api/permisos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("LEER"))
                .andExpect(jsonPath("$[0].descripcion").value("Permiso para leer datos"));
    }

    @Test
    public void testGetPermisoById() throws Exception {
        when(permisoService.ObtenerPorId(1)).thenReturn(permiso);

        mockMvc.perform(get("/api/permisos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("LEER"))
                .andExpect(jsonPath("$.descripcion").value("Permiso para leer datos"));
    }

    @Test
    public void testCreatePermiso() throws Exception {
        when(permisoService.guardar(any(Permiso.class))).thenReturn(permiso);

        mockMvc.perform(post("/api/permisos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permiso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("LEER"))
                .andExpect(jsonPath("$.descripcion").value("Permiso para leer datos"));
    }

    @Test
    public void testUpdatePermiso() throws Exception {
        when(permisoService.guardar(any(Permiso.class))).thenReturn(permiso);

        mockMvc.perform(put("/api/permisos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permiso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("LEER"))
                .andExpect(jsonPath("$.descripcion").value("Permiso para leer datos"));
    }

    @Test
    public void testDeletePermiso() throws Exception {
        doNothing().when(permisoService).eliminar(1);

        mockMvc.perform(delete("/api/permisos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Permiso eliminado con éxito"));

        verify(permisoService, times(1)).eliminar(1);
    }
}
