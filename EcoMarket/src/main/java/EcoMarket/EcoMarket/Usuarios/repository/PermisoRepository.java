package EcoMarket.EcoMarket.Usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Usuarios.model.Permiso;



@Repository
public interface  PermisoRepository extends JpaRepository<Permiso, Integer> {


}
