package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.principal.Separadores;
import exit.services.singletons.ApuntadorDeEntidad;

public class GetIdsAEliminar extends GetAbstractoGenerico{

	@Override
	void procesarPeticionOK(BufferedReader in, Integer id, int responseCode) throws Exception {
			//NO APLICA		
	}

	@Override
	void procesarPeticionError(BufferedReader in, Integer id, int responseCode) throws Exception {
		// NO APLICA
		
	}

	@Override
	void procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	void procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		String path=("error_recuperacion_servidor_codigo_"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        out.println("No se pudo recuerar informacion de la entidad: "+ApuntadorDeEntidad.getInstance().getEntidadActual());
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.escribirCSV("error_recuperacion_servidor_codigo_"+responseCode+".csv", "No se pudo recuerar informacion de la entidad: "+ApuntadorDeEntidad.getInstance().getEntidadActual(),false);            
        out.println(Separadores.SEPARADOR_ERROR_PETICION);
        out.close();		
	}

}
