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

import EcoMarket.EcoMarket.Productos.model.Resenia;
import EcoMarket.EcoMarket.Productos.service.ReseniaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/resenias")
@Tag(name = "Reseñas", description = "Operaciones relacionadas con la gestión de reseñas y calificaciones de productos")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @GetMapping
    @Operation(summary = "Obtener todas las reseñas", description = "Obtiene una lista de todas las reseñas y calificaciones de productos en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Resenia> obtenerTodos() {
        return reseniaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID", description = "Obtiene una reseña específica mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Resenia obtenerPorId(@PathVariable int id) {
        return reseniaService.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nueva reseña", description = "Crea una nueva reseña y calificación de producto en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Resenia guardar(@RequestBody Resenia resenia) {
        return reseniaService.guardar(resenia);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña", description = "Actualiza la información de una reseña existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Resenia actualizar(@PathVariable int id, @RequestBody Resenia resenia) {
        resenia.setId(id);
        return reseniaService.guardar(resenia);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        reseniaService.eliminar(id);
        return "Reseña eliminada con éxito";
    }
}
