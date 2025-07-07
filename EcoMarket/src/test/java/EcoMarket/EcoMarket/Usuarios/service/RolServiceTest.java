package EcoMarket.EcoMarket.Usuarios.service;

import EcoMarket.EcoMarket.Usuarios.model.Rol;
import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.repository.RolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RolServiceTest {

    @Autowired
    private RolService rolService;

    @MockBean
    private RolRepository rolRepository;

    @Test
    public void testObtenerTodos() {
        Rol rol = crearRol();
        when(rolRepository.findAll()).thenReturn(List.of(rol));

        List<Rol> roles = rolService.obtenerTodos();
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(rol.getId(), roles.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Rol rol = crearRol();
        when(rolRepository.findById(id)).thenReturn(Optional.of(rol));

        Rol found = rolService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(rolRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolService.ObtenerPorId(id);
        });
        assertEquals("Rol no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Rol rol = crearRol();
        when(rolRepository.save(rol)).thenReturn(rol);

        Rol saved = rolService.guardar(rol);
        assertNotNull(saved);
        assertEquals("ADMIN", saved.getNombre());
        assertNotNull(saved.getPermisos());
        assertEquals(1, saved.getPermisos().size());
    }

    @Test
    public void testGuardarConNombreVacio() {
        Rol rol = crearRol();
        rol.setNombre("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolService.guardar(rol);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConNombreNulo() {
        Rol rol = crearRol();
        rol.setNombre(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolService.guardar(rol);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarSinPermisos() {
        Rol rol = crearRol();
        rol.setPermisos(new ArrayList<>());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolService.guardar(rol);
        });
        assertEquals("El rol debe tener un Permiso.", exception.getMessage());
    }

    @Test
    public void testGuardarConPermisosNulos() {
        Rol rol = crearRol();
        rol.setPermisos(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolService.guardar(rol);
        });
        assertEquals("El rol debe tener un Permiso.", exception.getMessage());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(rolRepository.existsById(id)).thenReturn(true);
        doNothing().when(rolRepository).deleteById(id);

        rolService.eliminar(id);
        verify(rolRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(rolRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rolService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Rol con ID " + id + " no existe.", exception.getMessage());
    }

    private Rol crearRol() {
        Permiso permiso = new Permiso();
        permiso.setId(1);
        permiso.setNombre("READ");

        Rol rol = new Rol();
        rol.setId(1);
        rol.setNombre("ADMIN");
        rol.setPermisos(List.of(permiso));

        return rol;
    }
}
