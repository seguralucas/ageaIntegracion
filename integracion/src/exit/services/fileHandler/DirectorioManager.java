package exit.services.fileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import exit.services.principal.peticiones.GetIdsByBpSapIdCampana;
import exit.services.singletons.AlmacenadorFechaYHora;
import exit.services.singletons.ApuntadorDeEntidad;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public class DirectorioManager {
	
	public static final String NOMBRE_TEMP="temp";
	public static final String NOMBRE_BORRARSAPID_TEMP="tempsapid";
	public static Integer contadorEliminadosCampana=0;
	
	public static void SepararFicherosCampana(File archivo) throws Exception{
    				CSVHandler csv= new CSVHandler();
    				HashSet<String> setBpSapId= new HashSet<String>();
		    		String line="";
			    		try(BufferedReader br = new BufferedReader(
			    		         new InputStreamReader(
			    		                 new FileInputStream(archivo), "UTF-8"))){
			    		boolean esPrimeraVez=true;
			    		int i=0;
			    		while ((line = br.readLine()) != null) {
			    			if(esPrimeraVez){
			    				esPrimeraVez=false;
			    				CSVHandler.cabeceraFichero=line;			    									    		
			    			}
			    			else{
				    			csv.escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicioDivision(NOMBRE_TEMP+i+".csv"),line,true);
				    			setBpSapId.add(line.split(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSVREGEX())[0]);
			    				if(i>=RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo()-1)
			    					i=0;
			    				else
			    					i++;
			    			}
			    		}
				        List<String> listaBpSapId = new ArrayList<String>(setBpSapId);
				    	List<List<String>> listaDeLista= new ArrayList<List<String>>();
				    	int delta=listaBpSapId.size()/RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo();
				    	int desde=0;
				    	int hasta=delta;
				    	for(int j=1;j<=RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo();j++){
				    		listaDeLista.add(listaBpSapId.subList(desde, hasta));
				    		desde=hasta;//Ya que el hasta NO esta incluido en la sublist
				    		hasta+=delta;
				    		if(j==RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo()-1)
				    			hasta=listaBpSapId.size();
				    	}
				    	ExecutorService workers = Executors.newFixedThreadPool(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo());      	
					    List<Callable<Void>> tasks = new ArrayList<>();
						for(List<String> listBpSapId: listaDeLista){
							tasks.add(new Callable<Void>() {
						        public Void call() {
						        	int salto=100;
						        	//for()
						        	StringBuilder sb= new StringBuilder();
						        	for(int h=0;h<listBpSapId.size();h++){
							        	if((h!= 0 && h%salto==0) || h==listaBpSapId.size()-1){
								        	sb.append("%20BpSapId=%27"+listBpSapId.get(h)+"%27");
											GetIdsByBpSapIdCampana get= new GetIdsByBpSapIdCampana();
											System.out.println("https://help365.custhelp.com/services/rest/connect/v1.3/queryResults/?query=select%20id%20from%20CO.Facturacion%20where"+sb.toString());
											get.realizarPeticionString("https://help365.custhelp.com/services/rest/connect/v1.3/queryResults/?query=select%20id%20from%20CO.Facturacion%20where"+sb.toString());
											sb=new StringBuilder();
											System.out.println(DirectorioManager.contadorEliminadosCampana);
											DirectorioManager.contadorEliminadosCampana++;
							        	}
							        	else
							        		sb.append("%20BpSapId=%27"+listBpSapId.get(h)+"%27or");
						        	}
									return null;
				    		}
			    		});
						}
					    workers.invokeAll(tasks);
					    workers.shutdown();
					}
			        catch(Exception e){
			        	FileWriter fw = new FileWriter(getDirectorioFechaYHoraInicio("errorLote.txt"));
			        	fw.write(e.getMessage());
			        	fw.close();
			        	throw e;
			        }
	}
	
	public static void SepararFicheros(File archivo) throws IOException{
		CSVHandler csv= new CSVHandler();
		String line="";
    		try(BufferedReader br = new BufferedReader(
    		         new InputStreamReader(
    		                 new FileInputStream(archivo), "UTF-8"))){
    		boolean esPrimeraVez=true;
    		int i=0;
    		while ((line = br.readLine()) != null) {
    			if(esPrimeraVez){
    				esPrimeraVez=false;
    				CSVHandler.cabeceraFichero=line;			    									    		
    			}
    			else{
	    			csv.escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicioDivision(NOMBRE_TEMP+i+".csv"),line,true);
    				if(i>=RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo()-1)
    					i=0;
    				else
    					i++;
    			}
    		}
		}
        catch(Exception e){
        	FileWriter fw = new FileWriter(getDirectorioFechaYHoraInicio("errorLote.txt"));
        	fw.write(e.getMessage());
        	fw.close();
        	throw e;
        }
}
	

	private static String getEntidadFecha(){
		return ConstantesGenerales.PATH_EJECUCION+"/"+ApuntadorDeEntidad.getInstance().getEntidadActual()+"/"+AlmacenadorFechaYHora.getFechaYHoraInicio();
	}
	public static File getDirectorioFechaYHoraInicio(String nombreFichero) throws IOException{
		File file = new File(getEntidadFecha());
		if(!file.exists())
			Files.createDirectories(Paths.get(getEntidadFecha()));
		return new File(getEntidadFecha()+"/"+nombreFichero);
	}
	public static String getPathFechaYHoraInicioDivision() throws IOException{
		File file = new File(getEntidadFecha());
		if(!file.exists())
			Files.createDirectories(Paths.get(getEntidadFecha()));
		file = new File(getEntidadFecha()+"/division");
		if(!file.exists())
			Files.createDirectories(Paths.get(getEntidadFecha()+"/division"));
		return getEntidadFecha()+"/division";
	}
	private static File getDirectorioFechaYHoraInicioDivision(String nombreFichero) throws IOException{
		return new File(getPathFechaYHoraInicioDivision()+"/"+nombreFichero);
	}
	
	/*	public static void SepararFicherosSinSacsRepetidos(File archivo) throws IOException{
	CSVHandler csv= new CSVHandler();
	String line="";
		try(BufferedReader br = new BufferedReader(
		         new InputStreamReader(
		                 new FileInputStream(archivo), "UTF-8"))){
		boolean esPrimeraVez=true;
		int i=0;
		int columnaNroSac=-1;
		ArrayList<String> listaSacs= new ArrayList<String>();
		boolean agregarLinea=false;
		while ((line = br.readLine()) != null) {
			if(esPrimeraVez){
				esPrimeraVez=false;
				CSVHandler.cabeceraFichero=line;
				String[] elementos=line.split(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSVREGEX());
				for(int j=0;j<elementos.length;j++){
					if(elementos[j].equalsIgnoreCase("NRO_SAC"))
						columnaNroSac=j;
				}
			}
			else if(line.trim().length()!=0){
				agregarLinea=false;
				if(columnaNroSac!=-1){
				String nroSacActual=line.split(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSVREGEX())[columnaNroSac];
    				if(listaSacs.contains(nroSacActual)){
    	    			csv.escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(CSVHandler.NRO_SAC_REPETIDO_EN_EL_CSV_EJECUTADO),line,true);    				
    				}
    				else{
    					agregarLinea=true;
    					listaSacs.add(nroSacActual);
    				}
				}
				else
					agregarLinea=true;
				if(agregarLinea){
	    			csv.escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicioDivision(NOMBRE_TEMP+i+".csv"),line);
    				if(i>=RecuperadorPropiedadedConfiguracionEntidad.getInstance().getNivelParalelismo()-1)
    					i=0;
    				else
    					i++;
				}
			}
		}
	}
    catch(Exception e){
    	FileWriter fw = new FileWriter(getDirectorioFechaYHoraInicio("errorLote.txt"));
    	fw.write(e.getMessage());
    	fw.close();
    	throw e;
    }
}*/

}
