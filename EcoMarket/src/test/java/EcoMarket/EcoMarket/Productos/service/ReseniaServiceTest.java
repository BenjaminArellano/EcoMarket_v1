package EcoMarket.EcoMarket.Productos.service;

import EcoMarket.EcoMarket.Productos.model.Resenia;
import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.repository.ReseniaRepository;
import EcoMarket.EcoMarket.Usuarios.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ReseniaServiceTest {

    @Autowired
    private ReseniaService reseniaService;

    @MockBean
    private ReseniaRepository reseniaRepository;

    @Test
    public void testObtenerTodos() {
        Resenia resenia = crearResenia();
        when(reseniaRepository.findAll()).thenReturn(List.of(resenia));

        List<Resenia> resenias = reseniaService.obtenerTodos();
        assertNotNull(resenias);
        assertEquals(1, resenias.size());
        assertEquals(resenia.getId(), resenias.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Resenia resenia = crearResenia();
        when(reseniaRepository.findById(id)).thenReturn(Optional.of(resenia));

        Resenia found = reseniaService.obtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(reseniaRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reseniaService.obtenerPorId(id);
        });
        assertEquals("Resenia no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Resenia resenia = crearResenia();
        when(reseniaRepository.save(resenia)).thenReturn(resenia);

        Resenia saved = reseniaService.guardar(resenia);
        assertNotNull(saved);
        assertEquals("Excelente producto", saved.getComentario());
        assertEquals(5, saved.getCalificacion());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(reseniaRepository.existsById(id)).thenReturn(true);
        doNothing().when(reseniaRepository).deleteById(id);

        reseniaService.eliminar(id);
        verify(reseniaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(reseniaRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reseniaService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Resenia con ID " + id + " no existe.", exception.getMessage());
    }

    private Resenia crearResenia() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan Pérez");

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Producto Test");

        Resenia resenia = new Resenia();
        resenia.setId(1);
        resenia.setComentario("Excelente producto");
        resenia.setCalificacion(5);
        resenia.setCliente(cliente);
        resenia.setProducto(producto);

        return resenia;
    }
}
