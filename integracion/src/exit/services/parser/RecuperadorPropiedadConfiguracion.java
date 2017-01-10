package exit.services.parser;

import java.io.FileReader;
import java.util.Properties;

import exit.services.fileHandler.CSVHandler;

public class RecuperadorPropiedadConfiguracion {
	private String pathCSVRegistros;
	private String url;
	private String user;
	private String password;
	private String separadorCSV;
	private int nivelParalelismo;
	private String usaProxy;
	private String ipProxy;
	private String puertoProxy;
	private String action;
	
	private static RecuperadorPropiedadConfiguracion instance;
    private RecuperadorPropiedadConfiguracion(){
        Properties props = new Properties();
        try{
		props.load(new FileReader("WebContent/ConfiguracionConexion.properties"));
		pathCSVRegistros=props.getProperty("pathCSVRegistros");
		url=props.getProperty("url");
		user=props.getProperty("user");
		password=props.getProperty("password");
		separadorCSV=props.getProperty("separadorCSV");
		nivelParalelismo=Integer.parseInt(props.getProperty("nivelParalelismo"));
		usaProxy=props.getProperty("usaProxy");
		ipProxy=props.getProperty("ipProxy");
		puertoProxy=props.getProperty("puertoProxy");
		action=props.getProperty("action");
        }
        catch(Exception e){
        	e.printStackTrace();
        	CSVHandler csv= new CSVHandler();
        	csv.escribirErrorException(e.getStackTrace());
        }

    }
    
	public void mostrarConfiguracion(){
    	System.out.println("Directorio de busqueda: "+RecuperadorPropiedadConfiguracion.getInstance().getPathCSVRegistros());
    	System.out.println("URL: "+RecuperadorPropiedadConfiguracion.getInstance().getUrl());
    	System.out.println("Usuario: "+RecuperadorPropiedadConfiguracion.getInstance().getUser());
    	System.out.println("Password: "+RecuperadorPropiedadConfiguracion.getInstance().getPassword());
    	System.out.println("Acci�n: "+RecuperadorPropiedadConfiguracion.getInstance().getAction());
    	System.out.println("Nivel de paralelismo: "+RecuperadorPropiedadConfiguracion.getInstance().getNivelParalelismo());
    	System.out.println("Usa Proxy: "+RecuperadorPropiedadConfiguracion.getInstance().getUsaProxy());
    	if(RecuperadorPropiedadConfiguracion.getInstance().getUsaProxy().equalsIgnoreCase("si")){
        	System.out.println("IP Proxy: "+RecuperadorPropiedadConfiguracion.getInstance().getIpProxy());
    		System.out.println("Puerto Proxy: "+RecuperadorPropiedadConfiguracion.getInstance().getPuertoProxy());
    	}  	
	}
    
    public static synchronized RecuperadorPropiedadConfiguracion getInstance() {
    	if(instance==null)
    		instance=new RecuperadorPropiedadConfiguracion();
    	return instance;
    }

	public String getPathCSVRegistros() {
		return pathCSVRegistros;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getSeparadorCSV() {
		return separadorCSV;
	}
	
	public String getSeparadorCSVREGEX() {
		return "\\"+separadorCSV;
	}

	public int getNivelParalelismo() {
		return nivelParalelismo;
	}

	public String getUsaProxy() {
		return usaProxy;
	}

	public String getIpProxy() {
		return ipProxy;
	}

	public String getPuertoProxy() {
		return puertoProxy;
	}
	public String getAction() {
		return action;
	}
	

	



}
