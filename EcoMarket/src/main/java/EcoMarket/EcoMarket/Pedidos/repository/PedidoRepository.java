package EcoMarket.EcoMarket.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Pedidos.model.Pedido;



@Repository
public interface  PedidoRepository extends JpaRepository<Pedido, Integer> {
    

}
