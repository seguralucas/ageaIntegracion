package exit.services.json;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.parser.ParseException;

import exit.services.parser.RecuperadorPropierdadesJson;

public class JsonGenerico extends AbstractJsonRestEstructura{
	/*************************************/
	
	/*************************************/

	/***********************************************/
	
	public JsonGenerico() throws IOException {
		super();
	}
	
	@Override
	public String alterarValor(String cabecera, String valor) {
		
		switch(RecuperadorPropierdadesJson.getInstancia().getTipo(cabecera)){
		case RecuperadorPropierdadesJson.TIPO_FECHA: return "\""+insertarFecha(valor)+"\"";
		case RecuperadorPropierdadesJson.TIPO_ENTERO: return valor;
		case RecuperadorPropierdadesJson.TIPO_CADENA: return "\""+valor+"\"";
		default: return valor;
		}
	}
	
	@Override
	public void agregarCampo(String cabecera, String valor) {
		insertarValorJson(cabecera,valor);
		insertarValorMap(cabecera,valor);
	}	
	
	/**
	 * M�todo Exclusivo para debuguear
	 * @param dataJson
	 * @throws ParseException
	 */

	public void mostrar() {
		System.out.println(this.dataJson);
	}


	@Override
	public boolean validarCampos() {
		return true;
	}

	@Override
	public JSONHandler createJson() throws Exception {
		return new JSONHandler(getLine(),getDataJson());
	}

	@Override
	public HashMap<String, String> getMapCabeceraValor() {
		return mapCabeceraValor;
	}



}
