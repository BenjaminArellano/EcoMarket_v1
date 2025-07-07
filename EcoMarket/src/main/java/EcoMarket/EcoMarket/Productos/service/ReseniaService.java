package EcoMarket.EcoMarket.Productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Productos.model.Resenia;
import EcoMarket.EcoMarket.Productos.repository.ReseniaRepository;


@Service
public class ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    public List<Resenia> obtenerTodos() {
        return reseniaRepository.findAll();
    }

    public Resenia obtenerPorId(int id) {
        return reseniaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Resenia no encontrado con ID: " + id));
    }

    public Resenia guardar(Resenia resenia) {

        if (resenia.getCalificacion() <= 0) {
            throw new IllegalArgumentException("La Calificacion debe ser mayor a 0");
        }

        if (resenia.getComentario() == null || resenia.getComentario().trim().isEmpty()) {
            throw new IllegalArgumentException("El Comentario no puede estar vacío.");
        }

        if (resenia.getProducto() == null) {
            throw new IllegalArgumentException("La resenia debe tener un producto.");
        }

        if (resenia.getCliente() == null) {
            throw new IllegalArgumentException("La resenia debe tener un Cliente.");
        }

        return reseniaRepository.save(resenia);
    }

    public void eliminar(int id) {

        if (!reseniaRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Resenia con ID " + id + " no existe.");
        }

        reseniaRepository.deleteById(id);
    }
}