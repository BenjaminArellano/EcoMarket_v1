package EcoMarket.EcoMarket.Productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    @Id 
    private Long id;
    private String nombre, contacto, telefono;
}