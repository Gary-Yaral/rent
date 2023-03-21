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
		dashboardPaths.put("/rental", new String[]{"../dashboard.jsp", "Rentas", "rental"});
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

}
