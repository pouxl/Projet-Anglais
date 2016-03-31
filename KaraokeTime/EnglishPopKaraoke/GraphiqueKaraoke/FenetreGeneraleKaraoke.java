package EnglishPopKaraoke.GraphiqueKaraoke;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import EnglishPopKaraoke.EvenementKaraoke;

/** fenetre graphique generale de karaoke time
 *  c'est d'elle que derive toutes les fenetres graphiques
 *  Elle observe un objet de type EvenementKaraoke
 */
public class FenetreGeneraleKaraoke extends JFrame implements Observer{
		protected static final String imagesRep = "images/";
		protected String titreChanson = null;
    protected Font font = new Font("serif", Font.PLAIN, 11);
		protected int hauteur = 300;
		protected int largeur = 300;

		/** Constructeur normal de la classe FenetreGeneraleKaraoke
		 */
		public FenetreGeneraleKaraoke (int larg, int haut) {
				hauteur = haut;
				largeur = larg;
				setBackground(Color.lightGray);
				creeWindowListener();
				Toolkit tk = getToolkit();
				Image image = tk.getImage(FenetreGeneraleKaraoke.class.getResource(imagesRep + "Krt.gif"));
				setIconImage(image);

		}


		/** Constructeur minimal de la classe FenetreGeneraleKaraoke
		 */
		public FenetreGeneraleKaraoke () {
				setBackground(Color.lightGray);
				creeWindowListener();
		}
		
		/** Permet la gestion de la fermeture de la fenetre en cliquant
		 *  sur le bouton fermer du window manager
		 */
		public void creeWindowListener(){
				addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
								System.out.println("Abrupt closure of the window");
								System.exit(0);
						}
				});
		}

		/** Methode qui observe un evenement de type karaoke 'EvenementKaraoke'
		 */
		public void update(Observable observable, Object o) {
				EvenementKaraoke even = (EvenementKaraoke)o;
				switch (even.getType()) {
				case EvenementKaraoke.NouveauTitre : 
						titreChanson = (String)even.getObject();
						actualiseTitreChanson();
						break;			
				case EvenementKaraoke.RepeteTitre : 
						if (titreChanson == null){
								titreChanson = (String)even.getObject();
								actualiseTitreChanson();
						}
						break;			
				}
		}
		
		
		/** rend la taille preferee de la fenetre
		 */
		public Dimension getPreferredSize() {
				return new Dimension(largeur, hauteur);
		}

		
		/** est appele lorsque l'on desire fermer la fenetre
		 */
		public void terminer(){
				this.dispose();
		}

		/** est appele lorsque l'on desire actualiser le titre de la chanson dans la fenetre
		 */
		public void actualiseTitreChanson(){
				if (titreChanson != null)
						setTitle("Karaoke Time : " + titreChanson);
				else
						setTitle("Karaoke Time");

		}
}


	
