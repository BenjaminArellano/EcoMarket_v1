package EcoMarket.EcoMarket.Usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Usuarios.model.Usuario;
import EcoMarket.EcoMarket.Usuarios.repository.UsuarioRepository;




@Service    
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario ObtenerPorId(int id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));
    }

    public Usuario guardar(Usuario usuario) {

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre no puede estar vacío.");
        }

        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El Apellido no puede estar vacío.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El Email no puede estar vacío.");
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            throw new IllegalArgumentException("El Contrasena no puede estar vacío.");
        }

        if (usuario.getRol() == null) {
            throw new IllegalArgumentException("El usuario debe tener un Rol.");
        }

        return usuarioRepository.save(usuario);
    }

    public void eliminar(int id) {

        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Atributo con ID " + id + " no existe.");
        }

        usuarioRepository.deleteById(id);
    }

}
