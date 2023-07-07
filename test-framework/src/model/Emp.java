package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import etu2007.annotation.Url;
import etu2007.annotation.Scope;
import etu2007.framework.ModelView;

@Scope(scope="singleton")
public class Emp {
    int id;
    String nom;

    @Url(url="emp-insert" )
    public ModelView insert(){
        ModelView mv = new ModelView();
        mv.setView("../emplist.jsp");
        List<String> noms = new ArrayList<>();
        noms.add("notia");
        noms.add("test");
        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put("donne",nom);
        mv.setData(hashmap);
        return mv;
    }
    
    @Url(url="emp-testParam",param={"idNom","age"})
    public ModelView testParam(String idNom,String age){
        String id = idNom;
        String year = age;
        ModelView mv = new ModelView();
        mv.setView("../emplist.jsp");
        HashMap<String,Object> hashmap = new HashMap<>();
        hashmap.put("ids",id);
        hashmap.put("ages",year);
        mv.setData(hashmap);
        return mv;
    }
     @Url(url="emp-testUpload")
    public ModelView testUpload(){      
        ModelView mv = new ModelView();
        mv.setView("../welcome.jsp");
        return mv;
    }
     @Url(url="emp-testt")
    public ModelView testt(){      
        ModelView mv = new ModelView();
        mv.setView("../empll.jsp");
        return mv;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
