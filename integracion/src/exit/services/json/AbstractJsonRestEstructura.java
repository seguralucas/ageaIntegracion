package exit.services.json;

import java.io.IOException;
import java.util.HashMap;

import exit.services.fileHandler.CSVHandler;
import exit.services.singletons.RecuperadorFormato;
import exit.services.singletons.RecuperadorPropierdadesJson;

public abstract class AbstractJsonRestEstructura {
	protected String line;
	protected String dataJson;
	protected HashMap<String, String> mapCabeceraValor;

	abstract public void agregarCampo(String cabecera, String valor);
	abstract public boolean validarCampos();
	abstract public JSONHandler createJson() throws Exception;
	abstract protected String alterarValor(String cabecera, String valor);
	

	
	public AbstractJsonRestEstructura() throws IOException {
		super();
		this.dataJson=RecuperadorFormato.getInstancia().getFormato();
		mapCabeceraValor= new HashMap<String, String>();
	}
	
	public HashMap<String, String> getMapCabeceraValor(){
		return mapCabeceraValor;
	}
	
	protected void insertarValorMap(String cabecera, String valor){
		getMapCabeceraValor().put(cabecera, alterarValor(cabecera,valor));
	} 
	
	protected void insertarValorJson(String cabecera, String valor){ 
		this.dataJson=this.dataJson.replaceAll("#"+cabecera+"#", alterarValor(cabecera,valor));
	}
	
	
	protected Boolean insertarTrueOFalse(String valor){
		if(valor == null)
			return null;
		if (valor.equalsIgnoreCase("si") || valor.equalsIgnoreCase("true") || valor.equalsIgnoreCase("verdadero"))
			return true;
		else if(valor.equalsIgnoreCase("no") || valor.equalsIgnoreCase("false") || valor.equalsIgnoreCase("false"))
			return false;
		else 
			return null;
	}
	

	protected String insertarFecha(String valor){
		final String PATH_ERROR="error_formato_fecha.csv";
		if(valor==null || valor.length()==0)
			return null;
		CSVHandler csv= new CSVHandler();
		String[] fecha=valor.split("/");
		if(fecha.length==3){
			try{
				return fecha[2]+"-"+fecha[1]+"-"+fecha[0];
			}
			catch(Exception e){
				try {
					csv.escribirCSV(PATH_ERROR, this.getLine());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return null;
			}
		}
		fecha=valor.split("-");
		if(fecha.length!=3)
			return null;
		else
			return valor;

	}
	protected String insertarString(String valor){
		
		if(valor == null || valor.length()==0)
			return null;
		return valor;
	}
	
	protected String procesarFecha(String cabecera, String valor){
		return "\""+insertarFecha(valor)+"\"";
	}
	
	protected String procesarCadena(String cabecera, String valor){
		return "\""+valor+"\"";
	}
	
	protected String procesarEntero(String cabecera, String valor){
		if(RecuperadorPropierdadesJson.getInstancia().isBorrarCarNoNumericos(cabecera))
			return valor.replaceAll("[^0-9]", "");
		return valor;
	}
	
	
	public String getLine(){
		return line;
	}
	public void setLine(String line){
		this.line=line;
	}
	
	public String getDataJson(){
		return dataJson;
	}
	
	
}
