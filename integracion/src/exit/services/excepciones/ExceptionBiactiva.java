package exit.services.excepciones;

public abstract class ExceptionBiactiva extends Exception {
	public String ficheroErrorExcepcion;
	
	public ExceptionBiactiva(String fichero) {
		setFicheroErrorExcepcion(fichero);
	}
	public ExceptionBiactiva(String fichero, String mensaje) {
		super(mensaje);
		setFicheroErrorExcepcion(fichero);
	}
	
	public String getFicheroErrorExcepcion() {
		return ficheroErrorExcepcion;
	}

	public void setFicheroErrorExcepcion(String ficheroErrorExcepcion) {
		this.ficheroErrorExcepcion = ficheroErrorExcepcion;
	}

	
}
