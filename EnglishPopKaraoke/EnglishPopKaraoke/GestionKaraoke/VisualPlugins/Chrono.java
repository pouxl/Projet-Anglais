package EnglishPopKaraoke.GestionKaraoke.VisualPlugins;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import EnglishPopKaraoke.GestionKaraoke.VisualPluginKaraoke;



/** Plugin Chronometre : Affiche le temps ecoule de la chanson en cours
 */
public class Chrono extends VisualPluginKaraoke implements Runnable{
	  private JLabel temps = new JLabel("00:00:00");
		private boolean finTimeMachine = false;
		private int indexActuel = 0;
		private int indexFinal = 1;
	  private Thread timeMachine;

		/** Constructeur du plugin
		 */
		public Chrono () {
				super();
			  timeMachine = new Thread(this) ;
		}
		
		/**  Permet de connaitre le nom du plugin
		 */
		public String getName() {
				return "Stopwatch";
		}
		

		/** est appelee lorsque le plugin est arrete 
		 */
		public void terminer(){
				finTimeMachine = true;
		}



		/** Thread s'occupant d'afficher le temps (ou plutot une estimation partielle) de facon permanante
		 */
	  public void run() {
				long tempsInit = System.currentTimeMillis();
				int oldIndex = indexActuel;
				long tempsActuel;
				while ((finTimeMachine == false) && (indexActuel  <= indexFinal)){
						if (oldIndex != indexActuel){
								tempsInit = System.currentTimeMillis();
								oldIndex = indexActuel;
						}
						if ((indexActuel <= indexFinal) && (indexActuel != -1))
								tempsActuel = ((Long)timeEvents.elementAt(indexActuel)).longValue();
						else
								tempsActuel = 0;
						changeTexte((System.currentTimeMillis() - tempsInit) + tempsActuel);
						try{
								timeMachine.sleep(100);
						}
						catch (InterruptedException e ){
								}
				}
		}
		


		/** est appelee lorsque un changement de paroles a lieu
		 */
		public void changeParoles(Vector t, Vector txe, Vector tme){
				super.changeParoles(t, txe, tme);
				indexFinal = timeEvents.size() - 1;
				indexActuel = - 1; 
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


		/** est appelee lorsque la lecture commence
		 */
		public void debuteLecture(){
				finTimeMachine = false;
				if (timeMachine.isAlive() == false){
						timeMachine = new Thread(this) ;
						timeMachine.start();
				}
		}


		/** est appelee lorsque la lecture est terminee
		 */
		public void finLecture(){
				finTimeMachine = true;
				changeTexte(0);
		}
		

		
		/** est appelee lorsque un nouveau 'mot' doir etre affiche 
		 */
		public void actualise(int index){
				indexActuel = index;
				if (timeMachine.isAlive() == false){
						timeMachine = new Thread(this) ;
						timeMachine.start();
				}
		}
		
		private void changeTexte(long tmps){
				temps.setText(transformeTemps(tmps));
		}		


		/** Chaque plugin visuel doit creer un JPanel
		 */
		public JPanel creeJPanel () {
			  JPanel panPlug = new JPanel();
				panPlug.setLayout(new BorderLayout());
				panPlug.setBackground(Color.blue);
				temps.setForeground(Color.black);
				temps.setHorizontalAlignment(JLabel.CENTER);
				temps.setFont( new Font("serif", Font.BOLD, 40 ));	
				Border border = new EtchedBorder(EtchedBorder.LOWERED) ;
				Border margin = new EmptyBorder(10,10,10,10);
				temps.setBorder(new CompoundBorder(margin, border));			
				panPlug.add("Center",temps);
				return panPlug;
		}
}
