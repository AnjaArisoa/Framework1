package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import etu2007.annotation.Url;
import etu2007.framework.ModelView;

public class Formulaire {
    Double nom;
    String prenom;
  

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public  Double getNom() {
        return nom;
    }

    public void setNom(Double nom) {
        this.nom = nom;
    }
    public Formulaire() {
    }
    @Url(url="form-save")
    public ModelView save(){
		ModelView mv= new ModelView();
        mv.setView("../formulaire.jsp");
		return mv;
    }
     @Url(url="form-savee")
    public ModelView savee(){
		ModelView mv= new ModelView();
        mv.setView("formulaire.jsp");
		System.out.println(this.getNom());
		return mv;
    }
  
}
