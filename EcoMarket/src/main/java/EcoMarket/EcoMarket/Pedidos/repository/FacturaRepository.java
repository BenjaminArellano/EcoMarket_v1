package EcoMarket.EcoMarket.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Pedidos.model.Factura;



@Repository
public interface  FacturaRepository extends JpaRepository<Factura, Integer> {

}
