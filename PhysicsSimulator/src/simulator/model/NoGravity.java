package simulator.model;

import java.util.List;

public class NoGravity implements GravityLaws {
	public NoGravity() {
		
	}
	@Override
	public void apply(List<Body> bodies) {
		//No hace nada este metodo, mantiene la aceleracion de los cuerpos que tengan esta ley
		for(Body b: bodies) {
			b.setAcceleration(b.getAcceleration());
		}
		// TODO Auto-generated method stub
		
	}
	public String toString() {
		return "Esta ley de la gravedad deja la aceleracion constante";
	}
}
