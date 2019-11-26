package simulator.model;

import java.util.List;


public interface GravityLaws {
	/*
	 * Este método, en las clases que implementan esta interfaz, debe aplicar las leyes de la
		gravedad particulares de la clase para cambiar las propiedades (e
	 */
	public void apply(List<Body>bodies);
}
