package EcoMarket.EcoMarket.Usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.repository.PermisoRepository;




@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> obtenerTodos() {
        return permisoRepository.findAll();
    }

    public Permiso ObtenerPorId(int id) {
        return permisoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Permiso no encontrado con ID: " + id));
    }

    public Permiso guardar(Permiso permiso) {

        if (permiso.getNombre() == null || permiso.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre no puede estar vacío.");
        }

        if (permiso.getDescripcion() == null || permiso.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("El Descripcion no puede estar vacío.");
        }

        if (permiso.getRoles() == null || permiso.getRoles().isEmpty()) {
            throw new IllegalArgumentException("El permiso debe tener Roles.");
        }

        return permisoRepository.save(permiso);
    }

    public void eliminar(int id) {

        if (!permisoRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Permiso con ID " + id + " no existe.");
        }

        permisoRepository.deleteById(id);
    }

}
