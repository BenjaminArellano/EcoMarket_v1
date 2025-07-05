package EcoMarket.EcoMarket.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Pedidos.model.Cupon;




@Repository
public interface  CuponRepository extends JpaRepository<Cupon, Integer> {

}
