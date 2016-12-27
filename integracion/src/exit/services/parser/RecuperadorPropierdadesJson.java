package exit.services.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RecuperadorPropierdadesJson {
	private static RecuperadorPropierdadesJson instancia=null;
	private JSONObject json;
	private HashMap<String, JSONObject> mapCabeceraJson;
	
	public static RecuperadorPropierdadesJson getInstancia() throws IOException, ParseException{
		if(instancia==null)
			instancia= new RecuperadorPropierdadesJson();
		return instancia;
	}
	private RecuperadorPropierdadesJson() throws IOException, ParseException{
		mapCabeceraJson=new HashMap<String, JSONObject>();
		File f= new File("WebContent/tiposDeDatos.json");
		BufferedReader br= new BufferedReader(new FileReader(f));
		String line;
		StringBuilder sb= new StringBuilder();
		while((line=br.readLine()) != null){
			sb.append(line);
		}
		JSONParser parser = new JSONParser();
		this.json = (JSONObject) parser.parse(sb.toString());
		JSONArray arr=(JSONArray)json.get("informacion");
		for(int i=0;i<arr.size();i++){
			JSONObject aux=(JSONObject)arr.get(i);
			mapCabeceraJson.put((String)aux.get("nombre"), aux);
		}
	}
	public JSONObject getFormato() {
		return json;
	}
}