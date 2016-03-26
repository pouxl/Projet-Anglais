package KaraokeTime.GestionKaraoke.AudioPlugins;

import java.io.*;
import java.util.*;
import java.net.*;
import java.applet.*;

import KaraokeTime.GestionKaraoke.AudioPluginKaraoke;
import KaraokeTime.GestionKaraoke.InformationKaraoke;


/** Plugin Minute Test : envoie pendant 60 secondes les paroles representant la seconde 
 *  actuellement en cours. Il permet de tester simplement les plugins Visuels
 */
public class MinuteTest extends AudioPluginKaraoke {
		private FileInputStream in;
		//private DataInputStream dat;
		private int nbLus;
		private long tempsInit;	
		private boolean finLecture;	


		/** Constructeur du plugin
		 */
		public MinuteTest () {
				super();
		}

		/** Thread envoyant le temps aux differents observeurs des qu'un
		 *  nouveau mot doit faire son apparition
		 */
		private class EnvoyeurTemps extends Thread {
				public EnvoyeurTemps () {
				}
				public void run() {
						long tempsRes;
						int indexeActuel = 0;
						Vector te = getTimeEvents();
						while ((finLecture == false) && (indexeActuel < te.size())){
								tempsRes = System.currentTimeMillis() - tempsInit;
								if (tempsRes >= ((Long)te.elementAt(indexeActuel)).longValue()){
										envoie(InformationKaraoke.typeTemps , new Integer(indexeActuel));										
										indexeActuel++;
								}								
								try{
										this.sleep(100);
								}
								catch (InterruptedException e ){
								}
						}
						envoie(InformationKaraoke.typeStop , null);										
						finLecture = true;
				}
		}
			

		/** est appele pour charger un fichier musical
		 *  Ce plugin ne tient pas compte du fichier passe en parametre
		 */
		public int chargeFichier(File fichier){
				nomChanson ="Compteur de 61 secondes";
				auteurChanson ="personne !";
				textEvents.clear();
				timeEvents.clear();
				text.clear();
				String textadd;
				for (int i=0;i<=60;i++){
						textadd = (new Integer(i)).toString();
						if (((i+1) % 5) != 0)
								textadd = textadd + "  ";
						text.add(textadd);
						timeEvents.add(new Long(i * 1000));
						if ((i % 10) == 0)
								textEvents.add(new Integer(AudioPluginKaraoke.NOUVEAU_PAR));
						else
								if ((i % 5) == 0)
										textEvents.add(new Integer(AudioPluginKaraoke.NOUVELLE_LIGNE));
								else
										textEvents.add(new Integer(AudioPluginKaraoke.RIEN));
				}
				return 1;
		}

		

		/** Rend le nom du plugin
		 */
		public String getName() {
				return "Compteur (Test)";
		}

		/** debute la lecture
		 */
		public void play() {
				tempsInit = System.currentTimeMillis();
				finLecture = false;
				envoie(InformationKaraoke.typePlay , null);										
				(new EnvoyeurTemps()).start();
		}


		/** arrete la lecture
		 */
		public void stop() {
						finLecture = true;
		}
}
