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
        return reseniaRepository.findById(id).orElse(null);
    }

    public Resenia guardar(Resenia resenia) {
        return reseniaRepository.save(resenia);
    }

    public void eliminar(int id) {
        reseniaRepository.deleteById(id);
    }
}