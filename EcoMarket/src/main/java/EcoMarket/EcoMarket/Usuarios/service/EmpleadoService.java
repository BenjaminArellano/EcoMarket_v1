package EcoMarket.EcoMarket.Usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Usuarios.model.Empleado;
import EcoMarket.EcoMarket.Usuarios.repository.EmpleadoRepository;




@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    public Empleado ObtenerPorId(int id) {
        return empleadoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));
    }

    public Empleado guardar(Empleado empleado) {

        if (empleado.getUsuario() == null) {
            throw new IllegalArgumentException("El empleado debe tener un usuario asociado.");
        }

        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        if (empleado.getCorreo() == null || empleado.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }

        if (empleado.getCargo() == null || empleado.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("El cargo no puede estar vacío.");
        }

        return empleadoRepository.save(empleado);
    }

    public void eliminar(int id) {

        if (!empleadoRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Atributo con ID " + id + " no existe.");
        }

        empleadoRepository.deleteById(id);
    }

}
