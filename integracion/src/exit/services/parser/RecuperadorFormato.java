package exit.services.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RecuperadorFormato {

	private static RecuperadorFormato instancia=null;
	private String formato;
	
	public static RecuperadorFormato getInstancia() throws IOException{
		if(instancia==null)
			instancia= new RecuperadorFormato();
		return instancia;
	}
	private RecuperadorFormato() throws IOException{
		File f= new File("WebContent/formatoJson.json");
		BufferedReader br= new BufferedReader(new FileReader(f));
		String line;
		StringBuilder sb= new StringBuilder();
		while((line=br.readLine()) != null){
			sb.append(line);
		}
		this.formato=sb.toString();		
		br.close();
	}
	public String getFormato() {
		return formato;
	}

	
	
}
