package exit.services.excepciones;

public class ExceptionServidorNoAlcanzado extends ExceptionBiactiva {

		public ExceptionServidorNoAlcanzado(String fichero,String mensaje){
			super(fichero, mensaje);
		}
		public ExceptionServidorNoAlcanzado(String fichero){
			super(fichero);
		}

}
