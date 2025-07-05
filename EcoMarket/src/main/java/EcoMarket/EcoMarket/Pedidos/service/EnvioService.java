package EcoMarket.EcoMarket.Pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Pedidos.model.Envio;
import EcoMarket.EcoMarket.Pedidos.repository.EnvioRepository;



@Service
public class EnvioService {


    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> obtenerTodos() {
        return envioRepository.findAll();
    }

    public Envio ObtenerPorId(int id) {
        return envioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Envio no encontrado con ID: " + id));
    }

    public Envio guardar(Envio envio) {
        return envioRepository.save(envio);
    }

    public void eliminar(int id) {
        envioRepository.deleteById(id);
    }
}
