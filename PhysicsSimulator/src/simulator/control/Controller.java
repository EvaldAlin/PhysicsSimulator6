package simulator.control;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.PhysicsSimulator;

public class Controller {

	PhysicsSimulator simulator;
	Factory<Body> factory;


	public Controller(PhysicsSimulator s, Factory<Body> f) {
		this.simulator=s;
		this.factory=f;
		
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

		//cambiado post-tutoria
		public void run(int n, OutputStream out) throws IOException {


			String states= "{\n\"states\": [\n" ; 
			byte[] bytes = states.getBytes();
			out.write(bytes);

			for(int i=0;i<n-1;i++) {
				this.simulator.advance();
				states = "\n"+this.simulator.toString() +","; 
				bytes = states.getBytes();
				out.write(bytes);
				
			}
			
			states="\n" + "]" +"\n"+"}";
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
		//modificado post-tutoria
		public void run(int n)  {


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

	
	
	
	
}
