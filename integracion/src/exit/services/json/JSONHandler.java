package exit.services.json;

import org.json.simple.JSONObject;

public class JSONHandler extends JSONObject{
	
	private String line;
	

	
	
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString().replace("\\", "").replace(",", ",\n");
	}
	
	public String toStringSinEnter(){
		return super.toString().replace("\\", "");
	}
	
	public String toStringNormal(){
		return super.toString();
	}
}
