package exit.services.excepciones;

public class ExceptionFormatoFecha extends ExceptionBiactiva{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6965530480493706004L;

	public ExceptionFormatoFecha(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionFormatoFecha(String fichero){
		super(fichero);
	}
	
	
}
