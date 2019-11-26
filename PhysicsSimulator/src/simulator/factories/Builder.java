package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {
	
	private String typeTag;
	private String desc;
	
	public Builder(String typeTag, String desc){
		this.typeTag=typeTag;
		this.desc=desc;
	}

	protected double[] jsonArrayTodoubleArray(JSONArray array) {
		int tam = array.length();
		double[] arrayDouble = new double[tam];
		for(int i=0;i<tam;i++) {
			arrayDouble[i] = array.getDouble(i);
		}
		return arrayDouble;
		
	}
	public T createInstance(JSONObject info) throws IllegalArgumentException{ 
		T t;
		String tipo =info.getString("type");
		
		if (tipo != null && typeTag.equals(tipo))  t = createTheInstance(info.getJSONObject("data"));
		else {
			if(info.getJSONObject("data")!=null) {
				t=null;
			}
			else throw new IllegalArgumentException("Data no es reconocido");
		}
	return t;
		
	}
	public JSONObject getBuilderInfo() { 
		JSONObject info = new JSONObject(); 
		info.put("type", typeTag); 
		info.put("data", createData()); 
		info.put("desc", desc);
		
		return info;
	}
	protected JSONObject createData() {
	return new JSONObject(); }
	
	
	protected abstract T createTheInstance(JSONObject jsonObject);

	}

