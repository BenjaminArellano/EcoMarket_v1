package EcoMarket.EcoMarket.Usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Usuarios.model.Cliente;


@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Integer> {

}
