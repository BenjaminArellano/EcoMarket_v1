package EcoMarket.EcoMarket.Usuarios.service;

import EcoMarket.EcoMarket.Usuarios.model.Empleado;
import EcoMarket.EcoMarket.Usuarios.model.Usuario;
import EcoMarket.EcoMarket.Usuarios.repository.EmpleadoRepository;
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
public class EmpleadoServiceTest {

    @Autowired
    private EmpleadoService empleadoService;

    @MockBean
    private EmpleadoRepository empleadoRepository;

    @Test
    public void testObtenerTodos() {
        Empleado empleado = crearEmpleado();
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));

        List<Empleado> empleados = empleadoService.obtenerTodos();
        assertNotNull(empleados);
        assertEquals(1, empleados.size());
        assertEquals(empleado.getId(), empleados.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Empleado empleado = crearEmpleado();
        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));

        Empleado found = empleadoService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(empleadoRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            empleadoService.ObtenerPorId(id);
        });
        assertEquals("Empleado no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Empleado empleado = crearEmpleado();
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        Empleado saved = empleadoService.guardar(empleado);
        assertNotNull(saved);
        assertEquals("Juan Pérez", saved.getNombre());
        assertEquals("Vendedor", saved.getCargo());
        assertEquals("juan@empresa.com", saved.getCorreo());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(empleadoRepository.existsById(id)).thenReturn(true);
        doNothing().when(empleadoRepository).deleteById(id);

        empleadoService.eliminar(id);
        verify(empleadoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(empleadoRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            empleadoService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Empleado con ID " + id + " no existe.", exception.getMessage());
    }

    private Empleado crearEmpleado() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");

        Empleado empleado = new Empleado();
        empleado.setId(1);
        empleado.setNombre("Juan Pérez");
        empleado.setCargo("Vendedor");
        empleado.setCorreo("juan@empresa.com");
        empleado.setUsuario(usuario);

        return empleado;
    }
}
