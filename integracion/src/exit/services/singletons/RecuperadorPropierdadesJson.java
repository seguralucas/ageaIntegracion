package exit.services.singletons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConstantesGenerales;

public class RecuperadorPropierdadesJson {
	private static RecuperadorPropierdadesJson instancia=null;
	private JSONObject jsonPropiedades;
	private HashMap<String, JSONObject> mapCabeceraJson;
	public static final String PROPIEDAD_TIPO="tipo";
	public static final String PROPIEDAD_BORRAR_CAR_NO_NUMERICOS="borrarCarNoNumericos";
	public static final String TIPO_FECHA="fecha";
	public static final String TIPO_ENTERO="entero";
	public static final String TIPO_CADENA="cadena";
	public static RecuperadorPropierdadesJson getInstancia(){
		if(instancia==null)
			instancia= new RecuperadorPropierdadesJson();
		return instancia;
	}
	
	public JSONObject getPropiedades(String key){
		return mapCabeceraJson.get(key);
	}
	
	public String getTipo(String key){
		JSONObject j= RecuperadorPropierdadesJson.getInstancia().getPropiedades(key);
		return j==null?"":(String)j.get(PROPIEDAD_TIPO);
	}
	
	private RecuperadorPropierdadesJson(){
		mapCabeceraJson=new HashMap<String, JSONObject>();
		File f= new File(ConstantesGenerales.PATH_CONFIGURACION+"/"+ApuntadorDeEntidad.getInstance().getEntidadActual()+"/tiposDeDatos.json");
		try(BufferedReader br= new BufferedReader(new FileReader(f))){
			String line;
			StringBuilder sb= new StringBuilder();
			while((line=br.readLine()) != null){
				sb.append(line);
			}
			JSONParser parser = new JSONParser();
			this.jsonPropiedades = (JSONObject) parser.parse(sb.toString());
	
			for (Object key : this.jsonPropiedades.keySet()) {
		        String keyStr = (String)key;
		        Object keyvalue = this.jsonPropiedades.get(keyStr);
		        if (keyvalue instanceof JSONObject)
		        	mapCabeceraJson.put(keyStr, (JSONObject)keyvalue);
		    }
		}
		catch(Exception e){
			CSVHandler csv= new CSVHandler();
			e.printStackTrace();
			csv.escribirErrorException(e.getStackTrace());
		}

		/*JSONArray arr=(JSONArray)json.get("informacion");
		for(int i=0;i<arr.size();i++){
			JSONObject aux=(JSONObject)arr.get(i);
			mapCabeceraJson.put((String)aux.get("nombre"), aux);
		}*/
	}
	
	void reiniciar(){
		instancia=null;
	}
	
	public boolean isBorrarCarNoNumericos(String key){
		JSONObject j= RecuperadorPropierdadesJson.getInstancia().getPropiedades(key);
		Object aux=j==null?null:j.get(PROPIEDAD_BORRAR_CAR_NO_NUMERICOS);
		return aux==null?false:(Boolean)j.get(PROPIEDAD_BORRAR_CAR_NO_NUMERICOS);
	}
	
	public JSONObject getJsonPropiedades() {
		return jsonPropiedades;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		JSONObject a=RecuperadorPropierdadesJson.getInstancia().getPropiedades("BP_WCC_ID");
				System.out.println(a.get("tipo"));
	}
}
