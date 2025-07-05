package EcoMarket.EcoMarket.Productos.controller;

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

import EcoMarket.EcoMarket.Productos.model.Inventario;
import EcoMarket.EcoMarket.Productos.service.InventarioService;



@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> obtenerTodos() {
        return inventarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Inventario obtenerPorId(@PathVariable int id) {
        return inventarioService.obtenerPorId(id);
    }

    @PostMapping
    public Inventario guardar(@RequestBody Inventario inventario) {
        return inventarioService.guardar(inventario);
    }

    @PutMapping("/{id}")
    public Inventario actualizar(@PathVariable int id, @RequestBody Inventario inventario) {
        inventario.setId(id);
        return inventarioService.guardar(inventario);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        inventarioService.eliminar(id);
        return "Inventario eliminado con éxito";
    }
}