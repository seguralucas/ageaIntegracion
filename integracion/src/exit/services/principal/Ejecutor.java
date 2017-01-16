package exit.services.principal;

import java.io.IOException;
import java.lang.reflect.Method;

import exit.services.excepciones.ExceptionBiactiva;
import exit.services.fileHandler.CSVHandler;
import exit.services.json.AbstractJsonRestEstructura;
import exit.services.json.JSONHandler;
import exit.services.json.JsonGenerico;
import exit.services.principal.peticiones.InsertarAbstractoEntidades;
import exit.services.principal.peticiones.InsertarGenerico;
import exit.services.singletons.ApuntadorDeEntidad;

public class Ejecutor {
	
	public void ejecutorGenerico(AbstractJsonRestEstructura jsonEst){
		JSONHandler jsonH;
			try{
				jsonH=jsonEst.createJson();
				System.out.println(jsonH.toStringNormal());
				InsertarAbstractoEntidades insertar= new InsertarGenerico();
				insertar.realizarPeticion(jsonH);
			}
			catch(Exception e){
				e.printStackTrace();
				CSVHandler csv= new CSVHandler();
				try {
					csv.escribirErrorException(e.getStackTrace());
					csv.escribirCSV("error_no_espeficado.csv", jsonEst.getLine());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
	}
	

	public Object ejecutar(String nombreMetodo,AbstractJsonRestEstructura jsonEst) throws ExceptionBiactiva{
		ApuntadorDeEntidad.getInstance().siguienteEntidad();
		return ejecutar(nombreMetodo,null,jsonEst);
	}
		
	public Object ejecutar(String nombreMetodo, String parametros, AbstractJsonRestEstructura jsonEst) throws ExceptionBiactiva{
		Class<Ejecutor> a= Ejecutor.class;
		try {
			Method m;
			Object o;
			if(parametros!=null){
				m= a.getMethod(nombreMetodo, parametros.getClass(),jsonEst.getClass().getSuperclass());
				o=m.invoke(this,parametros,jsonEst);
			}
			else{
				m=a.getMethod(nombreMetodo,jsonEst.getClass().getSuperclass());
				o=m.invoke(this,jsonEst);
			}
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			CSVHandler csv= new CSVHandler();
			csv.escribirErrorException(e.getStackTrace());
			throw new ExceptionBiactiva("Error al ejecutar ejecutor");
		} 
	}
	
/*	public static void ejecutorGenerico2(AbstractJsonRestEstructura jsonEst){
		System.out.println("ttt");		
	}
	
	
	public static void main(String[] args) throws Exception {
		Method m;
		Object o;
		Class<Ejecutor> a= Ejecutor.class;
		ApuntadorDeEntidad.getInstance().siguienteEntidad();
		AbstractJsonRestEstructura json= new JsonGenerico();
		m=a.getMethod("ejecutorGenerico2",json.getClass().getSuperclass());
		o=m.invoke(null,json);
	}*/
}