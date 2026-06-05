package gym_proyecto.Gym_pro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import gym_proyecto.Gym_pro.model.Persona;
import gym_proyecto.Gym_pro.service.PersonaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")

public class ControladorInicio {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/")
    public String inicio(Model model) {
        var personas = personaService.listarPersonas();
        model.addAttribute("personas", personas);
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Model model) {
        model.addAttribute("persona", new Persona());
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("persona", persona);
            return "modificar";
        }
        personaService.guardar(persona);
        return "redirect:/";
    }

    @GetMapping("/editar/{idPersona}")
    public String editar(@PathVariable Long idPersona, Model model) {
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);
        persona = personaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";
    }

    @GetMapping("/eliminar/{idPersona}")
    public String eliminar(@PathVariable Long idPersona) {
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);
        personaService.eliminar(persona);
        return "redirect:/";
    }
}
