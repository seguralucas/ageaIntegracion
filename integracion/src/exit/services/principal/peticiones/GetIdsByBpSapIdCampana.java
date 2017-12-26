package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public class GetIdsByBpSapIdCampana extends GetAbstractoGenerico {

	@Override
	Object procesarPeticionOK(BufferedReader in, Long id, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Object procesarPeticionError(BufferedReader in, Long id, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		JSONArray jsonArrayItems= (JSONArray) jsonObject.get("items");
		JSONObject jsonCuerpo=(JSONObject) jsonArrayItems.get(0);
		JSONArray resultados=(JSONArray) jsonCuerpo.get("rows");
		if(resultados.size()>5){
			ExecutorService workers = Executors.newFixedThreadPool(3);      	
		    List<Callable<Void>> tasks = new ArrayList<>();		
		    
		    
		    for( int i=0;i<resultados.size();i++){
			final Integer j=i;
			tasks.add(new Callable<Void>() {
	        public Void call() {
				String id= (String)((JSONArray)resultados.get(j)).get(0);
				EliminarGenerico el= new EliminarGenerico();
				el.realizarPeticion(Long.parseLong(id));
				System.out.println(id);
				return null;
			        }
				});
			}
		    workers.invokeAll(tasks);
		    workers.shutdown();
		}
		else{
			for(int i=0;i<resultados.size();i++){
				String id= (String)((JSONArray)resultados.get(i)).get(0);
				EliminarGenerico el= new EliminarGenerico();
				el.realizarPeticion(Long.parseLong(id));
				System.out.println(id);
			}
		}
		return null;

	}


	@Override
	Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
