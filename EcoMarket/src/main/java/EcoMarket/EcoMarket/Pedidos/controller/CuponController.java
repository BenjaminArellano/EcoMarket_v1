package EcoMarket.EcoMarket.Pedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EcoMarket.EcoMarket.Pedidos.model.Cupon;
import EcoMarket.EcoMarket.Pedidos.service.CuponService;


@RestController
@RequestMapping("/api/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping
    public List<Cupon> obtenerTodos() {
        return cuponService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Cupon obtenerPorId(@PathVariable int id) {
        return cuponService.ObtenerPorId(id);
    }

    @PostMapping
    public Cupon guardar(@RequestBody Cupon cupon) {
        return cuponService.guardar(cupon);
    }

    @PutMapping("/{id}")
    public Cupon actualizar(@PathVariable int id, @RequestBody Cupon cupon) {
        cupon.setId(id);
        return cuponService.guardar(cupon);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        cuponService.eliminar(id);
        return "cupon eliminado con éxito";
    }

}
