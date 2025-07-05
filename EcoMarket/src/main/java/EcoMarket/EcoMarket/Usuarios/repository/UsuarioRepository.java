package EcoMarket.EcoMarket.Usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import EcoMarket.EcoMarket.Usuarios.model.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


}
