package exit.services.excepciones;

public class ExceptionTipoIncidenteInvalido extends ExceptionBiactiva {

	private static final long serialVersionUID = 1L;
	
	public ExceptionTipoIncidenteInvalido(String fichero,String mensaje){
		super(fichero, mensaje);
	}
	public ExceptionTipoIncidenteInvalido(String fichero){
		super(fichero);
	}
}
