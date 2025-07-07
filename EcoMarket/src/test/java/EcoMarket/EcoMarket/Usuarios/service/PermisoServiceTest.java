package EcoMarket.EcoMarket.Usuarios.service;

import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.repository.PermisoRepository;
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
public class PermisoServiceTest {

    @Autowired
    private PermisoService permisoService;

    @MockBean
    private PermisoRepository permisoRepository;

    @Test
    public void testObtenerTodos() {
        Permiso permiso = crearPermiso();
        when(permisoRepository.findAll()).thenReturn(List.of(permiso));

        List<Permiso> permisos = permisoService.obtenerTodos();
        assertNotNull(permisos);
        assertEquals(1, permisos.size());
        assertEquals(permiso.getId(), permisos.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Permiso permiso = crearPermiso();
        when(permisoRepository.findById(id)).thenReturn(Optional.of(permiso));

        Permiso found = permisoService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(permisoRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            permisoService.ObtenerPorId(id);
        });
        assertEquals("Permiso no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Permiso permiso = crearPermiso();
        when(permisoRepository.save(permiso)).thenReturn(permiso);

        Permiso saved = permisoService.guardar(permiso);
        assertNotNull(saved);
        assertEquals("READ", saved.getNombre());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(permisoRepository.existsById(id)).thenReturn(true);
        doNothing().when(permisoRepository).deleteById(id);

        permisoService.eliminar(id);
        verify(permisoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(permisoRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            permisoService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Permiso con ID " + id + " no existe.", exception.getMessage());
    }

    private Permiso crearPermiso() {
        Permiso permiso = new Permiso();
        permiso.setId(1);
        permiso.setNombre("READ");

        return permiso;
    }
}
