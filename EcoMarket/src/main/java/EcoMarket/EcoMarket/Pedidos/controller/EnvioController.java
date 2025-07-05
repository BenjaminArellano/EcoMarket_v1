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

import EcoMarket.EcoMarket.Pedidos.model.Envio;
import EcoMarket.EcoMarket.Pedidos.service.EnvioService;


@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public List<Envio> obtenerTodos() {
        return envioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Envio obtenerPorId(@PathVariable int id) {
        return envioService.ObtenerPorId(id);
    }

    @PostMapping
    public Envio guardar(@RequestBody Envio envio) {
        return envioService.guardar(envio);
    }

    @PutMapping("/{id}")
    public Envio actualizar(@PathVariable int id, @RequestBody Envio envio) {
        envio.setId(id);
        return envioService.guardar(envio);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        envioService.eliminar(id);
        return "Envio eliminado con éxito";
    }

}
