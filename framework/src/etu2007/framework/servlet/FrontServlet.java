package etu2007.framework.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import etu2007.framework.FileUpload;
import javax.servlet.annotation.MultipartConfig;
import java.util.Collection;
import etu2007.annotation.Url;
import etu2007.framework.Mapping;
import etu2007.framework.ModelView;
import utils.PackageTool;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;


@MultipartConfig
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
    public ArrayList<String> change(String[] strs){
        ArrayList<String> retour =new ArrayList<>();    
        for (String string : strs) {
            retour.add(string);
        }
        return retour;
    }

    public ArrayList<String> parametre(HttpServletRequest req,Method method){
        ArrayList<String> s = list(req);
        ArrayList<String> s1 = new ArrayList<>();
        try {
            Url annot = method.getAnnotation(Url.class);
            String[] st = annot.param();
            if (s.containsAll(change(st))) {
                    for (String string : st) {
                        if (s.contains(string)) {
                            s1.add(string);
                        }
                    }               
            }
        } catch (Exception e) {
            
        }
        return s1;
    }
    public ArrayList<String> getValeurParam(HttpServletRequest req,Method met){
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list1 = parametre(req, met);
        for (String string : list1) {
            list.add(req.getParameter(string));
        }
        return list;
    }

    public ArrayList<String> list(HttpServletRequest req){
        ArrayList<String> array = new ArrayList<>();
        Enumeration<String> nom = req.getParameterNames();
        while (nom.hasMoreElements()) {
                    array.add(nom.nextElement());
                }
        return array;
    }
    public static Method getMethode(Class modelClass, String methodName, String annotationName) {
    Method[] methods = modelClass.getDeclaredMethods();

        try {
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase(methodName)) {
                    if (method.isAnnotationPresent(Url.class)) {
                        Url annotation = method.getAnnotation(Url.class);
                        int parameterCount = annotation.param().length;

                        if (method.getParameterCount() == parameterCount) {
                            return method;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    protected void processRequest(HttpServletRequest req,HttpServletResponse res) throws IOException{
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        String url = req.getRequestURI();
        url = url.split("/")[url.split("/").length - 1];
        out.println(url);
        if(urlMapping.containsKey(url)){
            try {
    
//FileUpload
            if (req.getContentType() != null) {
                ArrayList<FileUpload> allUploads = new ArrayList<FileUpload>();
                Collection<Part> parts = req.getParts();
                if (parts != null) {
                    for (Part part : parts) {
                       String fileName = part.getName();
                        Part filePart = req.getPart(fileName);
                        InputStream in = filePart.getInputStream();
                        byte[] fileBytes = in.readAllBytes();
                        String directory = "./uploads/";
                        File file = new File(directory);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        FileUpload fileUpload = new FileUpload();
                        fileUpload.setFileName(fileName);
                        fileUpload.setPath(directory);
                        fileUpload.setBytes(fileBytes);
                        allUploads.add(fileUpload);
                        }
                    }
                    System.out.println(allUploads);
                    req.setAttribute("uploads", allUploads);
                }
//sprint 6  
                ArrayList<String> get = new ArrayList<>();
                Object act = Class.forName(urlMapping.get(url).getClassName()).newInstance();
                Method methody = getMethode(act.getClass(),urlMapping.get(url).getMethod(), url);
                get=getValeurParam(req, methody);
                ModelView mv =(ModelView)methody.invoke(act,get.toArray());
                for(String cle:mv.getData().keySet()){
                    Object valeur=mv.getData().get(cle);
                    req.setAttribute(cle, valeur);
                }

//sprint 7
                ArrayList<String> array = new ArrayList<>();
                ArrayList<String> don = new ArrayList<>();
                Field[] attribut = act.getClass().getDeclaredFields();
                Method[] methods = act.getClass().getDeclaredMethods();                
                array=list(req);

                    for (Field i : attribut) {
                        don.add(i.getName());
                    }
                    for (String string : array) {
                        if (don.contains(string)) {
                            for (Method method : methods) {
                                String setters = "set"+string;
                                if (method.getName().equalsIgnoreCase(setters)) {
                                    method.invoke(act, req.getParameter(string));
                                }
                            }
                        }
                    }

                for (String key : mv.getData().keySet()) {
                    Object valeur=mv.getData().get(key);
                    req.setAttribute(key, valeur);
                    out.println("ito"+String.valueOf(valeur));
                }
                req.setAttribute("objet", act);


                RequestDispatcher requestDispatcher = req.getRequestDispatcher(mv.getView()) ;    
                requestDispatcher.forward(req,res);
            } catch (Exception e) {
                    e.printStackTrace();
                    out.println(e);
            }
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        processRequest(req, res);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        processRequest(req, res);
    }
}