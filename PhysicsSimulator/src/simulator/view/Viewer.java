package simulator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {

	//private static final int WIDTH= 1000;
	//private static final int HEIGTH= 1000;

	//AÑADIR CONSTANTES PARA LOS COLORES
	private static final Color azul=Color.BLUE;
	private static final Color rojo=Color.RED;
	private int centerX;
	private int centerY;
	private double scale;
	private List<Body> bodies;
	private boolean showHelp;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Viewer(Controller ctrl){
		initGUI();
		ctrl.addObserver(this);

	}
	public List<Body> getBodies() {
		return bodies;
	}
	public void setBodies(List<Body> bodies) {
		this.bodies = bodies;
	}
	private void initGUI() {
		bodies= new ArrayList<>();
		scale=1.0;
		showHelp=true;

		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Viewer",
				TitledBorder.LEFT,TitledBorder.TOP));
		addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()){
				case '-': scale=scale* 1.1; break;
				case '+': scale= Math.max(1000.0, scale / 1.1); break;
				case '=': autoScale(); break;
				case 'h': showHelp = !showHelp; break;
				default:
				}
				repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});


		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				requestFocus();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		//usar gr para dibujar, no g

		centerX= getWidth()/2;
		centerY= getHeight()/2;
		gr.setColor(rojo);
		gr.drawLine(centerX, centerY-5, centerX, centerY+5);
		gr.drawLine(centerX-5, centerY, centerX+5, centerY);
		repaint();
		gr.setColor(azul);
		for(Body b:bodies) {
			Vector p=b.getPosition();
			gr.fillOval(centerX+(int) (p.coordinate(0)/scale), centerY-(int)(p.coordinate(1)/scale), 10, 10);
			gr.drawString(b.getId(), centerX+(int) (p.coordinate(0)/scale), centerY-(int)(p.coordinate(1)/scale));


		}
		//dibuja una cruz en el centro
		//dibuja los bodies
		//pintar un ciruclo de radio 5 con centro en (centerX+(int) (x/scale), centerY-(int)(y/scale))
		//donde x e y son las coordenadas 0 y 1 del cuerpo . Tambien escribir el nombre del cuerpo junto al circulo
		//dibuja help si showhelp es =true
		//PARA DIBUJAR, USAR--->>>gr.setColor,gr.fillOval, gr.drawString, o gr.drawLine
		if(showHelp==true) {
			gr.setColor(rojo);
			String h="h: toogle help, +: zoom in, -: zoom out, =: fit";
			gr.drawString(h, 8, 35);
			String scaling="Scaling ratio: " +scale;
			gr.drawString(scaling, 8, 50);
		}
	}



	private void autoScale() {
		double max= 1.0;
		for(Body b: bodies) {
			Vector p=b.getPosition();

			for (int i=0; i<p.dim();i++) max=Math.max(max,Math.abs(b.getPosition().coordinate(i)));	

		}
		double size= Math.max(1.0, Math.min((double)getWidth(),(double)getHeight()));
		scale=max >size ? 4.0 * max/ size :1.0;

	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setBodies(bodies);
				autoScale();
				repaint();

			}
		});



	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setBodies(bodies);
				autoScale();
				repaint();		

			}
		});

	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setBodies(bodies);
				autoScale();
				repaint();

			}
		});

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				repaint();
			}
		});



	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub

	}

}
