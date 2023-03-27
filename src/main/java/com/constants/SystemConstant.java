package com.constants;

import java.io.File;
import java.util.HashMap;


public class SystemConstant {
	String sessionName = "user-session";
	String sessionTenantName = "tenant-session";
	String appName = "RENTAL";
	String prefix = "?";
	HashMap<String, String[]> dashboardPaths = new HashMap<String, String[]>();
	public SystemConstant() {
		dashboardPaths.put("/", new String[]{"../dashboard.jsp", "Inicio", "home"});
		dashboardPaths.put("/houses", new String[]{"../dashboard.jsp", "Casas", "houses"});
		dashboardPaths.put("/new-house", new String[]{"../new_house.jsp", "Nuevo Registro", ""});
		dashboardPaths.put("/edit-house", new String[]{"../edit_house.jsp", "Editar", ""});
		dashboardPaths.put("/edit-user", new String[]{"../dashboard.jsp", "Editar usuario", "user"});
	}
	public String getSessionName() {
		return sessionName;
	}
	
	public String getAppName() {
		return appName;
	}
	public String getSessionTenantName() {
		return sessionTenantName;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String errorPath(String pathInfo, String filename) {
		int dots = pathInfo.split("/").length;
    	String path = "";
    	for (int i = 1; i < dots; i++) {
    		path = path + "../";
    	}
    	
    	return path + filename;
	}
	
	public String[] searchPath(String pathInfo) {
		return dashboardPaths.get(pathInfo);
	}
	
    public void getFiles(String path) {
        File folder = new File(path);

        if (folder.isDirectory()) {
            File[] archivos = folder.listFiles();
            System.out.println("Archivos en la carpeta " + path + ":");
            for (File archivo : archivos) {
                System.out.println(archivo.getName());
            }
        } else {
            System.out.println(path + " no es una carpeta válida");
        }
    }
    
    public void cleanFolder(String path) {
	    File folder = new File(path);
	
	    if (folder.exists()) {
	        File[] archivos = folder.listFiles();
	
	        for (File file : archivos) {
	           	file.delete();
	        }
	        System.out.println("Carpeta quedo vacia");
	    } else {
	        System.out.println("La carpeta no existe.");
	    }
    }
    
    public void deleteImages(String path,long house_id) {
    	File folder = new File(path);
    	
    	if (folder.exists()) {
    		File[] archivos = folder.listFiles();
    		
    		for (File file : archivos) {
    			String[] split = file.getName().split("-");
    			boolean mustDelete = split[1].equals(String.valueOf(house_id));    			
    			if(mustDelete) {
    				file.delete();    				
    				System.out.println("Se elimino: "+ file.getName());
    			}
    		}
    	} else {
    		System.out.println("La carpeta no existe.");
    	}
    }

}
