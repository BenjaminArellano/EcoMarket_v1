package EcoMarket.EcoMarket.Productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerPorId(int id) {
        return proveedorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con ID: " + id));
    }

    public Proveedor guardar(Proveedor proveedor) {

        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre no puede estar vacío.");
        }

        if (proveedor.getContacto() == null || proveedor.getContacto().trim().isEmpty()) {
            throw new IllegalArgumentException("El Contacto no puede estar vacío.");
        }

        if (proveedor.getTelefono() == null || proveedor.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El Telefono no puede estar vacío.");
        }
        return proveedorRepository.save(proveedor);
    }

    public void eliminar(int id) {

        if (!proveedorRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. proveedor con ID " + id + " no existe.");
        }

        proveedorRepository.deleteById(id);
    }
}