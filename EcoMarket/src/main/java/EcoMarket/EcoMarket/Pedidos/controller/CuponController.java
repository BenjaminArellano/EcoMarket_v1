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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cupones")
@Tag(name = "Cupones", description = "Operaciones relacionadas con la gestión de cupones de descuento")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping
    @Operation(summary = "Obtener todos los cupones", description = "Obtiene una lista de todos los cupones de descuento disponibles en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cupones obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Cupon> obtenerTodos() {
        return cuponService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cupón por ID", description = "Obtiene un cupón específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupón encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Cupon obtenerPorId(@PathVariable int id) {
        return cuponService.ObtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cupón", description = "Crea un nuevo cupón de descuento en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupón creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Cupon guardar(@RequestBody Cupon cupon) {
        return cuponService.guardar(cupon);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cupón", description = "Actualiza la información de un cupón existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupón actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Cupon actualizar(@PathVariable int id, @RequestBody Cupon cupon) {
        cupon.setId(id);
        return cuponService.guardar(cupon);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupón eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        cuponService.eliminar(id);
        return "cupon eliminado con éxito";
    }

}
