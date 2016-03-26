package KaraokeTime.GestionKaraoke;

import javax.swing.*;
import java.util.*;
import java.io.*;

import KaraokeTime.GestionKaraoke.PluginKaraoke;
import KaraokeTime.GestionKaraoke.InformationKaraoke;


/** Skelette d'un plugin audio Karaoke TIme
 *  Plugin audio de lecture karaoke
 *  Un plugin audio doit heriter de cette classe
 *  Il devra redefinir une grande partie des methodes
 *
 *
 */
public class AudioPluginKaraoke extends Observable implements PluginKaraoke{
		//elemente des textEvents
		protected InformationKaraoke info;
		protected String nomChanson = null;
		protected String auteurChanson = null;
		protected Vector textEvents = new Vector();
		protected Vector timeEvents = new Vector();
		protected Vector text = new Vector();
			
		/** Constructeur du plugin
		 *  peut etre redefini mais doit faire un appel a super
		 */
		public AudioPluginKaraoke () {
				info = new InformationKaraoke();
		}
				
		/** Permet de connaitre la nature sonore du plugin
		 *  Ne pas modifier
		 */
		public String getType() {
				return "audio";
		}
		
		/** Rend le nom du plugin 
		 *  (a redefinir par chaque plugin)
		 */
		public String getName() {
				return "Plugin de base";
		}

		/** est appelee lorsque le plugin est arrete 
		 *  (a redefinir par chaque plugin)
		 */
		public void terminer(){
		}



		/** est appele pour charger un fichier musical
		 *  (a redefinir par chaque plugin)
		 */
		public int chargeFichier(File fichier){
				return 0;
		}

		/** est appele pour lancer la lecture d'un fichier musical
		 *  (a redefinir par chaque plugin)
		 */
		public void play() {
		}

		/** est appele pour arreter la lecture d'un fichier musical
		 *  (a redefinir par chaque plugin)
		 */
		public void stop() {
		}


		/** rend le titre de la chanson actuelle
		 *  Ne pas modifier
		 */
		public String getTitre() {
				return nomChanson;
		}


		/** rend le nom de la chanson actuelle
		 *  Ne pas modifier
		 */
		public String getAuteur() {
				return auteurChanson;
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

		/** est utilise pour envoyer des informatione aux differents observeurs
		 *  Ne pas modifier
		 */
		public void envoie(int type, Object obj){
				info.modifieInformationKaraoke(type,obj);
				setChanged();
				notifyObservers(info);
		}


		/** est utilise pour envoyer pour la premiere fois les 
		 *  differentes informations concernant la chanson en cours
		 *  Ne pas modifier
		 */
		public void envoieDonnees(){
						envoie(InformationKaraoke.typeInfoNomChanson,getTitre());
						envoie(InformationKaraoke.typeInfoAuteurChanson ,getAuteur());
						Vector[] tx = {getText(),getTextEvents(),getTimeEvents()};
						envoie(InformationKaraoke.typeNouveauxText ,tx);
		}


		/** est utilise pour envoyer a nouveau les 
		 *  differentes informations concernant la chanson en cours
		 *  Ne pas modifier
		 */
			public void repeteDonnees(){
						envoie(InformationKaraoke.typeRepeteInfoNomChanson,getTitre());
						envoie(InformationKaraoke.typeRepeteInfoAuteurChanson ,getAuteur());
						Vector[] tx = {getText(),getTextEvents(),getTimeEvents()};
						envoie(InformationKaraoke.typeRepeteNouveauxText ,tx);
		}
}
