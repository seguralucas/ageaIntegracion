package exit.services.excepciones;

public class ExceptionIDNullIncidente extends ExceptionBiactiva{

	private static final long serialVersionUID = 1L;
	
	public ExceptionIDNullIncidente(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionIDNullIncidente(String fichero){
		super(fichero);
	}
}
