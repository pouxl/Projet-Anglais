package KaraokeTime.GestionKaraoke;

import java.util.*;
import java.io.*;

import KaraokeTime.EvenementKaraoke;
import KaraokeTime.GestionKaraoke.ChargeurPlugins;
import KaraokeTime.GestionKaraoke.AudioPluginKaraoke;

/** Classe s'occupant de la gestion de la fenetre de playlist et de controles karaoke 
 *  ainsi que de ses plugins audio
 */
public class GestionPlaylistKaraoke extends Observable {
		private Vector pluginsAudioName = new Vector();
		private Vector pluginsAudioClass = new Vector();
		private AudioPluginKaraoke pluginAudio = null;
		private Vector listeFichiers = new Vector();
		private EvenementKaraoke even;

		/** Constructeur de l'objet GestionPlaylistKaraoke
		 */
		public GestionPlaylistKaraoke () {
				even = new EvenementKaraoke();
				chargePlugins();

		}
		
		/** repete aux observeurs eventuels quel est le plugin audio
		 *  actuellement en fonction
		 */
		public void repeteAudioPlugin(){
				even.modifieEvenementKaraoke(EvenementKaraoke.RepeteObservable, pluginAudio);
				setChanged();
				notifyObservers(even);
		}


		/** repete aux observeurs eventuels quel sont les paroles
		 *  de la chanson actuelle. fait appel au plugin audio pour ces donnees
		 */
		public void repeteDonnees(){
				pluginAudio.repeteDonnees();
		}


		/** repete aux observeurs eventuels quel est le titre
		 *  de la chanson actuelle
		 */
		public void repeteTitre(){
				even.modifieEvenementKaraoke(EvenementKaraoke.RepeteTitre,pluginAudio.getTitre());
				setChanged();
				notifyObservers(even);
		}


		/** est appele lorsque l'on desire fermer la fenetre
		 */
		public void terminer(){
				if (pluginAudio != null){
						pluginAudio.stop();
						pluginAudio.terminer();
				}
		}


		/** active le plugin audio situe a l'index 'index'
		 */
		public void activeAudioPlugin(int index){
				try{
						AudioPluginKaraoke pluginAudioTmp = (AudioPluginKaraoke)((Class)(pluginsAudioClass.elementAt(index))).newInstance();
						even.modifieEvenementKaraoke(EvenementKaraoke.NouvelObservable, pluginAudioTmp);
						setChanged();
						notifyObservers(even);
						if (pluginAudio != null){
								pluginAudio.stop();
								pluginAudio.terminer();
						}
						pluginAudio = pluginAudioTmp;
				}
				catch (InstantiationException e ){
						System.out.println("Le plugin " + pluginsAudioName.elementAt(index) + " ne peut pas etre instancie");
				}
				catch (IllegalAccessException e ){
						System.out.println("Le plugin " + pluginsAudioName.elementAt(index) + " est interdit d'acces");
				}
		}



		/** rend le plugin audio actuel
		 */
		public AudioPluginKaraoke getAudioPlugin(){
						return pluginAudio;
		}


		/** repere et charge les differents plugins audio
		 */
		public void chargePlugins(){
				ChargeurPlugins.chargePlugins("AudioPlugins", "audio", pluginsAudioClass, pluginsAudioName);
		}


		/** rend le vecteur representant le nom des plugins
		 */
		public Vector getPluginsAudioName(){
				return pluginsAudioName;
		}


		/** rend l'index d'un plugin de nom 'name'
		 */
		public int getIndexAudioName(String name){
				return pluginsAudioName.indexOf(name);
		}


		/** rend le vecteur representant la classe des plugins
		 */
		public Vector getPluginsAudioClass(){
				return pluginsAudioClass;
		}


		/** debute la lecture du fichier musical situe a l'index 'index' dans la playlist en utilisant le plugin audio actuel
		 */
		public void debuteLecture(int index){
				File fich;
				if (index != -1)
						fich = (File)listeFichiers.elementAt(index);
				else
						fich = null;
				pluginAudio.stop();
				if (pluginAudio.chargeFichier(fich) == 1){
						pluginAudio.envoieDonnees();
						even.modifieEvenementKaraoke(EvenementKaraoke.NouveauTitre,pluginAudio.getTitre());
						setChanged();
						notifyObservers(even);
						pluginAudio.play();
				}
		}


		/** arrete la lecture du fichier musical actuellement en lecture
		 */
		public void stoppeLecture(){
				pluginAudio.stop();
		}


		/** ajoute un fichier musical a la playlist
		 */
		public void ajoutePlaylist(File fich){
				listeFichiers.add(fich);
		}

		/** supprime un fichier musical a la playlist situe a l'index 'index'
		 */
		public void supprimePlaylist(int index){
				listeFichiers.removeElementAt(index);
		}

		/** monte l'element a l'index 'index' d'un cran
		 *  rend l'index resultant
		 */
		public int Up(int index){
				if (index > 0){
						Object tmp = listeFichiers.set(index - 1, listeFichiers.elementAt(index));
						listeFichiers.set(index, tmp);
						return (index - 1);
				}
				else
						return index;
		}

		/** descend l'element a l'index 'index' d'un cran
		 *  rend l'index resultant
		 */
		public int Down(int index){
				if (index < (listeFichiers.size()  - 1)){
						Object tmp = listeFichiers.set(index + 1, listeFichiers.elementAt(index));
						listeFichiers.set(index, tmp);
						return (index + 1);
				}
				else
						return index;
		}


		/** Sauvegarde de la playlist par serialisation dans un fichier binaire
		 *  On lui donne le nom du fichier en argument 
		 */
		public void sauverPlaylist(File fichier){
				try {
						if ((fichier.toString()).endsWith(".kpl")){
								ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier));
								out.writeObject(listeFichiers);
								out.close();
						}
						else{
								ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier.toString() + ".kpl"));
								out.writeObject(listeFichiers);
								out.close();
						}
				}
				catch (IOException e) {
						System.err.println("Erreur d'ecriture de la playlist dans " + fichier);
				}
		}
		

		/** Chargement de la playlist par serialisation d'un fichier binaire
		 *  On lui donne le nom du fichier en argument 
		 */
		public void chargerPlaylist(File fichier){
				listeFichiers = new Vector();
				try {
						if ((fichier.toString()).endsWith(".kpl")){
								ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichier));
								listeFichiers = (Vector)in.readObject();
								in.close();
						}
						else
								System.out.println("Le fichier "+ fichier + " n'est pas un .kpl");
				}
				catch (IOException e) {
						System.err.println("Erreur de lecture de la playlist dans " + fichier);
				}
				catch (ClassNotFoundException e) {
						System.err.println("Erreur de lecture de la playlist dans " + fichier + " (classe non trouvee)");
				}
		}
		
		/** rend le vecteur representant la playlist
		 */
		public Vector getPlaylist(){
				return listeFichiers;
		}
}
