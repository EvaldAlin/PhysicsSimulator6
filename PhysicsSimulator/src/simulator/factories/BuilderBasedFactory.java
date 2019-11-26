package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;



public class BuilderBasedFactory<T> implements Factory<T> {

	private List<Builder<T>> builders;

	public BuilderBasedFactory(List<Builder<T>> builders) throws IllegalArgumentException {
		this.builders=builders;
	}
	@Override
	public T createInstance(JSONObject info) { 
		T instance =null;


		for(Builder<T> t:this.builders){
			instance=t.createInstance(info);
			if(instance!=null) return instance;

		}
		throw new IllegalArgumentException("No se ha encontrado el constructor invocado"); 	
	}

	@Override
	public List<JSONObject> getInfo() {
		List<JSONObject> listaJSON = new ArrayList<JSONObject>();

		for(Builder<T> t:this.builders){

			listaJSON.add(t.getBuilderInfo());

		}

		return listaJSON;
	}

}

