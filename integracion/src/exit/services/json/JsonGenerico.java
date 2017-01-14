package exit.services.json;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.parser.ParseException;

import exit.services.singletons.RecuperadorPropierdadesJson;

public class JsonGenerico extends AbstractJsonRestEstructura{
	/*************************************/
	
	/*************************************/

	/**
	 * @throws Exception *********************************************/
	
	public JsonGenerico() throws Exception {
		super();
	}
	
	@Override
	public Object alterarValor(String cabecera, String valor) {
		switch(RecuperadorPropierdadesJson.getInstancia().getTipo(cabecera)){
			case RecuperadorPropierdadesJson.TIPO_FECHA: return procesarFecha(cabecera,valor);
			case RecuperadorPropierdadesJson.TIPO_ENTERO: return procesarEntero(cabecera,valor);
			case RecuperadorPropierdadesJson.TIPO_CADENA: return  procesarCadena(cabecera,valor);
			default: return valor;
		}
	}
	
	@Override
	public void agregarCampo(String cabecera, String valor) {
		insertarValorJson(cabecera,valor);
		insertarValorMap(cabecera,valor);
	}	
	
	/**
	 * Método Exclusivo para debuguear
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
		return new JSONHandler(getLine(),getJsonFormato());
	}

	@Override
	public HashMap<String, Object> getMapCabeceraValor() {
		return mapCabeceraValor;
	}



}
