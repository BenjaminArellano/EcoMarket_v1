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

import EcoMarket.EcoMarket.Productos.model.Proveedor;
import EcoMarket.EcoMarket.Productos.service.ProveedorService;



@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> obtenerTodos() {
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Proveedor obtenerPorId(@PathVariable int id) {
        return proveedorService.obtenerPorId(id);
    }

    @PostMapping
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable int id, @RequestBody Proveedor proveedor) {
        proveedor.setId(id);
        return proveedorService.guardar(proveedor);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        proveedorService.eliminar(id);
        return "Proveedor eliminado con éxito";
    }
}