package EcoMarket.EcoMarket.Usuarios.controller;

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

import EcoMarket.EcoMarket.Usuarios.model.Permiso;
import EcoMarket.EcoMarket.Usuarios.service.PermisoService;


@RestController
@RequestMapping("/api/permisos")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping
    public List<Permiso> obtenerTodos() {
        return permisoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Permiso obtenerPorId(@PathVariable int id) {
        return permisoService.ObtenerPorId(id);
    }

    @PostMapping
    public Permiso guardar(@RequestBody Permiso permiso) {
        return permisoService.guardar(permiso);
    }

    @PutMapping("/{id}")
    public Permiso actualizar(@PathVariable int id, @RequestBody Permiso permiso) {
        permiso.setId(id);
        return permisoService.guardar(permiso);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        permisoService.eliminar(id);
        return "Permiso eliminado con éxito";
    }
}
