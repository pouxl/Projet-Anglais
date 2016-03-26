package KaraokeTime.GestionKaraoke.VisualPlugins;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import com.sun.java.swing.*;
import javax.swing.JPanel;
import java.awt.image.ImageObserver;
import java.awt.Toolkit.*;

import KaraokeTime.GestionKaraoke.VisualPluginKaraoke;



/** Plugin Logo : Affiche uniquement le logo KAraoke Time
 */
public class Logo extends VisualPluginKaraoke {
		private MonPannel panPlug;



		/** Constructeur du plugin
		 */
		public Logo(){
				super();
		}



		/** est appelee lorsque un changement de nom de chanson a lieu
		 */
		public void changeNom(String nm){
				//ne fait rien
		}


		/** est appelee lorsque un changement d'auteur a lieu
		 */
		public void changeAuteur(String au){
				//ne fait rien
		}
		

		/** est appelee lorsque un changement de paroles a lieu
		 */
		public void changeParoles(Vector t, Vector txe, Vector tme){
				//ne fait rien
		}


		/** est appelee lorsque un nouveau 'mot' doir etre affiche 
		 */
		public void actualise(int index){
				//ne fait rien
		}
		


		/**  Permet de connaitre le nom du plugin
		 */
		public String getName() {
				return "Logo 'Karaoke Time'";
		}




		/** Chaque plugin visuel doit creer un JPanel
		 */
		public JPanel creeJPanel(){
				panPlug = new MonPannel();
				return panPlug;
		}


		
		/** classe creant une JPanel etant ImageObserver
		 */
		private class MonPannel extends JPanel implements ImageObserver{
				private Toolkit tk = getToolkit();
				private Image image = tk.getImage(MonPannel.class.getResource("Logo.jpg"));
				

				/** Constructeur du JPanel
				 */
				public MonPannel(){
				}
				


				/** methode paint affichant le JPanel
				 */
				public void paint(Graphics g) {
						Graphics2D g2 = (Graphics2D) g;
						
						Dimension d = getSize();
						int maxWidth = d.width;
						int maxHeight = d.height;
						
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						
						g2.drawImage(image, 0, 0, maxWidth,maxHeight, this);
				}


				/** methode repaint reaffichant le JPanel
				 */
				public void repaint(Graphics g) {
						paint(g);
				}				
		}
}
