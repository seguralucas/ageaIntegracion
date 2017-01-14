package exit.services.procesadorJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public class JsonProcesarReemplazo implements IProcesadorJson {
	private Object valorPorReemplazaR;
	private String cabeceraPorReemplazar;
	public  JsonProcesarReemplazo(Object valorPorReemplazaR, String cabeceraPorReemplazar) {
		this.valorPorReemplazaR=valorPorReemplazaR;
		this.cabeceraPorReemplazar=cabeceraPorReemplazar;
	}
	
	@Override
	public void procesarJson(JSONObject json, String keyValue) {
		Object o= json.get(keyValue);
		if(o instanceof String){
			String identificador=RecuperadorPropiedadedConfiguracionEntidad.getInstance().getIdentificadorAtributo();
			String valor=(String)o;
			if(valor.equalsIgnoreCase(identificador+cabeceraPorReemplazar+identificador))
				json.put(keyValue, valorPorReemplazaR);
		}
	}
	
	@Override
	public void procesarJson(JSONArray json, Integer index) {
		Object o= json.get(index);
		if(o instanceof String){
			String identificador=RecuperadorPropiedadedConfiguracionEntidad.getInstance().getIdentificadorAtributo();
			String valor=(String)json.get(index);
			if(valor.equalsIgnoreCase(identificador+cabeceraPorReemplazar+identificador)){
				json.remove(index);
				json.add(valorPorReemplazaR);	
			}
		}
	}
}
