package simulator.model;

import java.util.List;

public interface SimulatorObserver {
	//bodies es la lista de cuerpos actual
	//b es un cuerpo
	//time es el tiempo actual del simulador
	//dt es el tiempo real de cada paso de simulacion
	//glawDesc es un string que describe las leyes de la gravedadactuales(que se obtiene invocando al metodo toString() de la ley actual
	
	public void onRegister(List<Body> bodies, double time, double dt,
			String gLawsDesc);
	
	public void onReset(List<Body> bodies, double time, double dt,
			String gLawsDesc);
	
	public void onBodyAdded(List<Body> bodies, Body b);
	
	public void onAdvance(List<Body> bodies, double time);
	
	public void onDeltaTimeChanged(double dt);
	
	public void onGravityLawChanged(String gLawsDesc);
	
	
	
}

