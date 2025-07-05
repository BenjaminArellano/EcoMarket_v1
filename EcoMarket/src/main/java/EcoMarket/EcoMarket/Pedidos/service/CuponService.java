package EcoMarket.EcoMarket.Pedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EcoMarket.EcoMarket.Pedidos.model.Cupon;
import EcoMarket.EcoMarket.Pedidos.repository.CuponRepository;




@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public List<Cupon> obtenerTodos() {
        return cuponRepository.findAll();
    }

    public Cupon ObtenerPorId(int id) {
        return cuponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cupon no encontrado con ID: " + id));
    }

    public Cupon guardar(Cupon cupon) {

        if (cupon.getCodigo() == null || cupon.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede estar vacío.");
        }

        if (cupon.getDescuento() <= 0 || cupon.getDescuento() > 100) {
        throw new IllegalArgumentException("El descuento debe ser un valor entre 1 y 100.");
        }

        if (cupon.getPedidos() == null || cupon.getPedidos().isEmpty()) {
            throw new IllegalArgumentException("El cupon debe tener un pedido.");
        }

        return cuponRepository.save(cupon);
    }


    public void eliminar(int id) {

        if (!cuponRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar. Cupon con ID " + id + " no existe.");
        }

        cuponRepository.deleteById(id);
    }
}
