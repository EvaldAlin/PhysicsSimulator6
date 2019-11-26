package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	public BasicBodyBuilder() {
		super("basic","Cuerpo basico");
	}
	@Override
	public Body createTheInstance(JSONObject jsonObject) {


		String id = jsonObject.getString("id");
		double[] v = jsonArrayTodoubleArray(jsonObject.getJSONArray("vel"));
		double[] p = jsonArrayTodoubleArray(jsonObject.getJSONArray("pos"));
		double m = jsonObject.getDouble("mass");
		Vector vel= new Vector(v);
		Vector pos= new Vector(p);
		Vector a= new Vector(2);
		return new Body(id,vel,a,pos,m);


		
	}

	protected JSONObject createData() {
		JSONObject nuevo = new JSONObject();
		nuevo.put("id","the identifier " );
		nuevo.put("pos","the position" );
		nuevo.put("vel","the velocity " );
		nuevo.put("mass","the mass " );
		return nuevo;

	}

}
