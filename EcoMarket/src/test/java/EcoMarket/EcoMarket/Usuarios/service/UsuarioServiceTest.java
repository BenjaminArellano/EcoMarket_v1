package EcoMarket.EcoMarket.Usuarios.service;

import EcoMarket.EcoMarket.Usuarios.model.Usuario;
import EcoMarket.EcoMarket.Usuarios.model.Rol;
import EcoMarket.EcoMarket.Usuarios.repository.UsuarioRepository;
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
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testObtenerTodos() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.obtenerTodos();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals(usuario.getId(), usuarios.get(0).getId());
    }

    @Test
    public void testObtenerPorId() {
        int id = 1;
        Usuario usuario = crearUsuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario found = usuarioService.ObtenerPorId(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        int id = 999;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.ObtenerPorId(id);
        });
        assertEquals("Usuario no encontrado con ID: " + id, exception.getMessage());
    }

    @Test
    public void testGuardar() {
        Usuario usuario = crearUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario saved = usuarioService.guardar(usuario);
        assertNotNull(saved);
        assertEquals("Juan", saved.getNombre());
        assertEquals("Pérez", saved.getApellido());
        assertEquals("juan@email.com", saved.getEmail());
        assertEquals("password123", saved.getContrasena());
    }

    @Test
    public void testGuardarConNombreVacio() {
        Usuario usuario = crearUsuario();
        usuario.setNombre("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(usuario);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConNombreNulo() {
        Usuario usuario = crearUsuario();
        usuario.setNombre(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(usuario);
        });
        assertEquals("El Nombre no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConApellidoVacio() {
        Usuario usuario = crearUsuario();
        usuario.setApellido("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(usuario);
        });
        assertEquals("El Apellido no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConEmailVacio() {
        Usuario usuario = crearUsuario();
        usuario.setEmail("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(usuario);
        });
        assertEquals("El Email no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarConContrasenaVacia() {
        Usuario usuario = crearUsuario();
        usuario.setContrasena("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(usuario);
        });
        assertEquals("El Contrasena no puede estar vacío.", exception.getMessage());
    }

    @Test
    public void testGuardarSinRol() {
        Usuario usuario = crearUsuario();
        usuario.setRol(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardar(usuario);
        });
        assertEquals("El usuario debe tener un Rol.", exception.getMessage());
    }

    @Test
    public void testEliminar() {
        int id = 1;
        when(usuarioRepository.existsById(id)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.eliminar(id);
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    public void testEliminarNoExistente() {
        int id = 999;
        when(usuarioRepository.existsById(id)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.eliminar(id);
        });
        assertEquals("No se puede eliminar. Atributo con ID " + id + " no existe.", exception.getMessage());
    }

    private Usuario crearUsuario() {
        Rol rol = new Rol();
        rol.setId(1);
        rol.setNombre("ADMIN");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@email.com");
        usuario.setContrasena("password123");
        usuario.setRol(rol);

        return usuario;
    }
}
