package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


import simulator.control.Controller;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel controlPanel;
	private JPanel bodiesTable;
	private JComponent viewer;
	private JPanel statusBarPanel;
	private JPanel centralPanel;

	Controller ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		this.ctrl=ctrl;
		initGUI();
	}

	private void initGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setSize(1300, 1000);
		//this.setExtendedState(MAXIMIZED_BOTH);
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.setVisible(true);
		mainPanel.setOpaque(false);
		mainPanel.setBackground(Color.WHITE);
		
		/*JButton bt= new JButton("prueba");
		bt.setSize(200, 300);

		bt.setVisible(true);
		mainPanel.add(bt);*/

		controlPanel = new ControlPanel(this.ctrl);
		//controlPanel.setPreferredSize(new Dimension(200,50));
		mainPanel.add(controlPanel,BorderLayout.PAGE_START);
		//controlPanel.setSize(800, 200);
		controlPanel.setBackground(Color.WHITE);
		controlPanel.setVisible(true);


		bodiesTable= new BodiesTable(this.ctrl);
		bodiesTable.setVisible(true);
		//bodiesTable.setPreferredSize(new Dimension(200,100));
		bodiesTable.setBackground(Color.WHITE);
		
		viewer = new Viewer(this.ctrl);
		viewer.setBackground(Color.WHITE);
		viewer.setVisible(true);
		viewer.setPreferredSize(new Dimension(200,2000));
		/*viewer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Viewer",
				TitledBorder.LEFT,TitledBorder.TOP));*/
		
		centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
		centralPanel.setBackground(Color.WHITE);
		centralPanel.add(bodiesTable);
		centralPanel.add(viewer);

		mainPanel.add(centralPanel,BorderLayout.CENTER);
		centralPanel.setVisible(true);

		statusBarPanel = new StatusBar(this.ctrl);
		statusBarPanel.setVisible(true);
		//statusBarPanel.setPreferredSize(new Dimension(200,50));
		mainPanel.add(statusBarPanel,BorderLayout.PAGE_END);




	}






}
