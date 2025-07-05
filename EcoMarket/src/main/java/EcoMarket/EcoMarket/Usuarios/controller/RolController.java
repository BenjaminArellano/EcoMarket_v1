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

import EcoMarket.EcoMarket.Usuarios.model.Rol;
import EcoMarket.EcoMarket.Usuarios.service.RolService;


@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Rol> obtenerTodos() {
        return rolService.obtenerTodos();
    }

    @PostMapping
    public Rol guardar(@RequestBody Rol rol) {
        return rolService.guardar(rol);
    }

    @GetMapping("/{id}")
    public Rol obtenerPorId(@PathVariable int id) {
        return rolService.ObtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Rol actualizar(@PathVariable int id, @RequestBody Rol rol) {
        rol.setId(id);
        return rolService.guardar(rol);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        rolService.eliminar(id);
        return "rol eliminado con éxito";
    }

}
