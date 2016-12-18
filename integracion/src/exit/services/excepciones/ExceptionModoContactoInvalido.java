package exit.services.excepciones;

public class ExceptionModoContactoInvalido extends ExceptionBiactiva{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4159567343930123389L;

	public ExceptionModoContactoInvalido(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionModoContactoInvalido(String fichero){
		super(fichero);
	}

	
}
