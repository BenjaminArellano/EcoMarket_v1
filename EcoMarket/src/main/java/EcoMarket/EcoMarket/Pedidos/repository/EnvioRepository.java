package EcoMarket.EcoMarket.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Pedidos.model.Envio;



@Repository
public interface  EnvioRepository extends JpaRepository<Envio, Integer> {
    

}
