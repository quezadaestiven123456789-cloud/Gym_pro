package gym_proyecto.Gym_pro.service;

import java.util.List;

import gym_proyecto.Gym_pro.model.Persona;

public interface PersonaService {
    public List<Persona> listarPersonas();

    public void guardar(Persona persona);

    public void eliminar(Persona persona);

    public Persona encontrarPersona(Persona persona);
}
