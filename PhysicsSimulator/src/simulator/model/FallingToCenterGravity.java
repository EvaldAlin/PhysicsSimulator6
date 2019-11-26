package simulator.model;

import java.util.List;


public class FallingToCenterGravity implements GravityLaws {
	
	private final double g =-9.81;
	
	public FallingToCenterGravity() {
		
	}
	@Override
	public void apply(List<Body> bodies) {
		
		for(Body b:bodies) {
			//Vector d = new Vector(b.getPosition()); 
			
			b.setAcceleration(b.getPosition().direction().scale(g)); 
			
		}
		
	}

}
