package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import exit.services.fileHandler.CSVHandler;
import exit.services.principal.WSConector;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public abstract class GetAbstractoGenerico {
	public static int x=0;
	public BufferedReader realizarPeticion(){
		return realizarPeticion(null);
	}
	 public BufferedReader realizarPeticion(Integer id){
	        try{
	        	WSConector ws;
	        	if(id!=null)
	        		 ws = new WSConector("GET",RecuperadorPropiedadedConfiguracionEntidad.getInstance().getUrl()+"/"+id,"application/json");
	        	else
	        		 ws = new WSConector("GET",RecuperadorPropiedadedConfiguracionEntidad.getInstance().getUrl(),"application/json");
	        	HttpURLConnection conn=ws.getConexion();
	            int responseCode = conn.getResponseCode();
	            BufferedReader in;
	            if(responseCode == 200){
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getInputStream()));
	            	if(id!=null)
	            		procesarPeticionOK(in, id,responseCode);
	            	else
	            		procesarPeticionOK(in,responseCode);
	            	
	            }
	            else{
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getErrorStream()));
	            	if(id!=null)
	            		procesarPeticionError(in,id,responseCode);
	            	else
	            		procesarPeticionError(in,responseCode);
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
				if(id!=null)
					csv.escribirErrorException("Error al recuperar el registro con id: "+id,e.getStackTrace(),false);
				else
					csv.escribirErrorException("Error al recuperar registros de la entidad "+ApuntadorDeEntidad.getInstance().getEntidadActual(),e.getStackTrace(),false);
				return null;
			}
      }
		abstract void procesarPeticionOK(BufferedReader in, Integer id,int responseCode) throws Exception;
		abstract void procesarPeticionError(BufferedReader in, Integer id, int responseCode) throws Exception;
		abstract void procesarPeticionOK(BufferedReader in, int responseCode) throws Exception;
		abstract void procesarPeticionError(BufferedReader in, int responseCode) throws Exception;

}
