package com.transportista.liquidaciones.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transportista.liquidaciones.dto.request.LoginRequest;
import com.transportista.liquidaciones.dto.request.RegisterRequest;
import com.transportista.liquidaciones.dto.response.AuthResponse;
import com.transportista.liquidaciones.entity.Usuario;
import com.transportista.liquidaciones.repository.UsuarioRepository;
import com.transportista.liquidaciones.security.JwtService;    


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
    if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new RuntimeException("El email ya está registrado");
    }
    Usuario usuario = new Usuario();
    usuario.setNombre(request.getNombre());
    usuario.setEmail(request.getEmail());
    usuario.setPassword(passwordEncoder.encode(request.getPassword()));
    usuario.setRol("ROLE_USER"); // ← agregá esta línea
    
    usuarioRepository.save(usuario);
    String token = jwtService.generateToken(usuario);
    return new AuthResponse(token);
   }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow();

        String token = jwtService.generateToken(usuario);

        return new AuthResponse(token);
    }
}