package com.bookflight.BookFlight.auth;

import com.bookflight.BookFlight.config.JwtService;
import com.bookflight.BookFlight.korisnik.Korisnik;
import com.bookflight.BookFlight.korisnik.KorisnikRepository;
import com.bookflight.BookFlight.korisnik.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final KorisnikRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenicationResponse register(RegisterRequest request) {
        var user = Korisnik.builder()
                .ime(request.getFirstname())
                .prezime(request.getLastname())
                .email(request.getEmail())
                .sifra(passwordEncoder.encode(request.getPassword()))
                .role(Role.KORISNIK)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenicationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenicationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenicationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
