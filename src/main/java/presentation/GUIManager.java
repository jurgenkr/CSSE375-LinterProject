package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data_source.Directory;
import data_source.GithubImport;
import data_source.Grabber;
import data_source.PopulateJavaFile;
import data_source.Testable;
import domain.AdapterPatternCheck;
import domain.CheckRunner;
import domain.CompositionCheck;
import domain.FacadePatternCheck;
import domain.HollywoodCheck;
import domain.MethodLengthCheck;
import domain.NamesCheck;
import domain.RedundantInterfaceCheck;
import domain.StrategyPatternCheck;
import domain.UnusedInstantiationCheck;

public class GUIManager {
	static Grabber githubGrabber;
	static PopulateJavaFile populator;
	static Directory directory;
	Testable githubImport;
	JFrame importFrame;

	public void filesToCheck() {
		this.importFrame = new JFrame("Import Method Chooser");
		JPanel importPanel = new JPanel();
		importFrame.setPreferredSize(new Dimension(500,125));
		importPanel.add(new JLabel("Would you like to import using Github or work with already imported packages?"), 
				BorderLayout.NORTH);
		JButton gitButton = new JButton("Github");
		gitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	importFrame.dispatchEvent(new WindowEvent(importFrame, WindowEvent.WINDOW_CLOSING));
				githubImport();
            }
        });
		importPanel.add(gitButton, BorderLayout.CENTER);
		JButton pkgButton = new JButton("Packages");
		pkgButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
				importFrame.dispatchEvent(new WindowEvent(importFrame, WindowEvent.WINDOW_CLOSING));
				packageImport();
            }
        });
		importPanel.add(pkgButton, BorderLayout.CENTER);
		
		importFrame.add(importPanel);
		importFrame.pack();
		importFrame.setVisible(true);
	}
	
	private void githubImport() {
		JFrame gitFrame = new JFrame("Github Import");
		gitFrame.setPreferredSize(new Dimension(700,125));
		JPanel gitPanel = new JPanel();
		
		ArrayList<GithubImport> gitImports = new ArrayList<>();
		
		gitPanel.add(new JLabel("Input the link to the project file/package you want to add, then press add. "
				+ "Once all entries are added, press submit."), BorderLayout.NORTH);
		JTextField gitTxt = new JTextField(35);
		gitPanel.add(gitTxt, BorderLayout.CENTER);
		JButton addButton = new JButton("Add");
		JLabel gitLabel = new JLabel("");
		ArrayList<String> classNames = new ArrayList<>();
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	GithubImport gitImport = new GithubImport(null);
            	try {
                	classNames.addAll(gitImport.generateClassesHelper(gitTxt.getText()));
                	gitImports.add(gitImport);
                	gitLabel.setText("Files from the selected link have been imported");
            	} catch (NullPointerException | StringIndexOutOfBoundsException e1) {
            		gitLabel.setText("Invalid link to a github project");
            	}
            }
        });
		gitPanel.add(addButton, BorderLayout.CENTER);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	 gitFrame.dispatchEvent(new WindowEvent(gitFrame, WindowEvent.WINDOW_CLOSING));
                 classSelector(classNames, true, gitImports);
            }
        });
		gitPanel.add(submitButton, BorderLayout.CENTER);
		
		gitPanel.add(gitLabel, BorderLayout.PAGE_END);
		
		gitFrame.add(gitPanel);
		gitFrame.pack();
		gitFrame.setVisible(true);
	}
	
	private void packageImport() {
		ArrayList<String> packages = new ArrayList<>();
		
		JFrame pkgFrame = new JFrame("Package Import");
		pkgFrame.setPreferredSize(new Dimension(650,125));
		JPanel pkgPanel = new JPanel();
		
		pkgPanel.add(new JLabel("Input the name of the package you want to add, then press add. Once all"
				+ " packages are added, press submit."), BorderLayout.NORTH);
		JTextField pkgTxt = new JTextField(35);
		pkgPanel.add(pkgTxt, BorderLayout.CENTER);
		JButton addButton = new JButton("Add");
		JLabel pkgLabel = new JLabel("");
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                packages.add(pkgTxt.getText());
                pkgLabel.setText("Added package "+ pkgTxt.getText());
            }
        });
		pkgPanel.add(addButton, BorderLayout.CENTER);
		
		JButton submitButton = new JButton("Submit");
		ArrayList<String> classNames = new ArrayList<>();
		submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	Directory dir;
                for (String pkg : packages) {
                	dir = new Directory(pkg);
                	ArrayList<String> classes = dir.getDirectoryString();
                	if (classes == null) {
                		JOptionPane.showMessageDialog(null, "Package " + pkg + " is not a valid package name,"
                				+ " so its classes have not been added.", "", JOptionPane.ERROR_MESSAGE);
                	} else {
                		classNames.addAll(classes);
                	}
                }
                pkgFrame.dispatchEvent(new WindowEvent(pkgFrame, WindowEvent.WINDOW_CLOSING));
                classSelector(classNames, false, null);
            }
        });
		pkgPanel.add(submitButton, BorderLayout.CENTER);
		
		pkgPanel.add(pkgLabel, BorderLayout.PAGE_END);
		
		pkgFrame.add(pkgPanel);
		pkgFrame.pack();
		pkgFrame.setVisible(true);
	}
	
	private void classSelector(ArrayList<String> classNames, boolean isGithub, ArrayList<GithubImport> gitImports) {
		ArrayList<String> added = classNames;
		ArrayList<String> removed = new ArrayList<>();
		
		JFrame selectFrame = new JFrame("Class Selector");
		selectFrame.setPreferredSize(new Dimension(975,600));
		JPanel selectPanel = new JPanel();
		
		selectPanel.add(new JLabel("Input the name of a class from the lists below you would like to add/remove from the"
				+ " classes being linted and press the respective button. Once finished, click Submit"), BorderLayout.PAGE_START);
		JTextField classNameText = new JTextField(35);
		selectPanel.add(classNameText, BorderLayout.NORTH);
		
		JLabel selectLabel = new JLabel("");
		
		DefaultListModel<String> addedModel = new DefaultListModel<String>();
		for (int i = 0; i < added.size(); i++) {
			addedModel.add(i, added.get(i));
		}
		JList<String> addedList = new JList<String>(addedModel);
		addedList.setLayoutOrientation(JList.VERTICAL);
		addedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane addedPane = new JScrollPane(addedList);
		addedPane.setPreferredSize(new Dimension(350, 450));
		
		
		DefaultListModel<String> removedModel = new DefaultListModel<String>();
		JList<String> removedList = new JList<String>(removedModel);
		removedList.setLayoutOrientation(JList.VERTICAL);
		removedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane removedPane = new JScrollPane(removedList);
		removedPane.setPreferredSize(new Dimension(350, 450));
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	String name = classNameText.getText();
            	if (removed.contains(name)) {
            		removed.remove(name);
            		added.add(name);
            		selectLabel.setText("class " + name + " added");
            		addedModel.add(addedModel.size(), name);
            		removedModel.removeElement(name);
            	} else {
            		selectLabel.setText("Class name not found in the list of removed classes");
            	}
            }
		});
		selectPanel.add(addButton, BorderLayout.NORTH);
		
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	String name = classNameText.getText();
            	if (added.contains(name)) {
            		added.remove(name);
            		removed.add(name);
            		selectLabel.setText("class " + name + " removed");
            		removedModel.add(removedModel.size(), name);
            		addedModel.removeElement(name);
            	} else {
            		selectLabel.setText("Class name not found in the list of added classes");
            	}
            }
		});
		selectPanel.add(removeButton, BorderLayout.NORTH);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
        		CheckRunner runner = new CheckRunner(added);
        		if(isGithub) {
        			for (GithubImport gitImport : gitImports) {
        				gitImport.getGrabber().deleteFiles();
        			}
        			
        		}
            	selectFrame.dispatchEvent(new WindowEvent(selectFrame, WindowEvent.WINDOW_CLOSING));
            	checkSelector(runner);
            }
		});
		selectPanel.add(submitButton, BorderLayout.NORTH);

		
		selectPanel.add(addedPane, BorderLayout.WEST);
		selectPanel.add(new JLabel("<---- Added      Removed---->"), BorderLayout.WEST);
		selectPanel.add(removedPane, BorderLayout.EAST);
		selectPanel.add(selectLabel, BorderLayout.SOUTH);
		
		selectFrame.add(selectPanel);
		selectFrame.pack();
		selectFrame.setVisible(true);
	}
	

	private void checkSelector(CheckRunner runner) {

		String[] checks = {"Adapter Pattern Check", "Composition Check", "Facade Pattern Check", "Hollywood Check", "Method Length Check",
								"Names Check", "Redundant Interface Check", "Strategy Pattern Check", "Unused Instantiation Check"};
		
		JFrame checkFrame = new JFrame("Class Selector");
		checkFrame.setPreferredSize(new Dimension(500,600));
		JPanel checkPanel = new JPanel();
		checkPanel.add(new JLabel("Use Ctrl to select the different checks you would like to run."), BorderLayout.PAGE_START);
		
		JLabel checkLabel = new JLabel("");
		
		DefaultListModel<String> addedModel = new DefaultListModel<String>();
		for (int i = 0; i < checks.length; i++) {
			addedModel.add(i, checks[i]);
		}
		JList<String> addedList = new JList<String>(addedModel);
		addedList.setLayoutOrientation(JList.VERTICAL);
		addedList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		addedList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					final List<String> selectedValuesList = addedList.getSelectedValuesList();
					runner.resetChecks();
					for (String value : selectedValuesList) {
						switch(value) {
						case "Adapter Pattern Check":
							runner.addCheck(new AdapterPatternCheck());
							break;
							
						case "Composition Check":
							runner.addCheck(new CompositionCheck());
							break;
							
						case "Facade Pattern Check":
							runner.addCheck(new FacadePatternCheck());
							break;
							
						case "Hollywood Check":
							runner.addCheck(new HollywoodCheck());
							break;
							
						case "Method Length Check":
							runner.addCheck(new MethodLengthCheck());
							break;
							
						case "Names Check":
							runner.addCheck(new NamesCheck());
							break;
							
						case "Redundant Interface Check":
							runner.addCheck(new RedundantInterfaceCheck());
							break;
							
						case "Strategy Pattern Check":
							runner.addCheck(new StrategyPatternCheck());
							break;
							
						case "Unused Instantiation Check":
							runner.addCheck(new UnusedInstantiationCheck());
							break;
							
						default:
							break;
						}
					}
					
				}
			}
		});
		JScrollPane addedPane = new JScrollPane(addedList);
		addedPane.setPreferredSize(new Dimension(350, 450));
		checkPanel.add(addedPane);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	checkFrame.dispatchEvent(new WindowEvent(checkFrame, WindowEvent.WINDOW_CLOSING));
            	runChecks(runner);
            }
		});
		checkPanel.add(submitButton, BorderLayout.NORTH);
		
		checkFrame.add(checkPanel);
		checkFrame.pack();
		checkFrame.setVisible(true);
	}
	
	private void runChecks(CheckRunner runner) {
		JFrame runFrame = new JFrame("Check Outputs");
		runFrame.setPreferredSize(new Dimension(1000,900));
		JPanel runPanel = new JPanel();

		JTextArea output = new JTextArea(50, 70);
		output.setText(runner.runChecks());
		output.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(output);
		scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		runPanel.add(scrollPane, BorderLayout.WEST);
		
		
		JButton restartButton = new JButton("Restart");
		restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	runFrame.dispatchEvent(new WindowEvent(runFrame, WindowEvent.WINDOW_CLOSING));
            	filesToCheck();
            }
		});
		
		
		
		runPanel.add(restartButton, BorderLayout.SOUTH);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	System.exit(0);
            }
		});
		runPanel.add(exitButton, BorderLayout.SOUTH);
		
		runFrame.add(runPanel);
		runFrame.pack();
		runFrame.setVisible(true);
		
		((GithubImport) githubImport).getGrabber().deleteFiles();
		
	}
}
