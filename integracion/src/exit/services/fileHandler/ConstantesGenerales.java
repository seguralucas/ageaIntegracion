package exit.services.fileHandler;

import java.io.File;
import java.net.URISyntaxException;

public class ConstantesGenerales {
	 public final static String SEPARADOR_ERROR_PETICION = "##############################################################################################";
	 public final static String SEPARADOR_ERROR_JSON = "**********************************************************************************************";
	 public final static String SEPARADOR_ERROR_TRYCATCH = "---------------------------------------------------------------------";
	 public static String PATH_EJECUCION="";
	 public static String PATH_CONFIGURACION=PATH_EJECUCION+"/Configuracion";
	 
	 
	 static{
		 try {
			String aux=new File(ConstantesGenerales.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getAbsolutePath(); //Dos puntos porque te lo deja en /bin
			String auxs[]=aux.split("\\\\");
			if(auxs.length==1)
				auxs=aux.split("\\/");
			System.out.println(aux);
			PATH_EJECUCION="";
			for(int i=0;i<auxs.length-1;i++)
				PATH_EJECUCION+=auxs[i]+"/";
			PATH_EJECUCION=PATH_EJECUCION.substring(0, PATH_EJECUCION.length());
			System.out.println(PATH_EJECUCION);
			PATH_CONFIGURACION=PATH_EJECUCION+"/Configuracion";
			 
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
}
