package simulator.model;


import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	private double tiempoPaso;
	private GravityLaws ley;
	private List<Body> list;
	private double tiempoActual;
	private List<SimulatorObserver> listaO;

	public PhysicsSimulator(GravityLaws l, double t) throws IllegalArgumentException {
		this.ley=l;
		this.tiempoPaso=t;
		this.tiempoActual=0.0;
		if(this.ley==null) throw new IllegalArgumentException("El valor de las leyes de la gravedad es nulo");
		if(this.tiempoPaso<=0.0) throw new IllegalArgumentException("El valor del tiempo por paso no es valido");
		this.listaO= new ArrayList<SimulatorObserver>();
		this.list= new ArrayList<Body>();

	}

	public double getTiempoPaso() {
		return tiempoPaso;
	}
	public void setTiempoPaso(double tp) {
		this.tiempoPaso=tp;
	}
	public GravityLaws getLey() {
		return ley;
	}
	
	public List<Body> getList() {
		return list;
	}
	public List<Body> setList(List<Body> b) {
		return this.list =b;
	}

	public double getTiempoActual() {
		return tiempoActual;
	}
	public void setTiempoActual(double ta) {
		this.tiempoActual=ta;
	}
	public void incrementaTiempo() {
		this.tiempoActual=tiempoActual+tiempoPaso;
	}
	public List<SimulatorObserver> getListaO(){
		return this.listaO;
	}
	
	public void setGravityLaws(GravityLaws gravityLaws) throws IllegalArgumentException {
		if(gravityLaws==null) throw new IllegalArgumentException("gravityLaws es null");
		else this.ley=gravityLaws;
		if(!this.getListaO().isEmpty()) {
		for(SimulatorObserver o:this.getListaO()) {
			o.onGravityLawChanged(gravityLaws.toString());
		}
		}
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException {
		if(dt<=0) throw new IllegalArgumentException("deltaTime es "+dt);
		else this.setTiempoPaso(dt);
		if(!this.getListaO().isEmpty()) {
			for(SimulatorObserver o:this.getListaO()) {
				o.onDeltaTimeChanged(dt);
			}
		}

	}



	//añade el cuerpo b al simulador
	//debe comprobar que no existe un cuerpo con el mismo identificador, y si existe,
	//lanzar una excepcion del tipo IllegalArgumentException

	public void addBody(Body b) throws IllegalArgumentException {

		if(this.getList().isEmpty()) {

			this.list.add(b);
			this.setList(list);

		}


		else {
			List<Body >bodies = this.getList();
			for(Body bs:bodies) {
				if(b.getId() == bs.getId()) throw new IllegalArgumentException("Los cuerpos tienen ids repetidos");
			}
			bodies.add(b);
			this.setList(bodies);

		}



		if(!this.getListaO().isEmpty()) {
			for(SimulatorObserver o:this.getListaO()) o.onBodyAdded(list, b);
		}
	}






	public void advance() throws IllegalArgumentException { 
		List<Body> bodies = this.getList();
		this.getLey().apply(bodies);;
		for(Body b:bodies) {
			b.move(this.getTiempoPaso());
		}
		if(!this.getListaO().isEmpty()) {
			for(SimulatorObserver o:this.getListaO()) {
				o.onAdvance(bodies, this.getTiempoActual());
			}
		}


		this.incrementaTiempo();
		//1__ primero llama al métodoapply de las leyes de la gravedad
		//2__ llama a move(dt) para cada cuerpo, donde dt es el tiempo real por paso
		//3__ finalmente incrementa el tiempo actual en dt segundos.
	}
	public void reset() {
		this.list.clear();
		this.setTiempoActual(0.0);
		if(!this.getListaO().isEmpty()) {
			for(SimulatorObserver o:this.getListaO()) {
				o.onReset(this.getList(), this.getTiempoActual(),this.getTiempoPaso(), this.getLey().toString());
			}
		}

	}
	public void addObserver(SimulatorObserver o) {
		if(o==null) throw new IllegalArgumentException("el observer es null");
		else {
			if(this.getListaO().isEmpty()) {
				this.listaO.add(o);
			}
			else {


				if(this.listaO.contains(o)) throw new IllegalArgumentException("Ya se tiene el observador" + o.toString());
				else this.listaO.add(o);


			}
			o.onRegister(this.getList(), this.getTiempoActual(), this.getTiempoPaso(), this.getLey().toString());
		}
	}
	public String toString() {
		return "{ \"time\": "+getTiempoActual() +", "+ "\"bodies\": " + getList().toString() +" }";
	}

}
