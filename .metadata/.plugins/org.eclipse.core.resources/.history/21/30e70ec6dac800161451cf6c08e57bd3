package exit.services.fileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import exit.services.json.IJsonRestEstructura;
import exit.services.parser.ParserXMLWSConnector;


public class ConvertidosJSONCSV{
    	private String line = "";
    	private CSVHandler csv;
		private BufferedReader br=null;
    	private boolean esPrimeraVez=true;
    	private boolean fin=false;
    	private String jsonPaquete;
    	
    	
		public ConvertidosJSONCSV(String jsonPaquete){
            csv = new CSVHandler();
            this.jsonPaquete=jsonPaquete;
		}
		
	   
	   public IJsonRestEstructura convertirCSVaJSONLineaALineaIncidentes(File fileCSV) {
		   try{
			   if(br==null)
			   br = new BufferedReader(
		  		         new InputStreamReader(
		  		                 new FileInputStream(fileCSV)));
  		String[] cabeceras=null;
  		while ((line = br.readLine()) != null) {
  			if(this.esPrimeraVez){
  				String firstChar=String.valueOf(line.charAt(0));
  				if(!firstChar.matches("[a-zA-Z]"))
  					line=line.substring(1);//Ocasionalmente el primer caracter erra un signo raro y hay que eliminarlo.
  				cabeceras = line.split(ParserXMLWSConnector.getInstance().getSeparadorCSVREGEX());
  				this.esPrimeraVez=false;
  				CSVHandler.cabecera=line;//Esto es sólo en caso de que estemos haciendo update
  			}
  			else{
  	    		String[] valoresCsv= line.replace("\"", "'").split(ParserXMLWSConnector.getInstance().getSeparadorCSVREGEX());
				try{
  					if(ColumnasMayorCabecera(valoresCsv))
  						throw new Exception();
  					IJsonRestEstructura jsonEstructura=crearJson(valoresCsv,CSVHandler.cabecera.split(ParserXMLWSConnector.getInstance().getSeparadorCSVREGEX()));  	
  					jsonEstructura.setLine(line);
    			return jsonEstructura;
  				}
  				catch(Exception e){
  					csv.escribirCSV("error_parser.csv", line);
  					return null;
  				}
  			}
  		}
  		br.close();
  		this.fin=true;
      }
      catch(IOException e){
      	e.printStackTrace();
  		this.fin=true;
			return null;
      }	   
 			return null;
	   }
			   
	   public IJsonRestEstructura crearJson(String[] valoresCsv, String[] cabeceras) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		   IJsonRestEstructura restEstructura= (IJsonRestEstructura)Class.forName(this.jsonPaquete).getConstructor().newInstance();
		   for(int i=0;i<valoresCsv.length;i++){
			   restEstructura.agregarCampo(cabeceras[i], valoresCsv[i]);
		   }
		   return restEstructura;
	   }

	   
	   private boolean ColumnasMayorCabecera(String[] valoresCsv){
		   return CSVHandler.cabecera.split(ParserXMLWSConnector.getInstance().getSeparadorCSVREGEX()).length<valoresCsv.length;
	   }
	   
	   	   
	   public boolean isFin() {
		return fin;
	}


}
