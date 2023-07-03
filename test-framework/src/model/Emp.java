package model;
import etu2007.annotation.Url;
import etu2007.framework.ModelView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Emp {
    @Url(url="emp-insert")
    public ModelView insert(){
        ModelView mv = new ModelView();
        mv.setView("../emplist.jsp");
        List<String> list = new ArrayList<>();
        list.add("Test1");
        list.add("Test2");
        HashMap<String,Object> hash = new HashMap<>();
        hash.put("data", list);
        mv.setData(hash);
        return mv;
    }
     @Url(url="emp-lien",parametre={"id","nom"})
    public ModelView lien(Object id,Object nom){
        String idd=id.toString();
        String s=nom.toString();
        System.out.println(idd+s);
        ModelView mv = new ModelView();
        mv.setView("../emplist.jsp");
        List<String> list = new ArrayList<>();
        list.add(idd);
        list.add(s);
        HashMap<String,Object> hash = new HashMap<>();
        hash.put("data", list);
        mv.setData(hash);
        return mv;
    }
}
