package EnglishPopKaraoke.GraphiqueKaraoke;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/** Classe creant l'interface d'une fenetre de playlist
 *	et controles de lecture karaokeTime
 */
public class FenetrePlaylistKaraoke extends FenetreGeneraleKaraoke {
		private JButton boutonQuit;
		private JButton boutonNouv;
		private JButton boutonUp;
		private JButton boutonDown;
		private JButton boutonPlay;
		private JButton boutonStop;
		private JButton boutonPrev;
		private JButton boutonNext;

		private JButton boutonLoad;
		private JButton boutonSave;
		private JButton boutonAdd;
		private JButton boutonDelete;

    //private JComboBox sndTypeCombo;
		private JList playlist = new JList(new DefaultListModel());
		JScrollPane scrollPane = new JScrollPane(playlist);


		/** Constructeur de la classe FenetrePlaylistKaraoke
		 */
		public FenetrePlaylistKaraoke(ActionListener act, ItemListener itm,Vector listePluginsAudio, int largeur, int hauteur) {
				super(largeur, hauteur);


				// NE COMPILE PAS : POURQUOI ??????????
				//playlist.setSelectionMode(JList.SINGLE_SELECTION);


				ImageIcon imageUp = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "up.gif"));
				ImageIcon imageDown = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "down.gif"));
				ImageIcon imagePlay = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "play.gif"));
				ImageIcon imageStop = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "stop.gif"));
				ImageIcon imagePrev = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "prev.gif"));
				ImageIcon imageNext = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "next.gif"));
				
				ImageIcon imageUpb = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "upb.gif"));
				ImageIcon imageDownb = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "downb.gif"));
				ImageIcon imagePlayb = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "playb.gif"));
				ImageIcon imageStopb = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "stopb.gif"));
				ImageIcon imagePrevb = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "prevb.gif"));
				ImageIcon imageNextb = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "nextb.gif"));
				
				ImageIcon imageUpj = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "upj.gif"));
				ImageIcon imageDownj = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "downj.gif"));
				ImageIcon imagePlayj = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "playj.gif"));
				ImageIcon imageStopj = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "stopj.gif"));
				ImageIcon imagePrevj = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "prevj.gif"));
				ImageIcon imageNextj = new ImageIcon(FenetrePlaylistKaraoke.class.getResource(imagesRep + "nextj.gif"));
				
				// BOUTON UP
				boutonUp = new JButton(imageUpb);
				boutonUp.setRolloverIcon(imageUp);
				boutonUp.setPressedIcon(imageUpj);
				boutonUp.setToolTipText("Up in selection");
				boutonUp.setActionCommand("Up");
				boutonUp.setFocusPainted(false);
				boutonUp.setMargin(new Insets(1,1,1,1));
				boutonUp.addActionListener(act);

				// BOUTON DOWN
				boutonDown = new JButton(imageDownb);
				boutonDown.setRolloverIcon(imageDown);
				boutonDown.setPressedIcon(imageDownj);
				boutonDown.setToolTipText("Down inselection");
				boutonDown.setActionCommand("Down");
				boutonDown.setFocusPainted(false);
				boutonDown.setMargin(new Insets(1,1,1,1));
				boutonDown.addActionListener(act);

				// BOUTON PLAY
				boutonPlay = new JButton(imagePlayb);
				boutonPlay.setRolloverIcon(imagePlay);
				boutonPlay.setPressedIcon(imagePlayj);
				boutonPlay.setToolTipText("Play");
				boutonPlay.setActionCommand("Play");
				boutonPlay.setFocusPainted(false);
				boutonPlay.addActionListener(act);

				// BOUTON STOP
				boutonStop = new JButton(imageStopb);
				boutonStop.setRolloverIcon(imageStop);
				boutonStop.setPressedIcon(imageStopj);
				boutonStop.setToolTipText("Stop");
				boutonStop.setActionCommand("Stop");
				boutonStop.setFocusPainted(false);
				boutonStop.addActionListener(act);

				// BOUTON PREV
				boutonPrev = new JButton(imagePrevb);
				boutonPrev.setRolloverIcon(imagePrev);
				boutonPrev.setPressedIcon(imagePrevj);
				boutonPrev.setToolTipText("Previous song");
				boutonPrev.setActionCommand("Prev");
				boutonPrev.setFocusPainted(false);
				boutonPrev.addActionListener(act);

				// BOUTON NEXT
				boutonNext = new JButton(imageNextb);
				boutonNext.setRolloverIcon(imageNext);
				boutonNext.setPressedIcon(imageNextj);
				boutonNext.setToolTipText("Next song");
				boutonNext.setActionCommand("Next");
				boutonNext.setFocusPainted(false);
				boutonNext.addActionListener(act);

				// BOUTON LOAD
				boutonLoad = new JButton("LOAD");
				boutonLoad.setToolTipText("Load a playlist");
				boutonLoad.setActionCommand("Load");
				boutonLoad.setMargin(new Insets(1,1,1,1));
				boutonLoad.addActionListener(act);

				// BOUTON SAVE
				boutonSave = new JButton("SAVE");
				boutonSave.setToolTipText("Save the playlist");
				boutonSave.setActionCommand("Save");
				boutonSave.setMargin(new Insets(1,1,1,1));
				boutonSave.addActionListener(act);

				// BOUTON ADD
				boutonAdd = new JButton("ADD");
				boutonAdd.setToolTipText("Add a file");
				boutonAdd.setActionCommand("Add");
				boutonAdd.setMargin(new Insets(1,1,1,1));
				boutonAdd.addActionListener(act);

				// BOUTON DELETE
				boutonDelete = new JButton("DELETE");
				boutonDelete.setToolTipText("Delete a file");
				boutonDelete.setActionCommand("Delete");
				boutonDelete.setMargin(new Insets(1,1,1,1));
				boutonDelete.addActionListener(act);

				// BOUTON QUIT
				boutonQuit = new JButton("Exit");
				boutonQuit.setToolTipText("Exit program");
				boutonQuit.setActionCommand("Exit");
				boutonQuit.setMargin(new Insets(1,1,1,1));
				boutonQuit.addActionListener(act);

				// BOUTON NOUVELLE FENETRE
				boutonNouv = new JButton("New window");
				boutonNouv.setToolTipText("New window");
				boutonNouv.setActionCommand("New");
				boutonNouv.setMargin(new Insets(1,1,1,1));
				boutonNouv.addActionListener(act);

				// COMBO BOX CHOIX DU PLUGIN SONORE
       /* sndTypeCombo = new JComboBox();
				sndTypeCombo.setToolTipText("Playing plugin choice");
        sndTypeCombo.setFont(font);
				for (int i = 0; i < listePluginsAudio.size(); i++) {
						sndTypeCombo.addItem(listePluginsAudio.elementAt(i));
				}
        if (listePluginsAudio.size() != 0)
        sndTypeCombo.setSelectedIndex(0);
        sndTypeCombo.addItemListener(itm);*/


				Container c = getContentPane();
				c.setLayout(new BorderLayout());
				c.setFont(font);


				JPanel paneGestPlaylist = new JPanel();
				GridBagLayout gridbag5 = new GridBagLayout();
				GridBagConstraints gridbagC5 = new GridBagConstraints();				
				paneGestPlaylist.setLayout(gridbag5);
				gridbagC5.fill = GridBagConstraints.BOTH;
				gridbagC5.weightx = 1.0;
				gridbagC5.weighty = 1;
				gridbagC5.gridwidth = 1; 
				gridbag5.setConstraints(boutonAdd, gridbagC5); 
				paneGestPlaylist.add(boutonAdd);
				gridbagC5.gridwidth = GridBagConstraints.REMAINDER; 
				gridbag5.setConstraints(boutonDelete, gridbagC5); 
				paneGestPlaylist.add(boutonDelete);
				gridbagC5.gridwidth = 1; 
				gridbag5.setConstraints(boutonLoad, gridbagC5); 
				paneGestPlaylist.add(boutonLoad);
				gridbagC5.gridwidth = GridBagConstraints.REMAINDER; 
				gridbag5.setConstraints(boutonSave, gridbagC5); 
				paneGestPlaylist.add(boutonSave);


				JPanel panePlaylist = new JPanel();
				panePlaylist.setLayout(new BorderLayout());
				panePlaylist.add ("Center",scrollPane);
				JPanel paneUpDown = new JPanel();
				GridBagLayout gridbag2 = new GridBagLayout();
				GridBagConstraints gridbagC2 = new GridBagConstraints();				
				paneUpDown.setLayout(gridbag2);
				gridbagC2.fill = GridBagConstraints.BOTH;
				gridbagC2.gridwidth = GridBagConstraints.REMAINDER; 
				//gridbagC2.gridheight = GridBagConstraints.REMAINDER; 
				gridbagC2.weighty = 1;
				gridbag2.setConstraints(boutonUp, gridbagC2); 
				paneUpDown.add(boutonUp);
				gridbag2.setConstraints(boutonDown, gridbagC2); 
				paneUpDown.add(boutonDown);
				panePlaylist.add ("East",paneUpDown);



				JPanel paneControl = new JPanel();
				GridBagLayout gridbag3  = new GridBagLayout();
				GridBagConstraints gridbagC3 = new GridBagConstraints();				
				paneControl.setLayout(gridbag3);
				gridbagC3.fill = GridBagConstraints.BOTH;
				gridbagC3.weightx = 1.0;
				gridbagC3.gridwidth = 1; 
				gridbag3.setConstraints(boutonPrev, gridbagC3); 
				paneControl.add(boutonPrev);
				gridbag3.setConstraints(boutonPlay, gridbagC3); 
				paneControl.add(boutonPlay);
				gridbag3.setConstraints(boutonStop, gridbagC3); 
				paneControl.add(boutonStop);
				gridbagC3.gridwidth = GridBagConstraints.REMAINDER; 
				gridbag3.setConstraints(boutonNext, gridbagC3); 
				paneControl.add(boutonNext);



				JPanel paneBas = new JPanel();
				GridBagLayout gridbag4 = new GridBagLayout();
				GridBagConstraints gridbagC4 = new GridBagConstraints();				
				paneBas.setLayout(gridbag4);
				gridbagC4.fill = GridBagConstraints.BOTH;
				gridbagC4.gridwidth = GridBagConstraints.REMAINDER; 
				gridbagC4.weightx = 1.0;
				gridbagC4.weighty = 1;
				gridbag4.setConstraints(paneGestPlaylist, gridbagC4); 
				paneBas.add(paneGestPlaylist);
				//gridbag4.setConstraints(sndTypeCombo, gridbagC4); 
				//paneBas.add(sndTypeCombo);
				gridbag4.setConstraints(boutonNouv, gridbagC4); 
				paneBas.add(boutonNouv);
				gridbag4.setConstraints(boutonQuit, gridbagC4); 
				paneBas.add(boutonQuit);

				//c.add("North",paneControl);
				c.add("North",paneControl );
 				c.add("Center",panePlaylist);
				c.add("South",paneBas);

				actualiseTitreChanson();
				this.pack();
 				this.setVisible(true);

		}
		

		/** associe le vecteur 'vect' comme etant la nouvelle playlist a afficher
		 */
		public void changePlaylist(Vector vect){
				playlist.setListData(vect);
		}


		/** rend l'index du fichier selectionne dans la playlist
		 */
		public int getSelectedIndex() {
				return playlist.getSelectedIndex();
		}

		/**  selectionne un fichier dans la playlist positionne a l'index 'index'
		 */
		public void setSelectedIndex(int index) {
			  playlist.setSelectedIndex(index);
		}

		/** Point d'entree eventuel de la fenetre pour la creation de l'interface sans aucune gestion
		 */
		public static void main (String argv[]) {
        int WIDTH = 100;
        int HEIGHT = 400;
 
				Vector vector = new Vector();
				vector.add("Snd Test1");
				vector.add("Snd Test2");
				vector.add("Snd Test3");
				vector.add("Snd Test4");

				Vector vector2 = new Vector();
				vector2.add("Fich Test1");
				vector2.add("Fich  Test2");
				vector2.add("Fich  Test3");
				vector2.add("Fich  Test4");



				TestListener testListen = new TestListener();
				FenetrePlaylistKaraoke  fenetre = new FenetrePlaylistKaraoke (testListen, testListen, vector, WIDTH, HEIGHT);

				fenetre.changePlaylist(vector2);
				// positionne la fenetre au milieu de l'ecran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				fenetre.setLocation(screenSize.width/2 - WIDTH/2,
                          screenSize.height/2 - HEIGHT/2);
		}
		

}

	
	
