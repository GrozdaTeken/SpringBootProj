package com.bookflight.BookFlight.korisnik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class KorisnikController {

    @Autowired private KorisnikService service;

    @GetMapping("/users")
    public String prikaziListuKorisnika(Model model){
        List<Korisnik> listaKorisnika = service.izlistajSve();
        model.addAttribute("prikaziListuKorisnika", listaKorisnika);
        return "users";
    }

    @GetMapping("/users/new")
    public String prikaziNovuFormu(Model model){
        model.addAttribute("korisnik", new Korisnik());
        model.addAttribute("pageTitle", "Dodaj novog korisnika");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String sacuvajKorisnika(Korisnik korisnik, RedirectAttributes ra){
        service.sacuvaj(korisnik);
        ra.addFlashAttribute("message", "Korisnik je uspesno dodat.");

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String prikaziEditFormu(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Korisnik korisnik = service.get(id);
            model.addAttribute("korisnik", korisnik);
            model.addAttribute("pageTitle", "Izmeni korisnika (ID: " + id + ")");

            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", "Korisnik je uspesno izmenjen.");

            return "redirect:/users";
        }
    }

    @GetMapping("users/delete/{id}")
    public String obrisiKorisnika(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try{
            service.obrisiKorisnika(id);
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", "Korisnik je uspesno obrisan.");
        }
        return "redirect:/users";
    }
}
