package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.json.JSONHandler;
import exit.services.principal.Separadores;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public class InsertarGenerico extends InsertarAbstractoEntidades{

	@Override
	void procesarPeticionOK(BufferedReader in, JSONHandler json, int responseCode) throws Exception {
		CSVHandler csv= new CSVHandler();
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(CSVHandler.PATH_INSERTADOS_OK);
        String inputLine;
        boolean marca = true; //Recuperamos el ID
        String id=null;
        while ((inputLine = in.readLine()) != null) {
        	//out.println(inputLine);
        	if(marca && inputLine.contains("id")){
        		id=inputLine.replaceAll("\"id\": ", "").replaceAll(",", "");
        		marca=false;
        	}
        }
        csv.escribirCSV(fichero, id+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSV()+json.getLine(), "ID"+RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSV()+CSVHandler.cabeceraFichero);        
	}

	@Override
	public void procesarPeticionError(BufferedReader in, JSONHandler json, int responseCode) throws Exception{
		String path=("error_insercion_servidor_codigo_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        out.println(json.toString());
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.escribirCSV("error_insercion_servidor_codigo_"+responseCode+".csv",json);            
        out.println(Separadores.SEPARADOR_ERROR_PETICION);
        out.close();
	 }
}
