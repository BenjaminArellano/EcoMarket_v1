package EcoMarket.EcoMarket.Productos.model;

import EcoMarket.EcoMarket.Usuarios.model.Cliente;
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
public class Resenia {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int calificacion;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "producto_id") 
    private Producto producto;

    @ManyToOne 
    @JoinColumn(name = "cliente_id") 
    private Cliente cliente;
}