package EcoMarket.EcoMarket.Productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Productos.model.Producto;
import EcoMarket.EcoMarket.Productos.repository.ProductoRepository;



@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(int id) {
        return productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }

    public Producto guardar(Producto producto) {

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre no puede estar vacío.");
        }

        if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar vacía.");
        }

        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoria no puede estar vacía.");
        }

        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El Precio debe ser mayor a 0");
        }

        if (producto.getProveedor() == null) {
            throw new IllegalArgumentException("El producto debe tener un proveedor.");
        }

        return productoRepository.save(producto);
    }

    public void eliminar(int id) {

        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Producto con ID " + id + " no existe.");
        }

        productoRepository.deleteById(id);
    }
}