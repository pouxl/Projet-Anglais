package KaraokeTime.GestionKaraoke;

import javax.swing.*;
import java.util.*;

import KaraokeTime.GestionKaraoke.PluginKaraoke;


/** Skelette d'un plugin Visuel Karaoke TIme
 *  Plugin graphique de lecture karaoke
 *  Un plugin Visuel doit heriter de cette classe
 *  Il devra redefinir une grande partie des methodes
 *
 */
public class VisualPluginKaraoke implements PluginKaraoke {
		protected Vector textEvents = null;
		protected Vector timeEvents = null;
		protected Vector text = null;
		protected String nomChanson = null;
		protected String auteurChanson = null;
				
		/** Constructeur du plugin 
		 *  peut etre redefini mais doit faire un appel a super
		 */
		public VisualPluginKaraoke () {
				super();
		}
				
		/** Permet de connaitre la nature visuelle du plugin
		 *  Ne pas modifier
		 */
		public String getType() {
				return "visual";
		}
		
		/** Rend le nom du plugin 
		 *  (a redefinir par chaque plugin)
		 */
		public String getName() {
				return "Plugin de base";
		}

		/** Chaque plugin visuel doit creer un JPanel
		 *  (a redefinir par chaque plugin)
		 */
		public JPanel creeJPanel(){
				return new JPanel();
		}


		/** est appelee lorsque un nouveau 'mot' doir etre affiche 
		 *  (a redefinir par chaque plugin)
		 */
		public void actualise(int index){
		}

		/** est appelee lorsque la lecture commence
		 *  (a redefinir par chaque plugin)
		 */
		public void debuteLecture(){
		}

		/** est appelee lorsque la lecture est terminee
		 *  (a redefinir par chaque plugin)
		 */
		public void finLecture(){
		}


		/** est appelee lorsque le plugin est arrete 
		 *  (a redefinir par chaque plugin)
		 */
		public void terminer(){
		}


		/** est appelee lorsque un changement de nom de chanson a lieu
		 *  peut etre redefini mais doit faire un appel a super
		 */
		public void changeNom(String nm){
				nomChanson = nm;
		}



		/** est appelee lorsque un changement d'auteur a lieu
		 *  peut etre redefini mais doit faire un appel a super
		 */
		public void changeAuteur(String au){
				auteurChanson = au;
		}


		/** est appelee lorsque un changement de paroles a lieu
		 *  peut etre redefini mais doit faire un appel a super
		 */
		public void changeParoles(Vector t, Vector txe, Vector tme){
				textEvents = txe;
				timeEvents = tme;
				text = t;
		}
		

		/** methode permettant de concactener tous les
		 *  mots jusqu'au prochain retour de ligne
		 *  Trouve la ligne suivant celle qui est en cours
		 */

		public String trouveProchaineLigne(int index){
				if (index >= (textEvents.size() - 1))
						return " ";
				int ind = index + 1;
				while((ind < textEvents.size()) && (((Integer)textEvents.elementAt(ind)).intValue() == VisualPluginKaraoke.RIEN)) {
						ind++;
				}
				return trouveLigne(ind);
		}
		
		/** methode permettant de concactener tous les
		 *  mots jusqu'au prochain retour de ligne
		 */

		public String trouveLigne(int index){
				if (index > (textEvents.size() - 1))
						return " ";
				if (index == (textEvents.size() - 1))
						return ((String)text.elementAt(index));
				String res = (String)text.elementAt(index);
				int ind = index + 1;
				while((ind < textEvents.size()) && (((Integer)textEvents.elementAt(ind)).intValue() == VisualPluginKaraoke.RIEN)) {
						res = res + ((String)text.elementAt(ind));
						ind++;
				}
				return res;
		}

		/** methode permettant de rendre un temps affichable a partir d'un 
		 *  temps exprime en millisecondes
		 */
		public String transformeTemps(long temps){
				String scent = "";
				String sseco = "";
				String sminu = "";
				long cent = (long)((temps %1000) / 10);
				long seco = (long)(temps / 1000);
				long minu = (long)(seco / 60);
				seco = (long)(seco % 60);

				scent = (new Long(cent)).toString();
				sseco = (new Long(seco)).toString();
				sminu = (new Long(minu)).toString();

				if (scent.length() == 1)
						scent = "0" + scent;

				if (sseco.length() == 1)
						sseco = "0" + sseco;

				if (sminu.length() == 1)
						sminu = "0" + sminu;

				return(sminu + ":" + sseco + ":" + scent );
		}


		/** les elements du vecteur sont des Long representant des millisecondes
		 *  Ne pas modifier
		 */
		public Vector getTimeEvents() {
				return timeEvents;
		}

		/** les elements du vecteur sont des Integer
		 *  Ne pas modifier
		 */
		public Vector getTextEvents() {
				return textEvents;
		}

		/** les elements du vecteur sont des String
		 *  Ne pas modifier
		 */
		public Vector getText() {
				return text;
		}


}
