package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {

	private double lossFactor;
	private double lossFrequency;
	private double contador;
	public MassLossingBody(String id, Vector v, Vector a, Vector p, double m, double fe,double fa) {
		super(id, v, a, p, m);

		this.lossFrequency=fe;
		this.lossFactor=fa;
		this.contador=0.0;
		// TODO Auto-generated constructor stub
	}

	public double getLossFactor() {
		return lossFactor;
	}
	public double getLossFrequency() {
		return lossFrequency;
	}
	public void setContador() {
		this.contador=0.0;
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

		double masa = this.getMass();

		if(contador>=this.getLossFrequency()) {
			this.setMass(masa*(1-this.getLossFactor()));
			this.setContador();
		}
		else contador++;



	}

}
