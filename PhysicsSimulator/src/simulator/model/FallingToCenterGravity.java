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
			//vector aceleracion = -g * vector direccion
			b.setAcceleration(b.getPosition().direction().scale(g)); 
			//no se si ese metodo direction del vector posicion de b devuelve la direccion o no
		}
		
	}
	public String toString() {
		return "Esta es la ley de falling to center gravity(FTCG)";
	}

}
