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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Envíos", description = "Operaciones relacionadas con la gestión de envíos de pedidos")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary = "Obtener todos los envíos", description = "Obtiene una lista de todos los envíos registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public List<Envio> obtenerTodos() {
        return envioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID", description = "Obtiene un envío específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Envio obtenerPorId(@PathVariable int id) {
        return envioService.ObtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo envío", description = "Crea un nuevo envío en el sistema con la información proporcionada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Envio guardar(@RequestBody Envio envio) {
        return envioService.guardar(envio);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío", description = "Actualiza la información de un envío existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Envio actualizar(@PathVariable int id, @RequestBody Envio envio) {
        envio.setId(id);
        return envioService.guardar(envio);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío", description = "Elimina un envío del sistema mediante su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public String eliminar(@PathVariable int id) {
        envioService.eliminar(id);
        return "Envio eliminado con éxito";
    }

}
