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
        return inventarioRepository.findById(id).orElse(null);
    }

    public Inventario guardar(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public void eliminar(int id) {
        inventarioRepository.deleteById(id);
    }
}