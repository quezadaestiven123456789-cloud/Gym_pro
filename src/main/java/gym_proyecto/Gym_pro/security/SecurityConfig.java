package gym_proyecto.Gym_pro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 private final UserDetailsService userDetailsService;

    // Inyección por constructor: Desacoplada y óptima para pruebas unitarias
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                // Recursos estáticos y login accesibles para todos
                .requestMatchers("/login", "/resources/*", "/css/", "/js/*").permitAll()
                // Operaciones restrictivas para la gestión administrativa
                .requestMatchers("/editar/*", "/agregar/", "/eliminar/*").hasRole("ADMIN")
                // Endpoint raíz accesible para cualquier usuario autenticado
                .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                // Cualquier otra petición requiere autenticación implícita
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true) // Limpia el contexto de seguridad explícitamente
                .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(exception -> exception
                .accessDeniedPage("/errores/403")
                )
                .build();

    }

    /**
     * Proveedor de autenticación configurado para mitigar cambios de
     * inmutabilidad en las últimas especificaciones de Spring Security.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        // Corrección del error de compilación: Pasamos la dependencia por constructor
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        // Inyectamos el componente codificador directamente
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
