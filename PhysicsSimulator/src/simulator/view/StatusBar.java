package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;



public class StatusBar extends JPanel implements SimulatorObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel currTime; //para current time
	private JLabel currLaws; //para current laws
	private JLabel numOfBodies; //para number of bodies

	private int bodies;
	StatusBar(Controller ctrl){

		initGUI();	
		this.setBodies(0);
		ctrl.addObserver(this);



	}
	public int getBodies() {
		return bodies;
	}
	public void setBodies(int bodies) {
		this.bodies = bodies;
	}
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));

		currTime = new JLabel();
		currTime.setPreferredSize(new Dimension(150,40));
		currTime.setBorder(BorderFactory.createBevelBorder(1, Color.BLACK,Color.BLACK));
		add(currTime,BorderLayout.WEST);
		currTime.setVisible(true);

		numOfBodies= new JLabel();
		numOfBodies.setPreferredSize(new Dimension(100,40));
		numOfBodies.setBorder(BorderFactory.createBevelBorder(1, Color.BLACK,Color.BLACK));
		add(numOfBodies,BorderLayout.CENTER);
		numOfBodies.setVisible(true);

		currLaws = new JLabel();
		currLaws.setPreferredSize(new Dimension(500,40));
		currLaws.setBorder(BorderFactory.createBevelBorder(1, Color.BLACK,Color.BLACK));
		add(currLaws,BorderLayout.EAST);
		currLaws.setVisible(true);

		//COMPLETAR PARA CONSTRUIR LA BARRA DE ESTADO
	}
	//AÑADIR PRIVATE/PROTECTED METHODS
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				setBodies(bodies.size());
				numOfBodies.setText("Bodies:      "+Integer.toString(getBodies()));
				currLaws.setText("Ley:      "+gLawsDesc);
				currTime.setText("Time:      "+Double.toString(time));
			}
		});




	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				setBodies(bodies.size());
				numOfBodies.setText("Bodies:      "+Integer.toString(getBodies()));
				currTime.setText("Time:      "+Double.toString(time));
			}
		});


	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setBodies(bodies.size());
				numOfBodies.setText("Bodies:      "+Integer.toString(getBodies()));

			}
		});


	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				currTime.setText("Time:      "+Double.toString(time));
			}
		});


	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				currLaws.setText("Ley:      "+gLawsDesc);


			}
		});

	}

}
