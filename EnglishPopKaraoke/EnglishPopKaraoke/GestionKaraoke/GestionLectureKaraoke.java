package EnglishPopKaraoke.GestionKaraoke;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import EnglishPopKaraoke.EvenementKaraoke;
import EnglishPopKaraoke.GestionKaraoke.ChargeurPlugins;
import EnglishPopKaraoke.GestionKaraoke.InformationKaraoke;
import EnglishPopKaraoke.GestionKaraoke.VisualPluginKaraoke;

/** Classe s'occupant de la gestion de la fenetre de visualisation 
 *  ainsi que de ses plugins visuels
 */
public class GestionLectureKaraoke extends Observable implements Observer {
		private Observable obs = null;
		private	VisualPluginKaraoke plug = null;
		private Vector pluginsVisualName = new Vector();
		private Vector pluginsVisualClass = new Vector();
		private EvenementKaraoke even;

		private EnvoyeurParoles envoieParoles = new EnvoyeurParoles();
		private EnvoyeurTitre envoieTitre = new EnvoyeurTitre();
		private EnvoyeurAuteur envoieAuteur = new EnvoyeurAuteur();
		private ActualiseurTemps actualiseTemps = new ActualiseurTemps();

		private InformateurDebLecture debLect = new InformateurDebLecture();
		private InformateurFinLecture finLect = new InformateurFinLecture();

		private Vector textEvents = null;
		private Vector timeEvents = null;
		private Vector text = null;
		private String nomChanson = null;
		private String auteurChanson = null;
		private int tempsCourant = 0;

		/** Constructeur de la classe GestionLectureKaraoke
		 */
		public GestionLectureKaraoke() {
				even = new EvenementKaraoke();
				ChargeurPlugins.chargePlugins("VisualPlugins", "visual", pluginsVisualClass, pluginsVisualName);

				if (pluginsVisualClass.size() != 0) {
						changePlugin(0);
				}
		}
		

		
		/** Ce thread (aindi que les autre du meme type) 
		 *   permet de rendre plus rapidement la main a la classe Observable pour qu'elle puisse 
		 *  vite prevenir les autres observeurs (les autres fenetres de lecture)
		 */
		private class EnvoyeurParoles implements Runnable {
				public EnvoyeurParoles () {
				}
				public void run() {
						plug.changeParoles(text, textEvents, timeEvents);
				}
		}
		


		/** Ce thread (aindi que les autre du meme type) 
		 *   permet de rendre plus rapidement la main a la classe Observable pour qu'elle puisse 
		 *  vite prevenir les autres observeurs (les autres fenetres de lecture)
		 */
		private class EnvoyeurAuteur implements Runnable {
				public EnvoyeurAuteur () {
				}
				public void run() {
						plug.changeAuteur(auteurChanson);
				}
		}
		


		/** Ce thread (aindi que les autre du meme type) 
		 *   permet de rendre plus rapidement la main a la classe Observable pour qu'elle puisse 
		 *  vite prevenir les autres observeurs (les autres fenetres de lecture)
		 */
		private class EnvoyeurTitre implements Runnable {
				public EnvoyeurTitre () {
				}
				public void run() {
						plug.changeNom(nomChanson);
				}
		}
		


		/** Ce thread (aindi que les autre du meme type) 
		 *   permet de rendre plus rapidement la main a la classe Observable pour qu'elle puisse 
		 *  vite prevenir les autres observeurs (les autres fenetres de lecture)
		 */
		private class ActualiseurTemps implements Runnable {
				public  ActualiseurTemps() {
				}
				public void run() {
						plug.actualise(tempsCourant);
				}
		}



		/** Ce thread (aindi que les autre du meme type) 
		 *   permet de rendre plus rapidement la main a la classe Observable pour qu'elle puisse 
		 *  vite prevenir les autres observeurs (les autres fenetres de lecture)
		 */
		private class InformateurDebLecture implements Runnable {
				public InformateurDebLecture () {
				}
				public void run() {
						plug.debuteLecture();
				}
		}



		/** Ce thread (aindi que les autre du meme type) 
		 *   permet de rendre plus rapidement la main a la classe Observable pour qu'elle puisse 
		 *  vite prevenir les autres observeurs (les autres fenetres de lecture)
		 */
		private class InformateurFinLecture implements Runnable {
				public InformateurFinLecture () {
				}
				public void run() {
						plug.finLecture();
				}
		}
	 


		/** Methode qui observe un evenement de type 'InformationKaraoke' ou 'EvenementKaraoke'
		 */
		public void update(Observable observable, Object o) {
				if (o instanceof InformationKaraoke) {
						InformationKaraoke info = new InformationKaraoke();
						info = (InformationKaraoke)o;
						switch (info.getTypeInfo()) {
						case InformationKaraoke.typeInfoNomChanson : 
								nomChanson = (String)info.getObjectInfo();
								new Thread(envoieTitre).start();
								break;			
						case InformationKaraoke.typeInfoAuteurChanson : 
								auteurChanson = (String)info.getObjectInfo();
								new Thread(envoieAuteur).start();
								break;			
						case InformationKaraoke.typeNouveauxText : 
								text =  ((Vector[])info.getObjectInfo())[0];
								textEvents =  ((Vector[])info.getObjectInfo())[1];
								timeEvents =  ((Vector[])info.getObjectInfo())[2];
								new Thread(envoieParoles).start();
								break;
						case InformationKaraoke.typeRepeteInfoNomChanson : 
								if (nomChanson == null){
										nomChanson = (String)info.getObjectInfo();
										new Thread(envoieTitre).start();
								}
								break;			
						case InformationKaraoke.typeRepeteInfoAuteurChanson : 
								if (auteurChanson == null){
										auteurChanson = (String)info.getObjectInfo();
										new Thread(envoieAuteur).start();
								}
								break;			
						case InformationKaraoke.typeRepeteNouveauxText : 
								if (text == null){
										text =  ((Vector[])info.getObjectInfo())[0];
										textEvents =  ((Vector[])info.getObjectInfo())[1];
										timeEvents =  ((Vector[])info.getObjectInfo())[2];
										new Thread(envoieParoles).start();
								}
								break;
						case InformationKaraoke.typeTemps : 
								tempsCourant = ((Integer)info.getObjectInfo()).intValue();
								new Thread(actualiseTemps).start();
								break;			
						case InformationKaraoke.typePlay : 
								new Thread(debLect).start();
								break;			
						case InformationKaraoke.typeStop : 
								new Thread(finLect).start();
								break;			
						}
				}
				else {
						EvenementKaraoke even = new EvenementKaraoke();
						even = (EvenementKaraoke)o;
						switch (even.getType()) {
						case EvenementKaraoke.NouvelObservable : 
								if (obs != null)
										obs.deleteObserver(this);
								obs = (Observable)even.getObject();
								obs.addObserver(this);
								break;
						case EvenementKaraoke.RepeteObservable : 
								if (obs == null){
										obs = (Observable)even.getObject();
										obs.addObserver(this);
								}
								break;
						case EvenementKaraoke.NouveauTitre : 
								even.modifieEvenementKaraoke(EvenementKaraoke.NouveauTitre, (String)even.getObject());
								setChanged();
								notifyObservers(even);
								break;			
						case EvenementKaraoke.RepeteTitre : 
								if (nomChanson == null){
										even.modifieEvenementKaraoke(EvenementKaraoke.RepeteTitre, (String)even.getObject());
										setChanged();
										notifyObservers(even);
								}
								break;			
						}
				}
		}
		
		/** est appele lorsque l'on desire quitter KaraokeTime
		 */
		public void terminer() {
				if (plug != null)
						plug.terminer();
				if (obs != null)
						obs.deleteObserver(this);
		}


		/** rend le vecteur representant le nom des plugins
		 */
		public Vector getPluginsVisualName() {
				return pluginsVisualName;
		}


		/** rend le vecteur representant la classe des plugins
		 */
		public Vector getPluginsVisualClass() {
				return pluginsVisualClass;
		}


		/** rend l'index d'un plugin de nom 'name'
		 */
		public int getIndexVisualName(String name){
				return pluginsVisualName.indexOf(name);
		}


		/** rend le plugin visuel actuel
		 */
		public VisualPluginKaraoke getPlugin(){
				return plug;
		}



		/** change le plugin visuel actuel
		 */
		public void changePlugin(int indexelmt) {
				Class c = null;
				try{
						if (plug != null)
								plug.terminer();
						c = (Class) pluginsVisualClass.elementAt(indexelmt);
						plug = (VisualPluginKaraoke)c.newInstance();
				}
				catch (InstantiationException e ){
						System.out.println("Le plugin " + c + " ne peut pas etre instancie");
				}
				catch (IllegalAccessException e ){
						System.out.println("Le plugin " + c + " est interdit d'acces");
				}

				plug.changeNom(nomChanson);
				plug.changeAuteur(auteurChanson);
				plug.changeParoles(text, textEvents, timeEvents);
				even.modifieEvenementKaraoke(EvenementKaraoke.NouveauJPanel,plug.creeJPanel() );
				setChanged();
				notifyObservers(even);			
		}
}


	
