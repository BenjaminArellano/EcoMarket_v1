package EcoMarket.EcoMarket.Productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Productos.model.Resenia;



@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Long> {

}