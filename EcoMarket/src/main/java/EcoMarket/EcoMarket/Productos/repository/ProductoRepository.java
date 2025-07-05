package EcoMarket.EcoMarket.Productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Productos.model.Producto;



@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
}