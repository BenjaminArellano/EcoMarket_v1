package EcoMarket.EcoMarket.Productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Productos.model.Proveedor;



@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

}