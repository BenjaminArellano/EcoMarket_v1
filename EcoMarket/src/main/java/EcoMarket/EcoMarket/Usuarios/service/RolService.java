package EcoMarket.EcoMarket.Usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Usuarios.model.Rol;
import EcoMarket.EcoMarket.Usuarios.repository.RolRepository;



@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    public Rol ObtenerPorId(int id) {
        return rolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + id));
    }

    public Rol guardar(Rol rol) {

        // Removed validation that rol must have permisos
//        if (rol.getPermisos() == null || rol.getPermisos().isEmpty()) {
//            throw new IllegalArgumentException("El rol debe tener un Permiso.");
//        }

        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre no puede estar vacío.");
        }

        return rolRepository.save(rol);
    }

    public void eliminar(int id) {

        if (!rolRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Rol con ID " + id + " no existe.");
        }

        rolRepository.deleteById(id);
    }
}
