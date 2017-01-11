package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import exit.services.fileHandler.CSVHandler;
import exit.services.principal.WSConector;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public abstract class EliminarAbstractoEntidad {
	public static int x=0;
	 public BufferedReader realizarPeticion(Integer id){
	        try{
	        	WSConector ws = new WSConector("DELETE",RecuperadorPropiedadedConfiguracionEntidad.getInstance().getUrl()+"/"+id,"application/json");
	        	HttpURLConnection conn=ws.getConexion();
	            int responseCode = conn.getResponseCode();
	            BufferedReader in;
	            if(responseCode == 200){
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getInputStream()));
	            	procesarPeticionOK(in, id,responseCode);
	            	
	            }
	            else{
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getErrorStream()));
	            	procesarPeticionError(in,id,responseCode);
	            }
	            return in;	 
	            }	                
           catch (ConnectException e) {
				CSVHandler csv= new CSVHandler();
				try {
					csv.escribirCSV(CSVHandler.PATH_ERROR_SERVER_NO_ALCANZADO_BORRADO, "ID no eliminado: "+id);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return null;
			}
           catch (Exception e) {
				CSVHandler csv= new CSVHandler();
				csv.escribirErrorException("Error al eliminar id: "+id,e.getStackTrace(),false);
				return null;
			}
       }
		abstract void procesarPeticionOK(BufferedReader in, Integer id,int responseCode) throws Exception;
		abstract void procesarPeticionError(BufferedReader in, Integer id, int responseCode) throws Exception;
}
