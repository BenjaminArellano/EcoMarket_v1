package EcoMarket.EcoMarket.Productos.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre, descripcion, categoria;
    private int precio;

    @ManyToOne
    @JoinColumn(name = "proveedor_id") 
    private Proveedor proveedor;
}