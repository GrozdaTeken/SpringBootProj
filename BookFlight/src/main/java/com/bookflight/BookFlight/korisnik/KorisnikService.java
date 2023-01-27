package com.bookflight.BookFlight.korisnik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {
    @Autowired private KorisnikRepository repo;

    public List<Korisnik> izlistajSve(){
        return (List<Korisnik>) repo.findAll();
    }

    public void sacuvaj(Korisnik korisnik) {
        repo.save(korisnik);
    }

    public Korisnik get(Integer id) throws UserNotFoundException {
        Optional<Korisnik> korisnik = repo.findById(id);
        if(korisnik.isPresent()){
            return korisnik.get();
        }
        throw new UserNotFoundException("Ne postoji user sa id: " + id);
    }

    public void obrisiKorisnika(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count == 0) {
            throw new UserNotFoundException("Nije pronadjen niti jedan user sa ID: " + id);
        }
        repo.deleteById(id);
    }
}
