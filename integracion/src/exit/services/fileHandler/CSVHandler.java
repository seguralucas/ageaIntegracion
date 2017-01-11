package exit.services.fileHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.csvreader.CsvWriter;

import exit.services.json.JSONHandler;
import exit.services.principal.Separadores;
import exit.services.singletons.RecuperadorPropiedadedConfiguracionEntidad;

public class CSVHandler {
	
	public static String cabeceraFichero;
	public static final String PATH_ERROR_SERVER_NO_ALCANZADO="servidor_no_alcanzado.csv";
	public static final String PATH_ERROR_SERVER_NO_ALCANZADO_BORRADO="servidor_no_alcanzado_borrado.csv";
	public static final String PATH_SAC_EXISTENTE="sac_existente_services.csv";
	public static final String LOG_ERROR_FETCH_TIPO_INCIDENTE="error_fetch_tipo_incidente.txt";
	public static final String PATH_ERROR_EXCEPTION="exception_ejecucion.csv";
	public static final String PATH_ID_NO_ENCONTRADO="id_no_encontrado.csv";
	public static final String PATH_ERROR_EXCEPTION_LOG="exception_ejecucion_log.txt";
	public static final String NRO_SAC_REPETIDO_EN_EL_CSV_EJECUTADO="nro_sac_repetido_en_el_csv_ejecutado.csv";
	public static final String PATH_INSERTADOS_OK="insertadosOK.csv";
	public static final String PATH_BORRADOS_OK="borradosOK.csv";
	

		private static synchronized void crearCabecer(File file,String cabecera)  throws IOException{
            if(!file.exists() || file.length() == 0){
            		CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSVREGEX().charAt(0));
    				csvOutput.write(cabecera);
    	        	csvOutput.endRecord();
    	            csvOutput.close();
    		}
		}
		
		private void escribirCampos(File file, String line) throws IOException{
		 	CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSV().charAt(0));
            String[] campos= line.split(RecuperadorPropiedadedConfiguracionEntidad.getInstance().getSeparadorCSVREGEX());
            for(String c:campos){
                csvOutput.write(insertarNoNull(c));        
            }
            csvOutput.endRecord();		 
            csvOutput.close();
		}
		
/*		private synchronized void crearCabecer(File file)  throws IOException{
			crearCabecer(file,cabeceraFichero);
		}	*/
		
		 public void escribirCSV(File file,String line, String cabecera) throws IOException{
	            crearCabecer(file,cabecera);
	            escribirCampos(file,line);
		 }
		 
		 public void escribirCSV(File file,String line, boolean tieneCabeceraDefault) throws IOException{
			 if(tieneCabeceraDefault)
				 escribirCSV(file,line,cabeceraFichero);
			 else
				 escribirCampos(file,line);
		 }
		 
		 public void escribirCSV(File file,String line) throws IOException{
			 escribirCSV(file,line,true);
		 }
		 
		 public void escribirCSV(String path,String line) throws IOException{
			 	escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(path),line);
		 }
		 public void escribirCSV(String path,String line,boolean cabeceraDefecto) throws IOException{
			 	escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(path),line,cabeceraDefecto);
		 }
		 public void escribirCSV(String path,JSONHandler json) throws IOException{
			 escribirCSV(DirectorioManager.getDirectorioFechaYHoraInicio(path),json.getLine(),true);
		 }
		 
/*		 private void insertarCampoVacio(CsvWriter csvOutput) throws IOException{
        	 csvOutput.write(insertarNoNull(""));
		 }*/
		 
		 private String insertarNoNull(String cadena){
			 if(cadena!=null)
				 return cadena;
			 return "";
		 }
		 
		 public synchronized void escribirErrorException(StackTraceElement[] stackArray) {
			 escribirErrorException((String)null,stackArray,false);
		 }
		 
		 public synchronized void escribirErrorException(JSONHandler json,StackTraceElement[] stackArray) {
			 escribirErrorException(json.getLine(),stackArray,true);
		 }
		 public synchronized void escribirErrorException(String line,StackTraceElement[] stackArray,boolean logueaEnCsv) {
				try {
					if(line!=null){
						if(logueaEnCsv)
							this.escribirCSV(PATH_ERROR_EXCEPTION,line);
						this.escribirCSV(PATH_ERROR_EXCEPTION_LOG,line);
					}
					for(StackTraceElement ste: stackArray){
					     this.escribirCSV(PATH_ERROR_EXCEPTION_LOG,"FileName: "+ste.getFileName()+" Metodo: "+ste.getMethodName()+"Clase "+ste.getClassName()+" Linea "+ste.getLineNumber(),false);
					}		
					this.escribirCSV(PATH_ERROR_EXCEPTION_LOG,Separadores.SEPARADOR_ERROR_TRYCATCH);
					} catch (IOException e) {
					e.printStackTrace();
				}
	 }
}
