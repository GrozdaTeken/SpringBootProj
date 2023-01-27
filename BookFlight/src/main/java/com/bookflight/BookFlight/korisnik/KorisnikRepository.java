package com.bookflight.BookFlight.korisnik;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    public Long countById(Integer id);

    Optional<Korisnik> findByEmail(String email);

}
