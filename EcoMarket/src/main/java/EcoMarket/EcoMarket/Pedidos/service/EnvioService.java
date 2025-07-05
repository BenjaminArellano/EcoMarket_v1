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

        if (envio.getDireccion() == null || envio.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La direccion no puede estar vacía.");
        }

        if (envio.getEstado() == null || envio.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }

        if (envio.getPedido() == null) {
            throw new IllegalArgumentException("El envio debe tener un pedido.");
        }


        return envioRepository.save(envio);
    }

    public void eliminar(int id) {

        if (!envioRepository.existsById(id)) {
        throw new IllegalArgumentException("No se puede eliminar. Envio con ID " + id + " no existe.");
        }
        envioRepository.deleteById(id);
    }
}
