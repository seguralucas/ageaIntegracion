package exit.services.principal.ejecutores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.jersey.spi.StringReader.ValidateDefaultValue;

import exit.services.excepciones.ExceptionAnioInvalido;
import exit.services.excepciones.ExceptionEstadoInvalido;
import exit.services.excepciones.ExceptionIDNoNumerico;
import exit.services.excepciones.ExceptionIDNullIncidente;
import exit.services.excepciones.ExceptionLongitud;
import exit.services.excepciones.ExceptionModoContactoInvalido;
import exit.services.excepciones.ExceptionTipoIncidenteInvalido;
import exit.services.fileHandler.CSVHandler;
import exit.services.fileHandler.ConvertidosJSONCSV;
import exit.services.fileHandler.FilesAProcesarManager;
import exit.services.fileHandler.Tipo_Json;
import exit.services.json.IJsonRestEstructura;
import exit.services.json.JSONHandler;
import exit.services.json.JsonRestClienteEstructura;
import exit.services.json.JsonRestIncidentes;
import exit.services.json.TipoTarea;
import exit.services.parser.ParserXMLWSConnector;
import exit.services.principal.DirectorioManager;
import exit.services.principal.Principal;
import exit.services.principal.peticiones.InsertarIncidente;
import exit.services.util.Contador;




public class EjecutorInsercionIncidentesDistintosFicheros {
	public static int y=0;
	public static int z=0;
	public void insertar(String claseJson) throws InterruptedException, IOException{
		CSVHandler csv = new CSVHandler();
	 	ArrayList<File> pathsCSVEjecutar= FilesAProcesarManager.getInstance().getCSVAProcesar(ParserXMLWSConnector.getInstance().getPathCSVRegistros());
	 	for(File path:pathsCSVEjecutar){
		 	try {
				DirectorioManager.SepararFicherosSinSacsRepetidos(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ArrayList<File> filesCSVDivididos=FilesAProcesarManager.getInstance().getAllCSV(DirectorioManager.getPathFechaYHoraInicioDivision());
	    	ExecutorService workers = Executors.newFixedThreadPool(ParserXMLWSConnector.getInstance().getNivelParalelismo());      	

		    List<Callable<Void>> tasks = new ArrayList<>();
			for(File file: filesCSVDivididos){
				tasks.add(new Callable<Void>() {
			        public Void call() {
			        	try {
				        	ConvertidosJSONCSV csvThread= new ConvertidosJSONCSV(claseJson);
			        		JSONHandler jsonH=null;
			        		IJsonRestEstructura jsonEst=null;
			        		while(!csvThread.isFin()){
				        		boolean excepcion=false;
				        		boolean excepcionGenerica=false;
			        			jsonEst = csvThread.convertirCSVaJSONLineaALineaIncidentes(file);
			        			if(jsonEst!=null){
								try{
									
										jsonH=jsonEst.createJson(TipoTarea.INSERTAR);
										System.out.println(jsonH);
								}
								catch(ExceptionEstadoInvalido e){
									excepcion=true;
									CSVHandler manejadorCSV = new CSVHandler();
									manejadorCSV.escribirCSVERRORLongitud("error_estado_invalido.csv", jsonEst.getLine());
								}
								catch(ExceptionTipoIncidenteInvalido e){
									excepcion=true;
									CSVHandler manejadorCSV = new CSVHandler();
									manejadorCSV.escribirCSVERRORLongitud("error_tipo_incidente_invalido.csv", jsonEst.getLine());
								}
								catch(ExceptionIDNullIncidente e){
									excepcion=true;
									CSVHandler manejadorCSV = new CSVHandler();
									manejadorCSV.escribirCSVERRORLongitud("error_id_incidente_null.csv", jsonEst.getLine());
								}
								catch(ExceptionModoContactoInvalido e){
									excepcion=true;
									CSVHandler manejadorCSV = new CSVHandler();
									manejadorCSV.escribirCSVERRORLongitud("error_modo_contacto_invalido.csv", jsonEst.getLine());
								}
								catch(ExceptionIDNoNumerico e){
									excepcion=true;
									CSVHandler manejadorCSV = new CSVHandler();
									manejadorCSV.escribirCSVERRORLongitud("error_id_no_numerico.csv", jsonEst.getLine());
								}				
								catch(ExceptionAnioInvalido e){
									excepcion=true;
									CSVHandler manejadorCSV = new CSVHandler();
									manejadorCSV.escribirCSVERRORLongitud("error_anio_invalido.csv", jsonEst.getLine());									
								}
								catch(Exception e){
									excepcion=true;
									excepcionGenerica=true;
									e.printStackTrace();
								}
								
								
								if(!excepcion){
									try{
										/*INSERTAR*/
									}
									catch(Exception e){
										CSVHandler csv= new CSVHandler();
										try {
											csv.escribirCSV(ParserXMLWSConnector.getInstance().getFicheroCSVERROREJECUCION().replace(".csv", "_error_no_espeficado.csv"), jsonH.getLine());
										} catch (IOException e1) {
											e1.printStackTrace();
										}

									}
								}
								else if(excepcionGenerica){
									CSVHandler manejadorCSV = new CSVHandler();
									manejadorCSV.escribirCSVERRORLongitud("error_generico.csv", jsonEst.getLine());
								}								
			    				Contador.x++;
			    				System.out.println(Contador.x);
			    				if(Contador.x%1000==0){
			    			  		FileWriter fw = new FileWriter(DirectorioManager.getDirectorioFechaYHoraInicio("cantidadProcesada.txt"));
			    		    		fw.write("el proceso lleva procesado un total de: "+Contador.x+" Registros");
			    		    		fw.close();
			    		    		System.out.println("el proceso lleva procesado un total de: "+Contador.x+" Registros");
			    				}
			        			}
			        		}
			        		}
			        	catch (Exception e) {
							e.printStackTrace();
						}
						return null;
			        }
			});
		}
		    workers.invokeAll(tasks);
		    workers.shutdown();
	}
	}
}
