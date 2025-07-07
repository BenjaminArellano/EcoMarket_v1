package EcoMarket.EcoMarket.Pedidos.service;

import EcoMarket.EcoMarket.Pedidos.model.Envio;
import EcoMarket.EcoMarket.Pedidos.model.Pedido;
import EcoMarket.EcoMarket.Pedidos.repository.EnvioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EnvioServiceTest {

    @Autowired
    private EnvioService envioService;

    @MockBean
    private EnvioRepository envioRepository;

    @Test
    public void testObtenerTodos() {
        Envio envio = crearEnvio();
        when(envioRepository.findAll()).thenReturn(List.of(envio));

        List<Envio> envios = envioService.obtenerTodos();
        assertNotNull(envios);
        assertEquals(1, envios.size());
        assertEquals(envio.getId(), envios.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Envio envio = crearEnvio();
        when(envioRepository.findById(id)).thenReturn(Optional.of(envio));

        Envio found = envioService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(envioRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            envioService.ObtenerPorId(id);
        });
        assertEquals("Envio no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Envio envio = crearEnvio();
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio saved = envioService.guardar(envio);
        assertNotNull(saved);
        assertEquals("Calle 123", saved.getDireccion());
        assertEquals("En tránsito", saved.getEstado());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(envioRepository.existsById(id)).thenReturn(true);
        doNothing().when(envioRepository).deleteById(id);

        envioService.eliminar(id);
        verify(envioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(envioRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            envioService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Envio con ID " + id + " no existe.", exception.getMessage());
    }

    private Envio crearEnvio() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        Envio envio = new Envio();
        envio.setId(1);
        envio.setDireccion("Calle 123");
        envio.setEstado("En tránsito");
        envio.setPedido(pedido);

        return envio;
    }
}
