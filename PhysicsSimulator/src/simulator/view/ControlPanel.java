package simulator.view;


import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.InputStream;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;

import simulator.control.Controller;

import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton loadButton; //cargar archivo boton
	private JButton physicsButton; //ley boton
	private JButton runButton; //run boton
	private JButton stopButton; //stop boton
	private JButton exitButton; //salir boton
	private JSpinner selectorSteps;
	private JTextField deltaTimeText;
	private JFileChooser selectorArchivo;
	private JSpinner delay;
	private volatile Thread thread;

	private Controller ctrl;


	ControlPanel(Controller ctrl){
		this.ctrl=ctrl;

		initGUI();
		ctrl.addObserver(this);
	}



	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));


		loadButton= new JButton("load");
		loadButton.setToolTipText("load bodies file into the editor");
		Icon load = new ImageIcon("resources/icons/open.png");
		loadButton.setIcon(load);
		loadButton.setMargin(new Insets(0, 0, 0, 0));
		add(loadButton);
		//add(loadButton,FlowLayout.LEFT);
		loadButton.setVisible(true);



		loadButton.addActionListener(new ActionListener() {



			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.reset();
				selectorArchivo = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int r= selectorArchivo.showOpenDialog(null);
				if(r==JFileChooser.APPROVE_OPTION) {
					File file= selectorArchivo.getSelectedFile();
					InputStream carga;
					try {
						carga = new FileInputStream(file);
						ctrl.loadBodies(carga);

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


				}

			}
		});

		physicsButton= new JButton("physics");
		physicsButton.setToolTipText("change the gravity law");
		Icon physics = new ImageIcon("resources/icons/physics.png");
		physicsButton.setIcon(physics);
		physicsButton.setMargin(new Insets(0, 0, 0, 0));
		add(physicsButton);
		physicsButton.setVisible(true);

		List<JSONObject> leyes =ctrl.getGravityLawsFactory().getInfo();
		JComboBox<JSONObject> leyesBox= new JComboBox<JSONObject>();
		for(JSONObject l: leyes) {

			leyesBox.addItem(l);
		}
		add(leyesBox);
		leyesBox.setVisible(false);

		/*
		String[] laws= new String[ctrl.getGravityLawsFactory().getInfo().size()];
		int i=0;
		for(JSONObject jsonL: ctrl.getGravityLawsFactory().getInfo()) {
			laws[i]=jsonL.getString("desc");
			i++;
		}

		JComboBox<String> listLaws = new JComboBox<String>(laws);
		add(listLaws);
		listLaws.setVisible(false);*/
		physicsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {



				if(leyesBox.isVisible()==false) {
					leyesBox.setVisible(true);	
				}
				else leyesBox.setVisible(false);


				JSONObject l = (JSONObject) leyesBox.getSelectedItem();
				ctrl.setGravityLaws(l);






			}
		});

		runButton= new JButton("run");
		runButton.setToolTipText("run the simulation");
		Icon run = new ImageIcon("resources/icons/run.png");
		runButton.setIcon(run);
		runButton.setMargin(new Insets(0, 0, 0, 0));
		add(runButton);
		runButton.setVisible(true);

		runButton.addActionListener(new ActionListener(){ 

			@Override
			public void actionPerformed(ActionEvent e) {

				loadButton.setEnabled(false);
				physicsButton.setEnabled(false);
				deltaTimeText.setEnabled(false);
				selectorSteps.setEnabled(false);
				runButton.setEnabled(false);
				leyesBox.setVisible(false);
				double dt = Double.parseDouble(deltaTimeText.getText());
				int n= (Integer) selectorSteps.getValue();
				int ms= (Integer) delay.getValue();
				ctrl.setDeltaTime(dt);
				
					thread= new Thread() {
					public void run() {
						run_sim(n,ms);
						
					}
				};
				
				thread.start();
				

				



			}
		});


		stopButton= new JButton("stop");
		stopButton.setToolTipText("stop the simulaiton");
		Icon stop = new ImageIcon("resources/icons/stop.png");
		stopButton.setIcon(stop);
		stopButton.setMargin(new Insets(0, 0, 0, 0));
		add(stopButton);
		stopButton.setVisible(true);

		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(thread!=null) {
					thread.interrupt();
				}


			}
		});




		JLabel delayLabel= new JLabel("Delay");
		add(delayLabel);
		SpinnerNumberModel modelDelay= new SpinnerNumberModel(1, 0, 1000, 1);
		delay = new JSpinner(modelDelay);
		add(delay);

		JLabel stepsLabel= new JLabel("Steps");
		add(stepsLabel);
		stepsLabel.setVisible(true);
		SpinnerNumberModel model= new SpinnerNumberModel(1000, 0, 10000000,1);
		selectorSteps= new JSpinner(model);
		add(selectorSteps);
		selectorSteps.setVisible(true);

		JLabel deltaLabel= new JLabel("Delta-time");
		add(deltaLabel);
		deltaLabel.setVisible(true);
		deltaTimeText = new JTextField("2500", 20);
		deltaTimeText.setName("deltaTime");

		deltaTimeText.setToolTipText("deltaTime to be changed");
		add(deltaTimeText);
		deltaTimeText.setVisible(true);





		exitButton= new JButton("exit");
		exitButton.setToolTipText("exit the aplication");
		Icon exit = new ImageIcon("resources/icons/exit.png");
		exitButton.setIcon(exit);
		exitButton.setMargin(new Insets(0, 0, 0, 0));
		add(exitButton);
		//add(exitButton,FlowLayout.RIGHT); Si descomento esta linea y comento la de arriba, lo que hace es poner este boton el más a la derecha
		//entre los demas botones, pero no teniendo en cuenta tambien el JTextField, los JLabels o el JSpinner
		exitButton.setVisible(true);

		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int eleccion = JOptionPane.showConfirmDialog(null, "Realmente desea salir de la aplicacion?", "Exit", JOptionPane.YES_NO_OPTION);
				if(eleccion==0) {
					System.exit(0);
				}


			}
		});



	}

	private void run_sim(int n,int ms) {
		while(n > 0 && !Thread.currentThread().isInterrupted()) {
			try {
				ctrl.run(1);

			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.toString());
				Thread.currentThread().interrupt();
				this.loadButton.setEnabled(true);
				this.physicsButton.setEnabled(true);
				this.deltaTimeText.setEnabled(true);
				this.selectorSteps.setEnabled(true);
				this.runButton.setEnabled(true);
				/*this.loadButton.setEnabled(true);
				this.physicsButton.setEnabled(true);
				this.deltaTimeText.setEnabled(true);
				this.selectorSteps.setEnabled(true);
				this.runButton.setEnabled(true);*/


				return;

			}
			try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				this.loadButton.setEnabled(true);
				this.physicsButton.setEnabled(true);
				this.deltaTimeText.setEnabled(true);
				this.selectorSteps.setEnabled(true);
				this.runButton.setEnabled(true);
				return;
			}
			n--;	
		}
		loadButton.setEnabled(true);
		physicsButton.setEnabled(true);
		deltaTimeText.setEnabled(true);
		selectorSteps.setEnabled(true);
		runButton.setEnabled(true);
		thread=null;
		/*
			this.loadButton.setEnabled(true);
			this.physicsButton.setEnabled(true);
			this.deltaTimeText.setEnabled(true);
			this.selectorSteps.setEnabled(true);
			this.runButton.setEnabled(true);
		 */


	}
	/*private void run_sim(int n) {
		if(n > 0 && !stopped) {
			try {
				ctrl.run(1);
			}
			catch (Exception e) {

				JOptionPane.showMessageDialog(null, e.toString());
				this.loadButton.setEnabled(true);
				this.physicsButton.setEnabled(true);
				this.deltaTimeText.setEnabled(true);
				this.selectorSteps.setEnabled(true);
				this.runButton.setEnabled(true);

				this.stopped=true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);

				}});

		}
		else {
			this.loadButton.setEnabled(true);
			this.physicsButton.setEnabled(true);
			this.deltaTimeText.setEnabled(true);
			this.selectorSteps.setEnabled(true);
			this.runButton.setEnabled(true);
			this.stopped=true;

		}
	}*/

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {



		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				deltaTimeText.setText(Double.toString(dt));

			}
		});

	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				deltaTimeText.setText(Double.toString(dt));

			}
		});


	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String delta= Double.toString(dt);
				deltaTimeText.setText(delta);

			}
		});


	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub

	}

}
