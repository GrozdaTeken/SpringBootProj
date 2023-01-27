package com.bookflight.BookFlight;

import com.bookflight.BookFlight.korisnik.Korisnik;
import com.bookflight.BookFlight.korisnik.KorisnikRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class KorisnikRepositoryTests {
    @Autowired private KorisnikRepository repo;

    @Test
    public void testDodajKorisnika(){
        Korisnik korisnik = new Korisnik();
        korisnik.setEmail("aleksandar@gmail.com");
        korisnik.setIme("Aleksandar");
        korisnik.setPrezime("Mitrovic");
        korisnik.setSifra("123456");

        Korisnik sacuvanKorisnik = repo.save(korisnik);
    }
    @Test
    public void testListALl(){
        Iterable<Korisnik> korisnici = repo.findAll();

        for(Korisnik korisnik : korisnici) {
            System.out.println(korisnik);
        }
    }

    @Test
    public void testUpdateKorisnik(){
        Integer id = 1;
        Optional<Korisnik> optionalKorisnik = repo.findById(id);
        Korisnik korisnik = optionalKorisnik.get();
        korisnik.setSifra("54321");
        repo.save(korisnik);

        Korisnik updatedKorisnik = repo.findById(id).get();
    }
    @Test
    public void testGet(){
        Integer id = 1;
        Optional<Korisnik> optionalKorisnik = repo.findById(id);
        Korisnik korisnik = optionalKorisnik.get();
        System.out.println(optionalKorisnik.get());
    }

    @Test
    public void obrisiKorisnika(){
        Integer id = 2;
        repo.deleteById(id);
    }
}
