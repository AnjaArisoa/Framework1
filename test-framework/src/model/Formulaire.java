package model;

import etu2007.annotation.Url;
import etu2007.framework.ModelView;

public class Formulaire {
     String nom;

    public  String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    @Url(url="form-save")
    public void save(String nom){
        this.setNom(nom);
        System.out.println(this.getNom());
    }
    @Url(url="formulaire-insert")
    public ModelView insert(){
        ModelView mv = new ModelView();
        mv.setView("../formulaire.jsp");
        return mv;
    }
}
