package exit.services.excepciones;

public class ExceptionEstadoInvalido  extends ExceptionBiactiva{

	private static final long serialVersionUID = 1L;
	
	public ExceptionEstadoInvalido(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionEstadoInvalido(String fichero){
		super(fichero);
	}
}
