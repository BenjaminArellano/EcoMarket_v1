package EcoMarket.EcoMarket.Usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Usuarios.model.Rol;



@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {


}
