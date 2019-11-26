package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws> {
	public FallingToCenterGravityBuilder() {
		super("ftcg","Falling to Center Gravity Law");
	}
	@Override
	public GravityLaws createTheInstance(JSONObject jsonObject) {
		return new FallingToCenterGravity();

	}

}
