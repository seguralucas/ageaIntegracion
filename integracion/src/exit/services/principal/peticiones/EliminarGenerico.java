package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.principal.Separadores;

public class EliminarGenerico extends EliminarAbstractoEntidad {
	@Override
	public void procesarPeticionOK(BufferedReader in, Integer id,int responseCode) throws Exception{
		CSVHandler csv= new CSVHandler();
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(CSVHandler.PATH_BORRADOS_OK);
        csv.escribirCSV(fichero, "Id eliminado: "+id);
	}

	@Override
	public void procesarPeticionError(BufferedReader in,  Integer id, int responseCode) throws Exception{
		String path=("error_insercion_servidor_codigo_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        out.println("No se pudo borrar id: "+id);
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.escribirCSV("error_borrado_servidor_codigo_"+responseCode+".csv", "No se pudo borrar id: "+id,false);            
        out.println(Separadores.SEPARADOR_ERROR_PETICION);
        out.close();
	 }
	
}
