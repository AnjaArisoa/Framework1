package model;
import etu2007.annotation.Url;
import etu2007.framework.ModelView;

public class Emp {
    @Url(url="emp-insert")
    public ModelView insert(){
        ModelView mv = new ModelView();
        mv.setView("../emplist.jsp");
        return mv;
    }
}
