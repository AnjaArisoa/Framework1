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
        ne.add("Test1");
        ne.add("Test2");
        HashMap<String,Object> hash = new HashMap<>();
        hash.put("lst", list);
        mv.setMv(hash);
        return mv;
    }
}
