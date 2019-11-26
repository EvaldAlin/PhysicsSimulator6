package simulator.model;


import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	private double tiempoPaso;
	private GravityLaws ley;
	private List<Body> list;
	private double tiempoActual;


	public PhysicsSimulator(GravityLaws l, double t) throws IllegalArgumentException {
		this.ley=l;
		this.tiempoPaso=t;
		this.tiempoActual=0.0;
		if(this.ley==null) throw new IllegalArgumentException("El valor de las leyes de la gravedad es nulo");
		if(this.tiempoPaso<=0.0) throw new IllegalArgumentException("El valor del tiempo por paso no es valido");
	}

	public double getTiempoPaso() {
		return tiempoPaso;
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
	public void incrementaTiempo() {
		this.tiempoActual=tiempoActual+tiempoPaso;
	}
	public void addBody(Body b) throws IllegalArgumentException {
		
		if(this.getList()==null) {
			List<Body >bodies= new ArrayList<Body>();
			bodies.add(b);
			this.setList(bodies);
		}
		else {
			List<Body >bodies = this.getList();
			for(Body bs:bodies) {
				if(b.getId() == bs.getId()) throw new IllegalArgumentException("Los cuerpos tienen ids repetidos");
			}
				bodies.add(b);
				this.setList(bodies);
			
			
			/*
			for(Body bs:bodies) {
			if(b.getId()==bs.getId()) throw new IllegalArgumentException("Los cuerpos tienen ids repetidos");
			else {
				bodies.add(b);
				
			}*/
		
		}
			
		}
		//añade el cuerpo b al simulador
		//debe comprobar que no existe un cuerpo con el mismo identificador, y si existe,
		//lanzar una excepcion del tipo IllegalArgumentException
	

	public void advance() {
		List<Body> bodies = this.getList();
		this.getLey().apply(bodies);;
		for(Body b:bodies) {
			b.move(this.getTiempoPaso());
		}
		this.incrementaTiempo();
		//1__ primero llama al métodoapply de las leyes de la gravedad
		//2__ llama a move(dt) para cada cuerpo, donde dt es el tiempo real por paso
		//3__ finalmente incrementa el tiempo actual en dt segundos.
	}/*
	public String toString() {
		JSONArray array = new JSONArray();
		array.put(this.getList());
		JSONObject listaB = new JSONObject();
		listaB.put("bodies", array);
		
		return "{ \"time\": "+getTiempoActual() +", "+ "\"bodies\": " + getList().toString();
	}*/
	public String toString() {
		return "{ \"time\": "+getTiempoActual() +", "+ "\"bodies\": " + getList().toString() +" }";
	}

}
