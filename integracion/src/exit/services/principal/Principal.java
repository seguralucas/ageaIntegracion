package exit.services.principal;


import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.json.simple.parser.ParseException;

import exit.services.fileHandler.DirectorioManager;

import exit.services.parser.RecuperadorPropiedadConfiguracion;
import exit.services.principal.ejecutores.EjecutorInsercionIncidentesDistintosFicheros;

public class Principal {
	public static final String UPDATE_CONTACTOS="UPDATE_CONTACTOS";
	public static final String INSERTAR_CONTACTOS="INSERTAR_CONTACTOS";
	public static final String INSERTAR_INCIDENTES="INSERTAR_INCIDENTES";
	public static final String BORRAR_INCIDENTES="BORRAR_INCIDENTES";
	

	
	
	public static void main(String[] args) throws IOException, ParseException {
   		long time_start, time_end;
    	time_start = System.currentTimeMillis();
    	RecuperadorPropiedadConfiguracion.getInstance().mostrarConfiguracion();
    		EjecutorInsercionIncidentesDistintosFicheros hiloApartre = new EjecutorInsercionIncidentesDistintosFicheros();
	      	try {
	      		hiloApartre.insertar();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	time_end = System.currentTimeMillis();
	    	System.out.println(ManagementFactory.getThreadMXBean().getThreadCount() );
	    	double tiempoDemorado=(time_end - time_start)/1000/60 ;
    		if(tiempoDemorado>1){
        		FileWriter fw = new FileWriter(DirectorioManager.getDirectorioFechaYHoraInicio("duracion.txt"));
    			fw.write("El proceso de updateo demor� un total de: "+tiempoDemorado+" minutos");
        		fw.close();
    		}    	
/***********************************************************/
		//***Borrar ficheros de ejecucion***/
/***********************************************************/
	//	FilesAProcesarManager.getInstance().deleteCSVAProcesar();
	}

}
