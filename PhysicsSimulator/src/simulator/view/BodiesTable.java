package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	
	/**
	 * 
	 */
	private JTable tabla;
	private static final long serialVersionUID = 1L;

	BodiesTable(Controller ctrl){
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Bodies",
				TitledBorder.LEFT,TitledBorder.TOP));
		
		BodiesTableModel modeloTabla = new BodiesTableModel(ctrl);
		this.tabla = new JTable(modeloTabla);
		JScrollPane jScrollTabla= new JScrollPane(tabla);
		
		add(jScrollTabla);
		
		//1--->Creando una instancia de BodiesTableModel que se le pase a la JTable
		//2--->añadiendo la JTable al panel(es decir, a this) con un JScrollPane
	}

}
