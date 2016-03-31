package EnglishPopKaraoke.GestionKaraoke.VisualPlugins;

import javax.swing.*;

import EnglishPopKaraoke.GestionKaraoke.VisualPluginKaraoke;

import java.awt.*;
import java.util.*;



/** Plugin Ascii : Affiche les paroles, le titre et l'auteur de la chanson en cours
 */
public class Ascii extends VisualPluginKaraoke{
		TextArea textbox = new TextArea();

				
		/** Constructeur du plugin
		 */
		public Ascii () {
				super();
				textbox.setEditable(false);
		}
						
		/**  Permet de connaitre le nom du plugin
		 */
		public String getName() {
				return "Lyrics";
		}

		/**  Affiche paroles, auteur, nom dans la zone texte
		 */
		private void afficheText(){
				String resText = "";
				char[] c = {'\n'};
				String nl = new String(c);
				if (nomChanson != null){
						resText = resText.concat("TITLE : " + nomChanson);
						resText = resText.concat(nl);
				}
				if (auteurChanson != null){
						resText = resText.concat("AUTHOR : " + auteurChanson);
						resText = resText.concat(nl);
				}
				if ((textEvents != null) && (text != null)){
						for (int i = 0; i < textEvents.size(); i++) {
								switch (((Integer)textEvents.elementAt(i)).intValue()) {
								case VisualPluginKaraoke.RIEN : 
										resText = resText.concat((String)(text.elementAt(i)));
										break;
								case VisualPluginKaraoke.NOUVELLE_LIGNE : 
										resText = resText.concat(nl);
										resText = resText.concat((String)(text.elementAt(i)));
										break;
								case VisualPluginKaraoke.NOUVEAU_PAR : 
										resText = resText.concat(nl);
										resText = resText.concat(nl);
										resText = resText.concat((String)(text.elementAt(i)));
										break;
								}
						}
				}
				textbox.setText(resText);
		}
		


		/** est appelee lorsque un nouveau 'mot' doir etre affiche 
		 */
		public void actualise(int index){
				//ne fait rien de special
		}
		

		/** est appelee lorsque un changement de nom de chanson a lieu
		 */
		public void changeNom(String nm){
				super.changeNom(nm);
				afficheText();
		}



		/** est appelee lorsque un changement d'auteur a lieu
		 */
		public void changeAuteur(String au){
				super.changeAuteur(au);
				afficheText();
		}
		


		/** est appelee lorsque un changement de paroles a lieu
		 */
		public void changeParoles(Vector t, Vector txe, Vector tme){
				super.changeParoles(t, txe, tme);
				afficheText();
		}
		
		/** Chaque plugin visuel doit creer un JPanel
		 */
		public JPanel creeJPanel(){
				JPanel panPlug = new JPanel();
	
				panPlug.setLayout(new BorderLayout());
				textbox.setBackground(Color.white);
				panPlug.add("Center",textbox);
				panPlug.setBackground(Color.white);
				return panPlug;
		}
}
