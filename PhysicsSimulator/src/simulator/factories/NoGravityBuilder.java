package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws>{
	public NoGravityBuilder() {
		super("ng","Sin gravedad");

	}
	@Override
	public GravityLaws createTheInstance(JSONObject jsonObject) {
	return new NoGravity();
		
	}

}
