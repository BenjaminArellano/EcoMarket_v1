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

import EcoMarket.EcoMarket.Usuarios.model.Empleado;
import EcoMarket.EcoMarket.Usuarios.service.EmpleadoService;


@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;
    
    @GetMapping
    public List<Empleado> obtenerTodos() {
        return empleadoService.obtenerTodos();
    }

    @PostMapping
    public Empleado guardar(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    @GetMapping("/{id}")
    public Empleado obtenerPorId(@PathVariable int id) {
        return empleadoService.ObtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoService.guardar(empleado);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        empleadoService.eliminar(id);
        return "Empleado eliminado con éxito";
    }
}
