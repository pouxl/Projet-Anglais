package KaraokeTime.GestionKaraoke.VisualPlugins;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.*;

import KaraokeTime.GestionKaraoke.VisualPluginKaraoke;



/** Plugin Karaoke Liste : Affiche la 'ligne de paroles' en cours et affiche plus precisement
 *  la syllabe prononcee en cours ainsi que le moment ou elle apparait
 */
public class TimeList extends VisualPluginKaraoke{
		private JList lstParoles = new JList(new DefaultListModel());
		private JList lstTemps = new JList(new DefaultListModel());
		private JLabel phrase = new JLabel(" ");
		private JScrollPane scrollPanelstParoles = new JScrollPane(lstParoles);
		private JScrollPane scrollPanelstTemps = new JScrollPane(lstTemps);


		/** Constructeur du plugin
		 */
		public TimeList() {
				super();
		}
						
		/**  Permet de connaitre le nom du plugin
		 */
		public String getName() {
				return "Karaoke Liste";
		}
		
		/** est appelee lorsque un nouveau 'mot' doir etre affiche 
		 */
		public void actualise(int index){
				if (((Integer)textEvents.elementAt(index)).intValue() != VisualPluginKaraoke.RIEN){
						phrase.setText(trouveLigne(index));
				}
				lstTemps.ensureIndexIsVisible(index); 
				lstParoles.ensureIndexIsVisible(index); 
				lstTemps.setSelectedIndex(index);
				lstParoles.setSelectedIndex(index); 
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
		

		/** est appelee lorsque un changement de paroles a lieu
		 */
		public void changeParoles(Vector t, Vector txe, Vector tme){
				super.changeParoles(t, txe, tme);
				if (timeEvents.size() != 0){
						lstParoles.setListData(text);
						lstTemps.setListData(creeTempAffichable());
						phrase.setText(trouveLigne(0));
				}
		}

		
		/** cree le vecteur representant le moment ou les syllabes doivent etres affichees
		 */
		public Vector creeTempAffichable(){
			  Vector tempAffichable = new Vector();
				for (int i = 0; i < timeEvents.size(); i++) {
						tempAffichable.add(transformeTemps(((Long)timeEvents.elementAt(i)).longValue()));
				}
				return tempAffichable;
		}
		
		
		/** Chaque plugin visuel doit creer un JPanel
		 */
		public JPanel creeJPanel(){
				JPanel panPlug = new JPanel();
				panPlug.setLayout(new BorderLayout());

				//panPlug.setBackground(Color.white);
				phrase.setHorizontalAlignment(JLabel.CENTER);
				lstTemps.setBackground(Color.white);
				lstParoles.setBackground(Color.white);

				Border border = new EtchedBorder(EtchedBorder.LOWERED) ;
				Border margin = new EmptyBorder(10,10,10,10);
				phrase.setBorder(new CompoundBorder(margin, border));


				JPanel panePlaylist = new JPanel();
				GridBagLayout gridbag = new GridBagLayout();
				GridBagConstraints gridbagC = new GridBagConstraints();				
				panePlaylist.setLayout(gridbag);
				gridbagC.fill = GridBagConstraints.BOTH;
				gridbagC.weightx = 1.0;
				gridbagC.weighty = 1;
				
				gridbagC.insets = new Insets(10, 10, 10, 10); 
				gridbag.setConstraints(scrollPanelstTemps, gridbagC); 
				panePlaylist.add(scrollPanelstTemps);
				gridbagC.gridwidth = GridBagConstraints.REMAINDER; 
				gridbag.setConstraints(scrollPanelstParoles, gridbagC); 
				panePlaylist.add(scrollPanelstParoles);



				//textbox.setBackground(Color.white);
				panPlug.add("North",phrase);
				panPlug.add("Center", panePlaylist);
				return panPlug;
		}
}
