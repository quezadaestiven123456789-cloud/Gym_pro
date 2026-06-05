package gym_proyecto.Gym_pro.dao;

import gym_proyecto.Gym_pro.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaDao extends JpaRepository<Persona, Long> {

}
