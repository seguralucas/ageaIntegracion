package exit.services.excepciones;

public class ExceptionAnioInvalido extends ExceptionBiactiva{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1155311689012858016L;

	public ExceptionAnioInvalido(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionAnioInvalido(String fichero){
		super(fichero);
	}
	
}
