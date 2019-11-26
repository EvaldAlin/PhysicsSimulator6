package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws>{
	public NewtonUniversalGravitationBuilder() {
		super("nlug","Ley de Newton de la gravitacion universal");

	}
	@Override
	public GravityLaws createTheInstance(JSONObject jsonObject) {

		return new NewtonUniversalGravitation();

	}

}
