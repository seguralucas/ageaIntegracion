package exit.services.principal.peticiones;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import exit.services.fileHandler.CSVHandler;
import exit.services.json.JSONHandler;
import exit.services.principal.WSConector;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public abstract class InsertarAbstractoEntidades {
	public static int x=0;
	 public BufferedReader realizarPeticion(JSONHandler json){
	        try{
	        	WSConector ws = new WSConector("POST",RecuperadorPropiedadedConfiguracionEntidad.getInstance().getUrl(),"application/json");
	        	HttpURLConnection conn=ws.getConexion();
	        	DataOutputStream wr = new DataOutputStream(
	        			conn.getOutputStream());
	        	wr.write(json.toStringNormal().getBytes("UTF-8"));
	        	wr.flush();
	        	wr.close();
	            int responseCode = conn.getResponseCode();
	            BufferedReader in;
	            if(responseCode == 201){
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getInputStream()));
	            	procesarPeticionOK(in, json,responseCode);
	            	
	            }
	            else{
	            	in = new BufferedReader(
		                    new InputStreamReader(conn.getErrorStream()));
	            	procesarPeticionError(in,json,responseCode);
	            }
	            return in;	 
	            }	                
            catch (ConnectException e) {
				CSVHandler csv= new CSVHandler();
				try {
					csv.escribirCSV(CSVHandler.PATH_ERROR_SERVER_NO_ALCANZADO, json.getLine());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return null;
			}
            catch (Exception e) {
				CSVHandler csv= new CSVHandler();
				csv.escribirErrorException(json,e.getStackTrace());
				return null;
			}
        }
		abstract void procesarPeticionOK(BufferedReader in, JSONHandler json,int responseCode) throws Exception;
		abstract void procesarPeticionError(BufferedReader in, JSONHandler json, int responseCode) throws Exception;	 
}
