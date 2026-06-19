package gym_proyecto.Gym_pro.dao;

import gym_proyecto.Gym_pro.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.roles WHERE u.username = :username")
    Usuario findByUsername(@Param("username") String username);
}
