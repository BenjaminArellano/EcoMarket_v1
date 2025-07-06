package EcoMarket.EcoMarket.Productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Productos.model.Inventario;
import EcoMarket.EcoMarket.Productos.repository.InventarioRepository;


@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    public Inventario obtenerPorId(int id) {
        return inventarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado con ID: " + id));
    }

    public Inventario guardar(Inventario inventario) {

        if (inventario.getStock() <= 0) {
            throw new IllegalArgumentException("El Stock debe ser mayor a 0");
        }

        if (inventario.getProducto() == null) {
            throw new IllegalArgumentException("El inventario debe tener un producto.");
        }

        return inventarioRepository.save(inventario);
    }

    public void eliminar(int id) {

        if (!inventarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Inventario con ID " + id + " no existe.");
        }

        inventarioRepository.deleteById(id);
    }
}