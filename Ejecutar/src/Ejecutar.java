import java.io.IOException;


public class Ejecutar {

	public static void main(String[] args) throws InterruptedException, IOException{
		while(true){
			Integer time=RecuperadorPropiedadesConfiguracionGenerales.getInstance().getIntervaloDeEjecucion();
			System.out.println("El intervalo es de :"+time);
			Process p = Runtime.getRuntime().exec("java -jar integracion.jar");
			if(time==null)
				return;
			Thread.sleep(time *   // minutes to sleep
		             60 *   // seconds to a minute
		             1000); // milliseconds to a second
		}
	}
	
}
