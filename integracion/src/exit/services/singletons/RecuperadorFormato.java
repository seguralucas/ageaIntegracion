package exit.services.singletons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import exit.services.fileHandler.CSVHandler;

public class RecuperadorFormato {

	private static RecuperadorFormato instancia=null;
	private String formato;
	
	public static RecuperadorFormato getInstancia(){
		if(instancia==null)
			instancia= new RecuperadorFormato();
		return instancia;
	}
	private RecuperadorFormato(){
		File f= new File("WebContent/"+ApuntadorDeEntidad.getInstance().getEntidadActual()+"/formatoJson.json");
		String line;
		StringBuilder sb= new StringBuilder();
		try(BufferedReader br= new BufferedReader(new FileReader(f))){
		while((line=br.readLine()) != null){
			sb.append(line);
		}
		this.formato=sb.toString();		
		}
		catch(Exception e){
			CSVHandler csv= new CSVHandler();
			csv.escribirErrorException(e.getStackTrace());
		}
	}
	public String getFormato() {
		return formato;
	}
	
	void reiniciar(){
		instancia=null;
	}
	
	
}