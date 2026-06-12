package gym_proyecto.Gym_pro.service;

import gym_proyecto.Gym_pro.dao.UsuarioDao;
import gym_proyecto.Gym_pro.model.Rol;
import gym_proyecto.Gym_pro.model.Usuario;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userDetailsService")
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Buscando usuario: " + username);
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            log.error("Usuario no encontrado: " + username);
            throw new UsernameNotFoundException(username);
        }
        log.info("Usuario encontrado: " + usuario.getUsername());
        log.info("Password hash: " + usuario.getPassword());
        log.info("Roles: " + usuario.getRoles());
        var roles = new ArrayList<GrantedAuthority>();
        if (usuario.getRoles() != null) {
            for (Rol rol : usuario.getRoles()) {
                log.info("Rol encontrado: " + rol.getNombre());
                roles.add(new SimpleGrantedAuthority(rol.getNombre()));
            }
        }
        log.info("Total roles: " + roles.size());
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
