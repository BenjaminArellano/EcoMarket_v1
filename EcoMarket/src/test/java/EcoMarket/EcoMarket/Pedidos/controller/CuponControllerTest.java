package EcoMarket.EcoMarket.Pedidos.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import EcoMarket.EcoMarket.Pedidos.model.Cupon;
import EcoMarket.EcoMarket.Pedidos.service.CuponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(CuponController.class)
public class CuponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuponService cuponService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cupon cupon;

    @BeforeEach
    void setUp() {
        cupon = new Cupon();
        cupon.setId(1);
        cupon.setCodigo("DESC10");
        cupon.setDescuento(10);
    }

    @Test
    public void testGetAllCupones() throws Exception {
        when(cuponService.obtenerTodos()).thenReturn(List.of(cupon));

        mockMvc.perform(get("/api/cupones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].codigo").value("DESC10"))
                .andExpect(jsonPath("$[0].descuento").value(10));
    }

    @Test
    public void testGetCuponById() throws Exception {
        when(cuponService.ObtenerPorId(1)).thenReturn(cupon);

        mockMvc.perform(get("/api/cupones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value("DESC10"))
                .andExpect(jsonPath("$.descuento").value(10));
    }

    @Test
    public void testCreateCupon() throws Exception {
        when(cuponService.guardar(any(Cupon.class))).thenReturn(cupon);

        mockMvc.perform(post("/api/cupones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cupon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value("DESC10"))
                .andExpect(jsonPath("$.descuento").value(10));
    }

    @Test
    public void testUpdateCupon() throws Exception {
        when(cuponService.guardar(any(Cupon.class))).thenReturn(cupon);

        mockMvc.perform(put("/api/cupones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cupon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value("DESC10"))
                .andExpect(jsonPath("$.descuento").value(10));
    }

    @Test
    public void testDeleteCupon() throws Exception {
        doNothing().when(cuponService).eliminar(1);

        mockMvc.perform(delete("/api/cupones/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("cupon eliminado con éxito"));

        verify(cuponService, times(1)).eliminar(1);
    }
}
