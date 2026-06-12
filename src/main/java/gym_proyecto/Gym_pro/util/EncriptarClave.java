package gym_proyecto.Gym_pro.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarClave {

    public static void main(String[] args) {

        String password = "123"; // Contraseña original

        String passwordEncriptado = encriptarPassword(password); // Encriptar la contraseña

        System.out.println("Contraseña original: " + password);
        System.out.println("Contraseña encriptada: " + passwordEncriptado);
    }

    public static String encriptarPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
