package EcoMarket.EcoMarket.Usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Usuarios.model.Empleado;



@Repository
public interface  EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
