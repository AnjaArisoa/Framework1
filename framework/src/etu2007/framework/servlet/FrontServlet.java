package etu2007.framework.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import etu2007.annotation.Url;
import etu2007.framework.Mapping;
import etu2007.framework.ModelView;
import utils.PackageTool;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> urlMapping = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {
            for (Class c : PackageTool.inPackage(getServletConfig().getInitParameter("model"))){
                for (Method m : c.getDeclaredMethods()){
                    if(m.isAnnotationPresent(Url.class)){
                        urlMapping.put(m.getAnnotation(Url.class).url(), new Mapping(c.getName(), m.getAnnotation(Url.class).url().split("-")[1]));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void processRequest(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        String url = req.getRequestURI();
        url = url.split("/")[url.split("/").length - 1];
        out.println(url);
        if(urlMapping.containsKey(url)){
            try {
                Object act = Class.forName(urlMapping.get(url).getClassName()).newInstance();
                Class<?> clazz = act.getClass();
                Field[] fields = clazz.getDeclaredFields();
                Enumeration<String> enumeration = req.getParameterNames();
					ArrayList<String> enumerationList = new ArrayList<String>();
					enumerationList = enumerationToList(enumeration);
                    for (int i = 0; i < fields.length; i++) {
						System.out.println("FIELD: "+fields[i].getName());
						if(checkIfExist(enumerationList, fields[i])==true) {
							System.out.println("EXIST FIELD: "+fields[i].getName());
							Object attributObject = req.getParameter(fields[i].getName());
						    Object objectCast =  castObject(attributObject, fields[i].getType()); 
                            Method m = act.getClass().getDeclaredMethod("set" + capitalize(fields[i].getName()), fields[i].getType());
                            m.invoke(act, objectCast);
                            System.out.println("metyyyy");
							
						}
                    }
                    ModelView mv = (ModelView)act.getClass().getDeclaredMethod(urlMapping.get(url).getMethod()).invoke(act);
                        for (String rl:mv.getData().keySet()){
                            Object valeur = mv.getData().get(rl);
                            req.setAttribute(rl,valeur);
                        }
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher(mv.getView());    
                    requestDispatcher.forward(req,res);
                            
                    
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Object castObject(Object object, Class<?> castType) {
        System.out.print("Tafiditra");
		try {
            if (castType.equals(Integer.TYPE) || castType.equals(Integer.class)) {
                return Integer.parseInt(object.toString());
            } else if (castType.equals(Double.TYPE) || castType.equals(Double.class)) {
                return Double.parseDouble(object.toString());
            } else if (castType.equals(Boolean.TYPE) || castType.equals(Boolean.class)) {
                return Boolean.parseBoolean(object.toString());
            } else if (castType.equals(java.sql.Date.class)) {
            	System.out.println("Cas 1");
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = (Date) df.parse(object.toString());
                System.out.println(date.toString());
                return date;
            } else if (castType.equals(Date.class)) {
            	System.out.println("Cas 2");
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date utilDate = (Date) df.parse(object.toString());
                System.out.println(new java.sql.Date(utilDate.getTime()).toString());
                return new java.sql.Date(utilDate.getTime());
            } else {
                return object;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    
	public static ArrayList<String> enumerationToList(Enumeration<String> enumeration) {
	    ArrayList<String> list = new ArrayList<String>();
	    while (enumeration.hasMoreElements()) {
	        list.add(enumeration.nextElement());
	    }
	    return list;
	}

	public static boolean checkIfExist(ArrayList<String> enumerationList, Field field){
		for(int i = 0; i < enumerationList.size(); i++){
			System.out.println("ENUMERATION: "+enumerationList.get(i)+" field: "+field.getName());
			if(field.getName().compareTo(enumerationList.get(i))==0){
				return true;
			}
		}
		return false;
	}
    public String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);  
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}