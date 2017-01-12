package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.DirectorioManager;
import exit.services.fileHandler.ConstantesGenerales;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public class GetIdsAEliminar extends GetAbstractoGenerico{

	@Override
	protected Object procesarPeticionOK(BufferedReader in, Long id, int responseCode) throws Exception {
			//NO APLICA		
		return null;
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, Long id, int responseCode) throws Exception {
		// NO APLICA
		return null;		
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		StringBuilder builder = new StringBuilder();
		JSONParser parser = new JSONParser();
		String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        String jsonString = builder.toString();
		JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
		JSONArray jsonArrayItems= (JSONArray) jsonObject.get(("items"));
		EliminarGenerico e= new EliminarGenerico();
		System.out.println(jsonArrayItems.size());
		Integer resultado=jsonArrayItems.size();
		ExecutorService workers = Executors.newFixedThreadPool(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo());      	
	    List<Callable<Void>> tasks = new ArrayList<>();
		for(int i=0;i<jsonArrayItems.size();i++){
			final Integer j=i;
			tasks.add(new Callable<Void>() {
		        public Void call() {
    		JSONObject jsonItem;
			jsonItem=(JSONObject)jsonArrayItems.get(j);
			Long id=(Long)jsonItem.get("id");
			e.realizarPeticion(id);
			return null;
		        }
			});
		}
	    workers.invokeAll(tasks);
	    workers.shutdown();
		return resultado; //Devuelve la cantidad de registros encontradas
	}

	@Override
	protected Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
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
        out.println(ConstantesGenerales.SEPARADOR_ERROR_PETICION);
        out.close();	
        return null;
	}
	

}
