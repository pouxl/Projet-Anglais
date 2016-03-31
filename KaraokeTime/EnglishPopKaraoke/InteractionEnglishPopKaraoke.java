package EnglishPopKaraoke;

import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import EnglishPopKaraoke.GestionKaraoke.*;
import EnglishPopKaraoke.GraphiqueKaraoke.*;

import java.io.*;

/** Classe d'interaction entre la fenetre graphique de Playlist KaraokeTime et 
 *  et la classe de gestion de celle-ci.
 *  c'est l'objet a creer pour lancer KARAOKE TIME
 */
public class InteractionEnglishPopKaraoke implements  ActionListener, ItemListener{
		private FenetrePlaylistKaraoke  fenetrePlaylist;
		private GestionPlaylistKaraoke  gestionPlaylist;
		private JFileChooser chooser;
		private	ExampleFileFilter filter = new ExampleFileFilter( "kpl", "EnglishPopKaraoke Playlist");

		/** Constructeur de EnglishPopKaraoke
		 */
		public InteractionEnglishPopKaraoke () {
				gestionPlaylist = new GestionPlaylistKaraoke();

				// coordonnees pour positionner les fenetres au milieu de l'ecran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int WIDTH = 700;
        int HEIGHT = 400;

				fenetrePlaylist = new FenetrePlaylistKaraoke (this, this, gestionPlaylist.getPluginsAudioName(),WIDTH - 500 , HEIGHT);
        fenetrePlaylist.setLocation(screenSize.width/2 - WIDTH/2 + 500, screenSize.height/2 - HEIGHT/2);
				chooser = new JFileChooser();
	

				if ((gestionPlaylist.getPluginsAudioClass()).size() != 0) {
						gestionPlaylist.activeAudioPlugin(0);
						gestionPlaylist.addObserver(fenetrePlaylist);
				}
				nouvelleFenetre(700, 400, 200);
		}
		

		/** analyse les actions executees par l'utilisateur sur l'interface
		 */
		public void actionPerformed(ActionEvent e) {
				String bouton = e.getActionCommand();
				if (bouton == "Exit"){
						fenetrePlaylist.terminer();
						gestionPlaylist.terminer();
						System.exit(0);
				}
				if (bouton == "Play"){
						int tmp = fenetrePlaylist.getSelectedIndex();
								gestionPlaylist.debuteLecture(tmp);
				}
				if (bouton == "Stop"){
						gestionPlaylist.stoppeLecture();
				}
				
				if (bouton == "Delete"){
						int tmp = fenetrePlaylist.getSelectedIndex();
						if (tmp != -1){
								gestionPlaylist.supprimePlaylist(tmp);
								fenetrePlaylist.changePlaylist(gestionPlaylist.getPlaylist());
						}
				}
				
				if (bouton == "Up"){
						int tmp = fenetrePlaylist.getSelectedIndex();
						if (tmp != -1){
								int res = gestionPlaylist.Up(tmp);
								fenetrePlaylist.changePlaylist(gestionPlaylist.getPlaylist());
								fenetrePlaylist.setSelectedIndex(res);
						}
				}
				
				if (bouton == "Down"){
						int tmp = fenetrePlaylist.getSelectedIndex();
						if (tmp != -1){
								int res = gestionPlaylist.Down(tmp);
								fenetrePlaylist.changePlaylist(gestionPlaylist.getPlaylist());
								fenetrePlaylist.setSelectedIndex(res);
						}
				}
				
				if (bouton == "Add"){
						chooser.setDialogTitle("Add a karakoe file"); 
						chooser.setFileFilter(chooser.getAcceptAllFileFilter()); 
						chooser.resetChoosableFileFilters();
						int returnVal = chooser.showOpenDialog(fenetrePlaylist);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
								File fich = chooser.getSelectedFile();
								gestionPlaylist.ajoutePlaylist(fich);
								//Multi pas encore implemente par java
								//File[] fich = chooser.getSelectedFiles();
								//for (int j = 0; j < fich.length; j ++) {
								//		System.out.println("Fichier : " + fich[j]);
								//		listeFichiers.add(fich[j]);
								//}
								fenetrePlaylist.changePlaylist(gestionPlaylist.getPlaylist());
						}
				}
				if (bouton == "Save"){
						chooser.setDialogTitle("Saving playlist"); 
						chooser.resetChoosableFileFilters();
						chooser.addChoosableFileFilter(filter);
						chooser.setFileFilter(filter);
						int returnVal = chooser.showSaveDialog(fenetrePlaylist);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
								File fich = chooser.getSelectedFile();
								gestionPlaylist.sauverPlaylist(fich);
								fenetrePlaylist.changePlaylist(gestionPlaylist.getPlaylist());
						}
				}
				if (bouton == "Load"){
						chooser.setDialogTitle("Loading a playlist");
						chooser.resetChoosableFileFilters();
						chooser.addChoosableFileFilter(filter);
						chooser.setFileFilter(filter);
						int returnVal = chooser.showOpenDialog(fenetrePlaylist);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
								File fich = chooser.getSelectedFile();
								gestionPlaylist.chargerPlaylist(fich);
								fenetrePlaylist.changePlaylist(gestionPlaylist.getPlaylist());
						}
				}
				if (bouton == "New"){
						nouvelleFenetre(400, 300, 0);
				}
				if (bouton == "Load"){
						//System.out.println("nb d' Observeurs de gestionPlaylist : " + gestionPlaylist.countObservers() );
				}
		}

		/** Cree une nouvelle fenetre de visualisation KaraokeTime
		 */
		public void nouvelleFenetre(int  WIDTH, int HEIGHT, int decal){
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				new InteractionLectureKaraoke(gestionPlaylist, screenSize.width/2 - WIDTH/2, screenSize.height/2 - HEIGHT/2,WIDTH - decal, HEIGHT);
				gestionPlaylist.repeteTitre();
				gestionPlaylist.repeteAudioPlugin();
				gestionPlaylist.repeteDonnees();
		}


		
		/** est appellee si un changement de plugin audio doit avoir lieu
		 */
		public void itemStateChanged(ItemEvent e) {
				//c'est la que se fait le changement de plugin audio
				if (e.getStateChange() == e.DESELECTED){
						//System.out.println(e.getItem() + " Deselectionne");
				}
				else {
						int nb  = gestionPlaylist.getIndexAudioName((String)e.getItem());   
						gestionPlaylist.activeAudioPlugin(nb);
						//System.out.println(e.getItem() + " Selectionne");
				}
		}		
		
		/** Methode main creant EnglishPop
		 */
		public static void main (String argv[]) {
				new InteractionEnglishPopKaraoke ();
		}


}
