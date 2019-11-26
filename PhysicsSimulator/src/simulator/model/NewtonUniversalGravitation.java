package simulator.model;
import java.util.List;
import simulator.misc.Vector;

public class NewtonUniversalGravitation implements GravityLaws{

	public static final double  G = 6.67E-11; 
	public NewtonUniversalGravitation() {

	}
	@Override
	public void apply(List<Body> bodies) {
		
		
		
		for(int i=0;i<bodies.size();i++) {
			//ver si el cuerpo si su masa es 0, y cambiar la aceleracion y la velocidad a 0 si lo es
			Body b1= bodies.get(i);
			if(b1.getMass()==0) {
				b1.setAcceleration(new Vector(b1.getVelocity().dim()));
				b1.setVelocity(new Vector(b1.getAcceleration().dim()));
			}
			else {
				Vector fuerzaSumatorio= new Vector(b1.getAcceleration().dim());
				
				for(int j=0;j<bodies.size();j++) {
					Body b2=bodies.get(j);
					if(i!=j) {	
						//g*((m1*m2)/(vectorp2-vectorp1)^2 --->esto nos da fij
						double auxF= (G * b1.getMass() * b2.getMass())/ (Math.pow(b2.getPosition().distanceTo(b1.getPosition()),2));
						
						//creamos la fuerza Fij que es el vector direccion por fij
						fuerzaSumatorio= fuerzaSumatorio.plus((b2.getPosition().minus(b1.getPosition())).direction().scale(auxF));
						//sumamos al sumatorio de las fuerzas que actuan sobre i, la que acabamos de calcular
					}
				}
						
						
						
						
						
			
				
				//hacemos el set de la aceleracion del sumatorio de las fuerzas de i por 1/masa de i
				
				b1.setAcceleration(fuerzaSumatorio.scale(1.0/b1.getMass()));
			}
				


		}
	}

}
