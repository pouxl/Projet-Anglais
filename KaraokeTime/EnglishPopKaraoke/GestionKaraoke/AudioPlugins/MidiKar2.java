package EnglishPopKaraoke.GestionKaraoke.AudioPlugins;

import java.io.*;
import java.util.*;

import EnglishPopKaraoke.GestionKaraoke.AudioPluginKaraoke;
import EnglishPopKaraoke.GestionKaraoke.InformationKaraoke;

import java.net.*;
import java.applet.*;

/** Plugin Midi Karaoke : permet de lire des fichiers avex l'extension .kar
 */
public class MidiKar2 extends AudioPluginKaraoke {
		private FileInputStream in;
    private AudioClip clip = null;
		private int nbLus;
		private long tempsInit;	
		private boolean finLecture;	


		/** Constructeur du plugin
		 */
		public MidiKar2 () {
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
										this.sleep(10);
								}
								catch (InterruptedException e ){
								}
						}
						finLecture = true;
						envoie(InformationKaraoke.typeStop , null);										
				}
		}
			

		/** est appele pour charger un fichier musical
		 */
		public int chargeFichier(File fichier){
				if ((fichier != null) && ((fichier.toString().toLowerCase()).endsWith(".kar"))){
						try{
								this.in = new FileInputStream(fichier);
								decodeFichier();
								in.close();
								
								URL url = fichier.toURL() ;
								clip = Applet.newAudioClip(url);
								return 1;
						}
						catch (FileNotFoundException e) {
								e.printStackTrace();
								return 0;
						}
						catch (IOException e) {
								e.printStackTrace();
								return 0;
						}
				}
				else
						return 0;
		}


		/** Trouve les paroles et les evenements temps associes au fichier .kar actuel
		 */
		public void decodeFichier(){
				int i;
				int tmp;
				int typeEvent;
				int typeMetaEvent;
				String chaine;
				long longueur;
				int longueurTotale;
				long deltaTime;
				long dureeDeltaTime = 120000000; //defaut
				double dureeTempo= 256*3600;
				double tempsMS = 0;
				boolean fin = false;
				long NombreTotalDeltaTimes	=0;			
				boolean premierTrack = true;

				Vector listeModifTempoDelta  = new Vector();
				Vector listeModifTempoTempo  = new Vector();
				Vector listeModifTempoDureePrecedante  = new Vector();
				int indexListeModifTempo = 0;

				long dureeTemporaire;
				double dureeTemporaire2;

				nbLus = 0; 
				nomChanson ="";
				auteurChanson ="";
				textEvents.clear();
				timeEvents.clear();
				text.clear();

				/******************************************************************
				 *                      ETUDE DU TRACK HEADER                     *
				 * lecture de l'equivalence en temps des delta times              *
				 *****************************************************************/

				chaine = lireTypeChunk();
				longueurTotale = lireLongChunk();
				sauter(4); 
				dureeDeltaTime = lireDureeDeltaTime();


				/******************************************************************
				 *                      ETUDE DU PREMIER TRACK                    *
				 * reperage des variations de tempo                               *
				 *****************************************************************/
				chaine = lireTypeChunk();
				longueurTotale = lireLongChunk();

				boolean finPremierTrack = false;
				NombreTotalDeltaTimes = 0;
				while (finPremierTrack == false ){
						deltaTime = lireLongueurVariable();
						NombreTotalDeltaTimes += deltaTime;
						while ((typeEvent = lireByte()) != 0xFF);
						typeMetaEvent = lireByte();
						switch(typeMetaEvent){
						case 0x51 :
								longueur = lireLongueurVariable();
								dureeTempo = lireNouveauTempoTime();
								listeModifTempoDelta.add(new Long(NombreTotalDeltaTimes));
								listeModifTempoTempo.add(new Double(dureeTempo));
								break;
							case 0x2f:
								tmp = lireByte();
								finPremierTrack = true;
								break;
						default:
								longueur = lireLongueurVariable();
								sauter((long)longueur); 
								break;
						}
				}

				/******************************************************************
				 *               ETUDE DES TRACK A PRIORI INUTILES                *
				 * recherche du track des paroles                                 *
				 *****************************************************************/

				String nomTrack = "";
				long saut = 0;

				do{
						sauter(saut);
						chaine = lireTypeChunk();
						longueurTotale = lireLongChunk();
						saut = longueurTotale;
						NombreTotalDeltaTimes = lireLongueurVariable();

						saut = saut - nbLus;
						saut = saut - 1;
						while ((typeEvent = lireByte()) != 0xFF){
								saut = saut - 1;
						}
						if (typeEvent == 0xff){
								saut = saut - 1;
								typeMetaEvent = lireByte();
							
								fin = false;
								while (fin == false){
										switch(typeMetaEvent){
										case 0x03 : 
												longueur = lireLongueurVariable();
												saut = saut - nbLus - longueur;
												nomTrack = lireChaine((int)longueur);
												fin = true;
												break;
										case 0x2f:
												saut = saut - 1;
												tmp = lireByte();
												fin = true;
												break;
										default:
												longueur = lireLongueurVariable();
												sauter(longueur);
												saut = saut - nbLus - longueur;
												break;
										}
										if (fin == false){
												NombreTotalDeltaTimes = lireLongueurVariable();
												saut = saut - nbLus;
												saut = saut - 1;
												while ((typeEvent = lireByte()) != 0xFF){
														saut = saut - 1;
												}
												saut = saut - 1;
												typeMetaEvent = lireByte();
										}
								}
						}
						
				}	 while (nomTrack.equals("Words") == false);

				/******************************* 
				 * Creation de la liste contenant le temps d'atente total avant un changement de tempo
				 *******************************/ 

				listeModifTempoDureePrecedante.add(new Double(0));
				for (int i_calcule_temps = 1; i_calcule_temps < listeModifTempoDelta.size(); i_calcule_temps++){
						dureeTemporaire =  ((Long)listeModifTempoDelta.elementAt(i_calcule_temps)).longValue() 
								- ((Long)listeModifTempoDelta.elementAt(i_calcule_temps - 1)).longValue();
						dureeTemporaire2 = ((dureeTemporaire * ((Double)listeModifTempoTempo.elementAt(i_calcule_temps - 1)).doubleValue())
																/(double)dureeDeltaTime);
						dureeTemporaire2 +=  ((Double)listeModifTempoDureePrecedante.elementAt(i_calcule_temps - 1)).doubleValue();
						listeModifTempoDureePrecedante.add(new Double(dureeTemporaire2));
				}




				/******************************************************************
				 *             ETUDE DU TRACK CONTENANT LES PAROLES               *
				 * lecture des paroles et des deltatimes associes                 *
				 *****************************************************************/

				indexListeModifTempo = 0;
				fin = false;
				while (fin == false ){
						deltaTime = lireLongueurVariable();
						while ((typeEvent = lireByte()) != 0xFF);
						typeMetaEvent = lireByte();
						switch(typeMetaEvent){
						case 0x2f:
								tmp = lireByte();
								fin = true;
								break;
						case 0x01:
								longueur = lireLongueurVariable();
								chaine = lireChaine((int)longueur);
								NombreTotalDeltaTimes += deltaTime;
								if 	((chaine.charAt(0) == '@') && (chaine.charAt(1) == 'T')){
										if (nomChanson == "")
												nomChanson = chaine.substring(2);
										else{
												if (auteurChanson == "")
														auteurChanson = chaine.substring(2);
										}
								}

								/******************************* 
								 * calcul de la duree d'attente avant l'affichage du mot
								 *******************************/ 

								indexListeModifTempo = 0;
								while ((indexListeModifTempo < (listeModifTempoDelta.size() - 1)) &&
											 (NombreTotalDeltaTimes >= ((Long)listeModifTempoDelta.elementAt(indexListeModifTempo + 1 )).longValue()) ){
										indexListeModifTempo++;
								}
								
								tempsMS = ((Double)listeModifTempoDureePrecedante.elementAt(indexListeModifTempo)).doubleValue();
								
								if (NombreTotalDeltaTimes > ((Long)listeModifTempoDelta.elementAt(indexListeModifTempo)).longValue()) {
										dureeTemporaire = NombreTotalDeltaTimes
												- ((Long)listeModifTempoDelta.elementAt(indexListeModifTempo)).longValue();
										tempsMS = tempsMS + ((dureeTemporaire * ((Double)listeModifTempoTempo.elementAt(indexListeModifTempo)).doubleValue()) 
																				 / (double)dureeDeltaTime);
								}
								

								/******************************* 
								 * affectation des valeurs (temps, mot, evenement) aux vecteurs de donnees
								 *******************************/ 

								switch(chaine.charAt(0)){
								case '/' : 
										text.add(chaine.substring(1));
										textEvents.add(new Integer(AudioPluginKaraoke.NOUVELLE_LIGNE));
										timeEvents.add(new Long((long)(tempsMS * 1000)));
										break;
								case '\\' : 
										text.add(chaine.substring(1));
										textEvents.add(new Integer(AudioPluginKaraoke.NOUVEAU_PAR));
										timeEvents.add(new Long((long)(tempsMS * 1000)));
										break;
								case '@' : 
										break;
								default :
										text.add(chaine);
										textEvents.add(new Integer(AudioPluginKaraoke.RIEN));
										timeEvents.add(new Long((long)(tempsMS * 1000)));
										break;
								}
								break;
						default:
								longueur = lireLongueurVariable();
								sauter((long)longueur); 
								break;
						}
				}
		}

		/** rend un temps affichable de maniere lisible
		 */
		public String transformeTemps(double temps, long tm){
				String chaine = "";
				int cent = (int)((temps * 100) %100);
				int seco = (int)(temps % 60);
				int minu = (int)(temps / 60);
				return(" " + minu + ":" + seco + ":" + cent +"\t" + tm  );
				//return(" " + (long)temps);
		}


		/** lit un tempo TIme sur le fichier musical
		 */
		private double lireNouveauTempoTime(){
				int[] ent = new int[3];
				double nb = 0;
				try{
						ent[0] = in.read();
						ent[1] = in.read();
						ent[2] = in.read();
						nb = (double) (((ent[0]*256) + ent[1])*256 + ent[2]);
				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
				return nb;
		}

		/** lit un delta Time sur le fichier musical
		 */
		private long lireDureeDeltaTime(){
				int[] test = new int[2];
				long nb = 0;
				try{
						test[0] = in.read();
						test[1] = in.read();
						if ((test[0] & 0x80) == 0){
								nb = ((test[0] * 256) + test[1]) * 1000000;
						}
						else{
								nb = ((test[0] & 0x7f) * (test[1]*10) * 1000000);
						}
				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
				return nb;
		}

		
		
		/** lit le type de track sur le fichier musical
		 */
		private String lireTypeChunk(){
				byte[] test = new byte[4];
				String res = "";
				try{
						in.read(test);
						res  = new String(test);
				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
				return res;
		}


		/** lit une chaine sur le fichier musical
		 */
		private String lireChaine(int longeur){
				char[] test = new char[longeur];
				byte[] test2 = new byte[longeur];
				String res = "";
				try{
					in.read(test2);
						res  = new String(test2);
				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
				return res;
		}



		/** saute 'longeur' bytes sur le fichier musical
		 */
		private void sauter(long longeur){
				try{
						in.skip(longeur); 
				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
		}


		/** lit la longueur du track sur le fichier musical
		 */
		private int lireLongChunk(){
				int[] ent = new int[4];
				int res = 0;
				try{
						//in.read(test);

						ent[0] = in.read();
						ent[1] = in.read();
						ent[2] = in.read();
						ent[3] = in.read();
						res = (((ent[0]*256) + ent[1])*256 + ent[2])*256 + ent[3];
						//System.out.println("lireLongChunk " + ent[0]+"|" + ent[1]+"|" + ent[2]+"|" + ent[3] +  " == " +  res);
 				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
				return res;
		}


		/** lit un byte sur le fichier musical
		 */
		private int lireByte(){
				int b = 0;
				try{
						b = in.read();
				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
				return b;
		}
				

		/** lit une longueur sur le fichier musical
		 */
		private long lireLongueurVariable(){
				long value = 0;
				int c;
				nbLus = 1;
				try {
						c = in.read();
						value = c;
						if ((value & 0x80) != 0) {
								value &= 0x7f;
								do  {
										nbLus++;
										value = (value << 7) + ((c = in.read()) & 0x7f);
										//System.out.println(c+ "|") ;
								}  while ((c & 0x80) != 0); 
						} 
				}
				catch (IOException e) {
						System.out.println("le fichier n'est pas un .kar valide");
						e.printStackTrace();
				}
				finally {
						return value;
				}
		}
		/** affiche la transformation d'une valeur cod�e sur 7 bits
		 */
		private long afficheLongueurVariable(String S2 ,String S ){
				long value = 0;
				int c;
				nbLus = 1;
				
				int longueur = S.length() / 2;
				byte[] test = new byte[longueur];
				
				for(int i=0;i<longueur;i++){
						test[i] = new Long((Long.parseLong(S.substring(i*2,i*2 + 2), 16))).byteValue();
				}
				ByteArrayInputStream ch = new ByteArrayInputStream(test);
				c = ch.read();
				value = c;
				if ((value & 0x80) != 0) {
						value &= 0x7f;
						do  {
								nbLus++;
								value = (value << 7) + ((c = ch.read()) & 0x7f);
						}  while ((c & 0x80) != 0); 
				} 
				System.out.println(S +  " -->" + value + " --> " + Long.toHexString(value).toUpperCase() + " =? " + S2);
				return value;
		}
		
		/** Rend le nom du plugin
		 */
		public String getName() {
				return "Midi Karaoke";
		}

		/** est appele pour lancer la lecture d'un fichier musical
		 */
		public void play() {
				if (clip != null){
						clip.play();
						tempsInit = System.currentTimeMillis();
						finLecture = false;
						envoie(InformationKaraoke.typePlay , null);										
						(new EnvoyeurTemps()).start();
				}
		}


		/** est appele pour arreter la lecture d'un fichier musical
		 */
		public void stop() {
				if (clip != null){
						finLecture = true;
						/* clip.stop() fait parfois planter la machine : on detruit 
						 * donc directement la musique par un appel au gc()
						 */
						//clip.stop();
						clip = null;
						System.gc();
						
				}
		}


		/** affiche les paroles de la chanson en cours sur la sortie standard
		 */
		public void afficheParoles(){
				Vector vect = getText();
				Vector vect2 = getTextEvents();
				System.out.println("-------------------------------------------------------");
				System.out.println("TITRE : " + getTitre());
				System.out.println("AUTEUR : " + getAuteur());
				for (int i = 0; i < vect.size(); i++) {
						switch (((Integer)vect2.elementAt(i)).intValue()) {
						case AudioPluginKaraoke.RIEN : 
								System.out.print((String)(vect.elementAt(i)));
								break;
						case AudioPluginKaraoke.NOUVELLE_LIGNE : 
								System.out.println("");
								System.out.print((String)(vect.elementAt(i)));
								break;
						case AudioPluginKaraoke.NOUVEAU_PAR : 
								System.out.println("");
								System.out.println("");
								System.out.print((String)(vect.elementAt(i)));
								break;
						default :
								break;
						}
				}
		}



		public void joue(String s){
				chargeFichier(new File(s));
				play();
				try{
						System.in.read();
						System.in.skip(1000);
				}
				catch (IOException e){
						e.printStackTrace();
				}
				stop();
		}


		public void testlong(){
        afficheLongueurVariable("00000000","00");
        afficheLongueurVariable("00000040","40");
        afficheLongueurVariable("0000007F","7F");
        afficheLongueurVariable("00000080","8100");
        afficheLongueurVariable("00002000","C000");
        afficheLongueurVariable("00003FFF","FF7F");
        afficheLongueurVariable("00004000","818000");
        afficheLongueurVariable("00100000","C08000");
        afficheLongueurVariable("001FFFFF","FFFF7F");
        afficheLongueurVariable("00200000","81808000");
        afficheLongueurVariable("08000000","C0808000");
        afficheLongueurVariable("0FFFFFFF","FFFFFF7F");
		}

		

		/** methode main permettant de tester le plugin
		 */
		public static void main (String argv[]) {
				MidiKar2 plug = new MidiKar2 ();
				//plug.testlong();
				plug.joue("j:/Divers/MidiKaraokeFiles/AlanisMorissette/HeadOverFeetAMorissette.kar");
				plug.joue("i:/midikar/QuandLaMusiqueEstBonne.kar");
				plug.joue("f:/KaraokeTime/Sounds/gloria.kar");
				plug.joue("j:/Divers/MidiKaraokeFiles/Bangles/WALK_LIK.kar");
				plug.joue("j:/Divers/MidiKaraokeFiles/Cranberries/ZOMBIE.kar");
				plug.joue("f:/KaraokeTime/Sounds/don_t_lt.kar");
				plug.joue("f:/KaraokeTime/Sounds/jesus.kar");
				plug.joue("i:/midikar/VoyageEnItalieLilicub.kar");
				plug.joue("i:/midikar/Cendrillon.kar");
				plug.joue("i:/midikar/LaigleNoir.kar");
				plug.joue("i:/midikar/LaneigeauSahara-Anggun.kar");
		}
		
}
