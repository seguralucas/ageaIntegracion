package aa.bb;

import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) {
		try{
		Class<?> clase=Class.forName("aa.bb.ClaseB");
		ClaseB b= (ClaseB)Class.forName("aa.bb.ClaseB").getConstructor().newInstance();
		ClaseA a=(ClaseA) b;
		Method method = clase.getDeclaredMethod("s");
		method.invoke (a);
		}
		catch(Exception e){
			System.out.println("dd");
			e.printStackTrace();
		}
	}

}
