import com.marketplace.security.service.JwtService;
import com.marketplace.security.model.AuthResponse;
import com.marketplace.security.model.LoginRequest;
import com.marketplace.security.model.RegisterRequest;
import com.marketplace.models.User;
import com.marketplace.models.Role;
import com.marketplace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails user = userRepository.findByUsername(request.getUsername())
            .orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .correoElectronico(request.getCorreoElectronico())
            .direccion(request.getDireccion())
            .ciudad(request.getCiudad())
            .pais(request.getPais())
            .telefono(request.getTelefono())
            .role(Role.USER)
            .estado("Activo")
            .build();
        userRepository.save(user);
        return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }
}
