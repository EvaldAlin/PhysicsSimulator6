package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{
	public MassLosingBodyBuilder() {
		super("mlb","Cuerpo que pierde masa");

	}
	@Override
	public MassLossingBody createTheInstance(JSONObject jsonObject) {
		String id = jsonObject.getString("id");
		double[] v = jsonArrayTodoubleArray(jsonObject.getJSONArray("vel"));
		double[] p = jsonArrayTodoubleArray(jsonObject.getJSONArray("pos"));
		//double[] a = jsonArrayTodoubleArray(jsonObject.getJSONArray("acc"));
		double m = jsonObject.getDouble("mass");
		double fe = jsonObject.getDouble("freq");
		double fa = jsonObject.getDouble("factor");
		Vector vel= new Vector(v);
		Vector pos= new Vector(p);
		Vector acc= new Vector(2);

		return new MassLossingBody(id,vel,acc,pos,m,fe,fa);

	}

	protected JSONObject createData() {
		JSONObject nuevo = new JSONObject();
		nuevo.put("id","the id " );
		nuevo.put("pos","the position " );
		nuevo.put("vel","the velocity " );
		nuevo.put("mass","the mass" );
		nuevo.put("freq","the loss Factor(factor de perdida de masa " );
		nuevo.put("factor","the loss Frequency(intervalo de tiempo despues del cual el objeto pierde masa " );

		return nuevo;	

	}

}
