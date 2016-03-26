package KaraokeTime.GraphiqueKaraoke;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import KaraokeTime.EvenementKaraoke;

/** fenetre graphique de lecture karaoke qui s'occupe d'afficher les
 *  differents plugins de visualisation
 *  Elle observe un objet de type EvenementKaraoke
 */
public class FenetreLectureKaraoke extends FenetreGeneraleKaraoke implements Observer{
		private JButton boutonQuit;
		private JPanel plugin;
		private JComboBox imgTypeCombo;
		private Container fenetre;		

		/** Constructeur de la classe FenetreLectureKaraoke
		 */
		public FenetreLectureKaraoke(ActionListener act, ItemListener itm, Vector listePluginsVisuels, int largeur, int hauteur) {
				super(largeur, hauteur);
			
				plugin  = new JPanel();

				boutonQuit = new JButton("Close");
				boutonQuit.setToolTipText("Close the Window");
				boutonQuit.setActionCommand("Close");
				boutonQuit.setFocusPainted(false);
				boutonQuit.setMargin(new Insets(1,1,1,1));
	
				fenetre = getContentPane();
				boutonQuit.addActionListener(act);

        imgTypeCombo = new JComboBox();
				imgTypeCombo.setToolTipText("Playing plugin choice");
        imgTypeCombo.setPreferredSize(new Dimension(120, 18));
        imgTypeCombo.setFont(font);
				for (int i = 0; i < listePluginsVisuels.size(); i++) {
						imgTypeCombo.addItem(listePluginsVisuels.elementAt(i));
				}
        if (listePluginsVisuels.size() != 0)
						imgTypeCombo.setSelectedIndex(0);
        imgTypeCombo.addItemListener(itm);

				JPanel paneButton = new JPanel();
				GridBagLayout gridbag3  = new GridBagLayout();
				GridBagConstraints gridbagC3 = new GridBagConstraints();				
				paneButton.setLayout(gridbag3);
				gridbagC3.fill = GridBagConstraints.BOTH;
				gridbagC3.weightx = 1.0;
				gridbagC3.gridwidth = 1; 
				gridbag3.setConstraints(imgTypeCombo, gridbagC3); 
				paneButton.add(imgTypeCombo);
				gridbagC3.weightx = 0.39; //nombre d'or   :-))
				gridbagC3.gridwidth = GridBagConstraints.REMAINDER; 
				gridbag3.setConstraints(boutonQuit, gridbagC3); 
				paneButton.add(boutonQuit);

				paneButton.setOpaque(false);

				fenetre.setLayout(new BorderLayout());
				
				//paneCanvas.add(dessin);
				
				//c.add("Center", paneCanvas);
				fenetre.add("Center", plugin);
				fenetre.add("South", paneButton);
				actualiseTitreChanson();

				this.pack();
 				this.setVisible(true);
		}
		
		/** Methode qui observe un evenement de type karaoke 'EvenementKaraoke'
		 *  C'est une redefinition de la methode mais elle fait appel a la methode mere		
		 */
		public void update(Observable observable, Object o) {
				super.update(observable,o);
				EvenementKaraoke even = (EvenementKaraoke)o;
				switch (even.getType()) {
				case EvenementKaraoke.NouveauJPanel : 
						changeJPanel((JPanel)even.getObject());
						break;
				}
		}

		/** methode utilisee pour changer le Jpanel de la fenetre qui represente, en fait, le
		 *  plugin visuel
		 */
		public void changeJPanel(JPanel p) {
				fenetre.remove(plugin);
				plugin = p;
				fenetre.add("Center", plugin);
				fenetre.validate();
		}

		
		/** Point d'entree eventuel de la fenetre pour la creation de l'interface sans aucune gestion
		 */
		public static void main (String argv[]) {
        int WIDTH = 500;
        int HEIGHT = 350;
				Vector vector = new Vector();
				vector.add("Vis Test1");
				vector.add("Vis Test2");
				vector.add("Vis Test3");
				vector.add("Vis Test4");

				TestListener testListen = new TestListener();
				FenetreLectureKaraoke fenetre = new FenetreLectureKaraoke(testListen, testListen, vector, WIDTH, HEIGHT);

				// positionne la fenetre au milieu de l'ecran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fenetre.setLocation(screenSize.width/2 - WIDTH/2,
                          screenSize.height/2 - HEIGHT/2);

		}		
}


	
