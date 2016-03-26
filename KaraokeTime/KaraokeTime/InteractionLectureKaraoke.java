package KaraokeTime;

import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import KaraokeTime.GraphiqueKaraoke.*;
import KaraokeTime.GestionKaraoke.*;


/** Classe d'interaction entre la fenetre graphique de visualisation KaraokeTime et 
 *  et la classe de gestion de celle-ci. C'est l'objet a creer pour lancer une 
 *  fenetre de visualisation Karaoke Time
 */
public class InteractionLectureKaraoke implements  ActionListener, ItemListener{
		private FenetreLectureKaraoke fenetreLecture;
		private GestionLectureKaraoke gestionLecture;
		private Observable obsPrincipal;

		/** Constructeur de l'objet fenetre de visualisation Karaoke Time
		 */
		public InteractionLectureKaraoke(Observable obs, int posx, int posy, int larg, int haut) {
				obsPrincipal = obs;
				gestionLecture = new GestionLectureKaraoke();
				obsPrincipal.addObserver(gestionLecture);

				fenetreLecture = new FenetreLectureKaraoke(this, this, gestionLecture.getPluginsVisualName(), larg, haut);

 				gestionLecture.addObserver(fenetreLecture);

				if ((gestionLecture.getPluginsVisualClass()).size() != 0) {
						gestionLecture.changePlugin(0);
				}
 
        fenetreLecture.setLocation(posx, posy);

		}


		/** est appellee lorsque l'objet doit etre supprime (bouton fermer appuye)
		 */
		public void terminer(){
				//System.out.println("nb d' Observeurs avant : " + obsPrincipal.countObservers() );
 				gestionLecture.deleteObserver(fenetreLecture);
				obsPrincipal.deleteObserver(gestionLecture);
				fenetreLecture.terminer();
				gestionLecture.terminer();
				//System.out.println("nb d' Observeurs apres : " + obsPrincipal.countObservers() );
		}


		/** analyse les actions executees par l'utilisateur sur l'interface
		 */
		public void actionPerformed(ActionEvent e) {
				String bouton = e.getActionCommand();
				//System.out.println("Bouton " + bouton  + " appuye");
				if (bouton == "Exit"){
						terminer();
				}
		}
		
		/** est appellee si un changement de plugin visuel doit avoir lieu
		 */
		public void itemStateChanged(ItemEvent e) {
				//c'est la que se fait le changement de plugin visuel
				if (e.getStateChange() == e.SELECTED) {
						int indexelmt = gestionLecture.getIndexVisualName((String)e.getItem()); 
						gestionLecture.changePlugin(indexelmt);
				}
		}		
		
}
