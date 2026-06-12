package gym_proyecto.Gym_pro.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    //Relacion de uno a varios entre las tablas usuario y rol (id_usuario)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private List<Rol> roles;
}
