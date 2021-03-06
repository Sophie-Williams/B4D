package fr.B4D.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import fr.B4D.bot.B4D;
import fr.B4D.bot.B4DException;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.CaptureDeviceOpenException;

public class JFrame_B4D extends JFrame{

	private static final long serialVersionUID = -7925109992438988807L;
	
	public final static Color selectedTab = new Color(33,43,53);
	public final static Color unSelectedTab = new Color(52,63,73);

	private Point offset;
	private final int offset_x_Form = 20, offset_y_Form = 70;	
	
	private final JPanel_Programme programPanel;
	private final JPanel_Personnage personPanel;
	private final JPanel_Reglage settingPanel;
	private final JPanel_Admin adminPanel;
	
	private final JLabel lblProgrammes = new JLabel("Programmes");		//Bouton programmes
	private final JLabel lblPersonnages = new JLabel("Personnages");	//Bouton personnages
	private final JLabel lblReglages = new JLabel("R\u00E9glages");		//Bouton reglages
	private final JLabel lblAdmin = new JLabel("Admin");				//Bouton admin

	private B4D b4d;
	
	public static void main(String[] args) {
		try {
			JFrame_B4D windowB4D = new JFrame_B4D();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			windowB4D.setVisible(true);
		} catch (Exception e) {
			B4D.logger.error(e);
		}
	}
	
	public JFrame_B4D() throws B4DException, ClassNotFoundException, IOException, CaptureDeviceLookupException, CaptureDeviceOpenException, AWTException{
		b4d = new B4D();
		programPanel = new JPanel_Programme(b4d);
		personPanel = new JPanel_Personnage(b4d);
		settingPanel = new JPanel_Reglage(b4d);
		adminPanel = new JPanel_Admin(b4d);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setUndecorated(true);
		setResizable(false);
		setBackground(Color.GRAY);
		getContentPane().setBackground(unSelectedTab);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JFrame_B4D.class.getResource("/fr/B4D/icones/Dofus_Rouge.png")));	//Defini l'icone
		setTitle("B4D");									//Defini le nom de la fenetre
		setBounds(100, 100, 807, 541);					//Defini les coordonnees
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Defini l'action lors de l'appui sur la cloix
		getContentPane().setLayout(null);

		programPanel.setBounds(10, 60, 635, 235);
		getContentPane().add(programPanel);
		personPanel.setBounds(10, 60, personPanel.width, personPanel.height);
		getContentPane().add(personPanel);
		settingPanel.setBounds(10, 60, settingPanel.width, settingPanel.height);
		getContentPane().add(settingPanel);
		adminPanel.setBounds(10, 60, adminPanel.width, adminPanel.height);
		getContentPane().add(adminPanel);
		
		JLabel lblReduce = new JLabel("-");
		lblReduce.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setState(Frame.ICONIFIED);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblReduce.setForeground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblReduce.setForeground(Color.WHITE);
			}
		});
		lblReduce.setHorizontalAlignment(SwingConstants.CENTER);
		lblReduce.setForeground(Color.WHITE);
		lblReduce.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblReduce.setBounds(595, 0, 30, 30);
		getContentPane().add(lblReduce);		
		
		JLabel lblClose = new JLabel("x");
		lblClose.setVerticalAlignment(SwingConstants.BOTTOM);
		lblClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					b4d.saveConfiguration();
					b4d.saveTeam();
					B4D.socketListener.interrupt();
					B4D.keyboardListener.interrupt();
					dispose();
				} catch (IOException e1) {
					B4D.logger.error(e1);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblClose.setForeground(Color.BLACK);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblClose.setForeground(Color.WHITE);
			}
		});
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.WHITE);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblClose.setBounds(625, 0, 30, 30);
		getContentPane().add(lblClose);
		
		JLabel lblBd = new JLabel("Bot for Dofus");
		lblBd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				offset = getMousePosition();
			}
		});
		lblBd.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point point = MouseInfo.getPointerInfo().getLocation();
				setLocation(point.x - (int)offset.getX(), point.y - (int)offset.getY());
			}
		});
		
		lblBd.setOpaque(true);
		lblBd.setBackground(new Color(222,118,56));
		lblBd.setForeground(Color.WHITE);
		lblBd.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		lblBd.setBounds(0, 0, 655, 30);
		lblBd.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(JFrame_B4D.class.getResource("/fr/B4D/icones/Dofus.png"))));
		getContentPane().add(lblBd);
		
		/** PROGRAMMES PANEL **/
		lblProgrammes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changerPanel(0);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!programPanel.isVisible())
					lblProgrammes.setBackground(selectedTab);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!programPanel.isVisible())
					lblProgrammes.setBackground(unSelectedTab);
			}
		});
		lblProgrammes.setOpaque(true);
		lblProgrammes.setBackground(selectedTab);
		lblProgrammes.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgrammes.setForeground(Color.WHITE);
		lblProgrammes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblProgrammes.setBounds(10, 30, 159, 30);
		getContentPane().add(lblProgrammes);
		
		/** PERSONNAGES PANEL **/
		lblPersonnages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changerPanel(1);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!personPanel.isVisible())
					lblPersonnages.setBackground(selectedTab);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!personPanel.isVisible())
					lblPersonnages.setBackground(unSelectedTab);
			}
		});		
		lblPersonnages.setOpaque(true);
		lblPersonnages.setBackground(unSelectedTab);
		lblPersonnages.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnages.setForeground(Color.WHITE);
		lblPersonnages.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPersonnages.setBounds(169, 30, 159, 30);
		getContentPane().add(lblPersonnages);
		
		/** REGLAGES PANEL **/
		lblReglages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changerPanel(2);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!settingPanel.isVisible())
					lblReglages.setBackground(selectedTab);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!settingPanel.isVisible())
					lblReglages.setBackground(unSelectedTab);
			}
		});		
		lblReglages.setOpaque(true);
		lblReglages.setBackground(unSelectedTab);
		lblReglages.setHorizontalAlignment(SwingConstants.CENTER);
		lblReglages.setForeground(Color.WHITE);
		lblReglages.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblReglages.setBounds(328, 30, 159, 30);
		getContentPane().add(lblReglages);
		
		/** ADMIN PANEL **/
		lblAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changerPanel(3);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(!adminPanel.isVisible())
					lblAdmin.setBackground(selectedTab);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!adminPanel.isVisible())
					lblAdmin.setBackground(unSelectedTab);
			}
		});
		lblAdmin.setOpaque(true);
		lblAdmin.setBackground(unSelectedTab);
		lblAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdmin.setForeground(Color.WHITE);
		lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAdmin.setBounds(487, 30, 158, 30);
		getContentPane().add(lblAdmin);
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem itemImport = new JMenuItem("Importer", new ImageIcon(JFrame_B4D.class.getResource("/fr/B4D/icones/Import_20x20.png")));		
		itemImport.setMnemonic(KeyEvent.VK_O);
		itemImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					b4d.importFile();
					personPanel.ActualiserInfos();
					settingPanel.ActualiserInfos();
				} catch (ClassNotFoundException | IOException ex) {
					B4D.logger.popUp("Impossible d'importer le fichier, fichier corrompu.");
				}
			}
		});
		popupMenu.add(itemImport);
		
		JMenuItem itemExport = new JMenuItem("Exporter", new ImageIcon(JFrame_B4D.class.getResource("/fr/B4D/icones/Export_20x20.png")));
		itemExport.setMnemonic(KeyEvent.VK_S);
		itemExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					try {
						b4d.exportFile();
					} catch (IOException ex) {
						B4D.logger.error(ex);
					}
			}
		});
		popupMenu.add(itemExport);
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		
		changerPanel(0);
	}

	  /**************/
	 /** METHODES **/
	/**************/
	
	private void changerPanel(int index) {
		lblProgrammes.setBackground(unSelectedTab);
		lblPersonnages.setBackground(unSelectedTab);
		lblReglages.setBackground(unSelectedTab);
		lblAdmin.setBackground(unSelectedTab);		
		programPanel.setVisible(false);
		personPanel.setVisible(false);
		settingPanel.setVisible(false);
		adminPanel.setVisible(false);
		
		switch(index) {
		case 0:
			lblProgrammes.setBackground(selectedTab);
			setSize(programPanel.width + offset_x_Form, programPanel.height + offset_y_Form);
			programPanel.setVisible(true);
			break;
		case 1:
			lblPersonnages.setBackground(selectedTab);
			setSize(personPanel.width + offset_x_Form, personPanel.height + offset_y_Form);
			personPanel.setVisible(true);
			break;
		case 2:
			lblReglages.setBackground(selectedTab);
			setSize(settingPanel.width + offset_x_Form, settingPanel.height + offset_y_Form);
			settingPanel.setVisible(true);
			break;
		case 3:
			lblAdmin.setBackground(selectedTab);
			setSize(adminPanel.width + offset_x_Form, adminPanel.height + offset_y_Form);
			adminPanel.setVisible(true);
			break;
		}
	}
}

