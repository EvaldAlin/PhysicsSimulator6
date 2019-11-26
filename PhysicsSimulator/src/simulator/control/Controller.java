package simulator.control;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {

	private PhysicsSimulator simulator;
	private Factory<Body> factory;
	private Factory<GravityLaws> factoryL;

	public Controller(PhysicsSimulator s, Factory<Body> f,Factory<GravityLaws> factoryL) {
		this.simulator=s;
		this.factory=f;
		this.factoryL=factoryL;

	}

	public void loadBodies(InputStream in)  throws IllegalArgumentException {
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		if(jsonInupt.has("bodies")) {

			JSONArray ja = jsonInupt.getJSONArray("bodies");
			for(int i=0;i<ja.length();i++) {
				Body body= this.factory.createInstance(ja.getJSONObject(i));
				this.simulator.addBody(body);

			}

		}







	}
	//cambiar este de aqui abajo
	public void run(int n, OutputStream out) throws IOException {


		String states= "{\n\"states\": [\n" ; //aqui metemos s0
		byte[] bytes = states.getBytes();
		out.write(bytes);

		for(int i=0;i<n-1;i++) {
			this.simulator.advance();
			states = "\n"+this.simulator.toString() +","; //aqui vamos metiendo de s1 en adelante por cada iteracion menos el ultimo state
			bytes = states.getBytes();
			out.write(bytes);
			
		}
		
		states="\n" + "]" +"\n"+"}"; //aqui metemos el ultimo state en el string
		//jo1.put("states",states); //aqui metemos todos los states en el json donde la key es states

		//states=jo1.getString("states");



		bytes = states.getBytes();
		out.write(bytes);
		out.close();
	}
	
	//ESTE METEDO FUNCIONA PERO ESCRIBE MUY LENTO
	/*public void run(int n, OutputStream out) throws IOException {


		String states= "{\n\"states\": [\n" +this.simulator.toString()+","; //aqui metemos s0
		JSONObject jo1 = new JSONObject();

		for(int i=0;i<n-1;i++) {
			this.simulator.advance();


			states += "\n"+this.simulator.toString() +","; //aqui vamos metiendo de s1 en adelante por cada iteracion menos el ultimo state

		}
		this.simulator.advance(); //aqui hacemos la ultima simulacion
		states+="\n"+this.simulator.toString() + "\n" + "]" +"\n"+"}"; //aqui metemos el ultimo state en el string
		jo1.put("states",states); //aqui metemos todos los states en el json donde la key es states

		states=jo1.getString("states");



		byte[] bytes = states.getBytes();
		out.write(bytes);
		out.close();
	}*/
	//cambiar este de aqui abajo
	public void runC(int n)  {


		String states= "{\n\"states\": [\n";
		
		System.out.println(states);
		for(int i=0;i<n;i++) {
			
			this.simulator.advance();
			
			states = "\n"+this.simulator.toString() +","; 
			

			System.out.println(states);
		}
		
		states="\n" + "]" +"\n"+"}";
		System.out.println(states);


	}
	/*//ESTE METEDO FUNCIONA PERO ESCRIBE MUY LENTO
	public void runC(int n)  {


		String states= "{\n\"states\": [\n" +this.simulator.toString()+","; //aqui metemos s0
		JSONObject jo1 = new JSONObject();

		for(int i=0;i<n-1;i++) {
			this.simulator.advance();


			states += "\n"+this.simulator.toString() +","; //aqui vamos metiendo de s1 en adelante por cada iteracion menos el ultimo state

		}
		this.simulator.advance(); //aqui hacemos la ultima simulacion
		states+="\n"+this.simulator.toString() + "\n" + "]" +"\n"+"}"; //aqui metemos el ultimo state en el string
		jo1.put("states",states); //aqui metemos todos los states en el json donde la key es states

		states=jo1.getString("states");

		System.out.println(states);


	}*/
	

	public void reset() {
		this.simulator.reset();
		
	}
	
	public void setDeltaTime(double dt) throws IllegalArgumentException {
		if(dt<=0) throw new IllegalArgumentException("Delta time tiene que ser mayor que 0");
		else
		this.simulator.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		this.simulator.addObserver(o);
	}
	
	public void run(int n) {
		for(int i=0;i<n;i++) {
			this.simulator.advance();
		}
	}
	
	public Factory<GravityLaws> getGravityLawsFactory(){
		return this.factoryL;
	}

	public void setGravityLaws(JSONObject info) {
		GravityLaws l = this.factoryL.createInstance(info);
		this.simulator.setGravityLaws(l);
		
	}
	public PhysicsSimulator getSimulator() {
		return this.simulator;
	}
}
