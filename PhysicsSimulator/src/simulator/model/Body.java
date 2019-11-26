package simulator.model;

import simulator.misc.Vector;

public  class Body {
	private String id;
	private Vector velocity;
	private Vector acceleration;
	private Vector position;
	private double mass;
	
	public Body(String id, Vector v, Vector a, Vector p, double m) {
		this.id=id;
		this.velocity=v;
		this.acceleration=a;
		this.position=p;
		this.mass=m;
	}

	public String getId() {
		return id;
	}

	public Vector getVelocity() {
		return new Vector(velocity);
	}

	public Vector getAcceleration() {
		return new Vector(acceleration);
	}

	public Vector getPosition() {
		return new Vector(position);
	}

	public double getMass() {
		return mass;
	}
	
	public void setVelocity(Vector v) {
		this.velocity = new Vector(v);
	}

	public void setAcceleration(Vector a) {
		this.acceleration = new Vector(a);
	}
	
	public void setPosition(Vector p) {
		this.position = new Vector(p);
	}
 	
	
	protected void setMass(double mass) {
		this.mass = mass;
	}
	
	
	
	
	protected void move(double t) {
		//posicion==vectorp + (vectorv *t + (1/2 *vectora * t*t))
		Vector auxP = new Vector(2);
		double tiempo=(t*t)/2; //    1/2 * t*t
		auxP = this.getAcceleration().scale(tiempo); //vector a por lo de arriba
		auxP=auxP.plus(this.getVelocity().scale(t)); //   (vectorv*t) mas lo de arriba
		auxP=auxP.plus(this.getPosition());  //vectorp mas lo de arriba
		this.setPosition(auxP); //setposition
		
		/*-------------------------------------------------------------------------------------------*/
		//velocidad== vectorv +(vectoa*t)
		Vector auxV = new Vector(2);
		auxV=this.getAcceleration().scale(t); //       vectorAceleracion*t
		auxV=auxV.plus(this.getVelocity()); //       vectorV mas lo de arriba
		this.setVelocity(auxV); //setVelocity
		
		
	}
	public String toString() {
		return " {  \"id\": "+ "\""+getId()+"\", " + "\"mass\": " + getMass() + ", \"pos\": " + getPosition()+", \"vel\": "+getVelocity()
				+", \"acc\": "+getAcceleration() +" }";
	}
}
