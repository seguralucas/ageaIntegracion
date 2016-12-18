package exit.services.excepciones;

public class ExceptionIDNoNumerico extends ExceptionBiactiva {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionIDNoNumerico(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionIDNoNumerico(String fichero){
		super(fichero);
	}

	
}
