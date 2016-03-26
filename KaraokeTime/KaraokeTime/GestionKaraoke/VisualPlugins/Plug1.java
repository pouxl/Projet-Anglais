package KaraokeTime.GestionKaraoke.VisualPlugins;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import KaraokeTime.GestionKaraoke.VisualPluginKaraoke;



/** Plugin Neant : Ce plugin ne fait rien du tout : cool non ?!!
 */
public class Plug1 extends VisualPluginKaraoke {
				
		/** Constructeur par default de l'objet PluginKaraoke
		 */
		public Plug1 () {
				super();
		}
						
		/**  Permet de connaitre le nom du plugin
		 */
		public String getName() {
				return "Neant";
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



		/** Chaque plugin visuel doit creer un JPanel
		 */
		public JPanel creeJPanel(){
				JPanel panPlug = new JPanel();
				JButton boutonTest;
				boutonTest = new JButton("Ce Plugin ne fait rien du tout !");
				panPlug.add(boutonTest);
				boutonTest.setBackground(Color.yellow);
				panPlug.setBackground(Color.green);
				return panPlug;
		}
}
