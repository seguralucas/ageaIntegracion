package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.fileHandler.DirectorioManager;

public class GetExistFieldURLQueryRightNow extends GetAbstractoGenerico{

	@Override
	Object procesarPeticionOK(BufferedReader in, Long id, int responseCode) throws Exception {
		// No utilizado
		return null;
	}

	@Override
	Object procesarPeticionError(BufferedReader in, Long id, int responseCode) throws Exception {
		// No utilizado
		return null;
	}

	@Override
	Object procesarPeticionOK(BufferedReader in, int responseCode) throws Exception {
		JSONObject jsonObject = ConvertidorJson.convertir(in);
		JSONArray jsonArrayItems= (JSONArray) jsonObject.get("items");
		JSONObject jsonCuerpo=(JSONObject) jsonArrayItems.get(0);
		JSONArray resultados=(JSONArray) jsonCuerpo.get("rows");
		if(resultados.size()>=1)
			return ((JSONArray)resultados.get(0)).get(0);
		return null;
	}

	@Override
	Object procesarPeticionError(BufferedReader in, int responseCode) throws Exception {
		String path=("error_ger_verificarExistencia"+responseCode+".txt");
	    File fichero = DirectorioManager.getDirectorioFechaYHoraInicio(path);
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fichero, true)));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
         	out.println(inputLine);
        }
        out.close();
        return null;
	}

}
