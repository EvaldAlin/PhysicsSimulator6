package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	/*
	private static String col1="id";
	private static String col2="Mass";
	private static String col3="Position";
	private static String col4="Velocity";
	private static String col5="Acceleration";
	 */
	private String[] columns;
	private List<Body> bodies;


	public List<Body> getBodies() {
		return bodies;
	}

	public void setBodies(List<Body> bodies) {
		this.bodies = bodies;
	}

	BodiesTableModel(Controller ctrl){
		bodies= new ArrayList<>();
		ctrl.addObserver(this);
		columns= new String[]{"id","Mass", "Position", "Velocity", "Acceleration"};
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getRowCount() {

		return bodies.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}
	@Override
	public String getColumnName(int column) {
		String s=null;
		switch(column) {
		case 0: s="id"; break;
		case 1: s= "Mass"; break;
		case 2: s= "Position"; break;
		case 3: s= "Velocity"; break;
		case 4: s= "Acceleration"; break;
		default: s=null; break;
		}
		return s;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b= bodies.get(rowIndex);
		switch(columnIndex) {
		case 0: return b.getId(); 
		case 1: return b.getMass(); 
		case 2: return b.getPosition(); 
		case 3: return b.getVelocity(); 
		case 4: return b.getAcceleration(); 
		default: return null;
		}

	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				setBodies(bodies);
				fireTableStructureChanged();
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
				fireTableStructureChanged();
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
				fireTableStructureChanged();
			}
		});


	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				setBodies(bodies);
				fireTableStructureChanged();

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
