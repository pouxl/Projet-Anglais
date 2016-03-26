package KaraokeTime.GestionKaraoke.VisualPlugins;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import com.sun.java.swing.*;
import javax.swing.JPanel;
import java.awt.image.ImageObserver;
import java.awt.Toolkit.*;
import java.awt.Rectangle;
import java.awt.font.*;
import java.text.*;

import KaraokeTime.GestionKaraoke.VisualPluginKaraoke;


/** Plugin Hilight : Affiche la 'ligne de paroles' en cours en affichant distinctivement la syllabe prononcee
 *  et affiche aussi la ligne suivante
 */
public class Hilight extends VisualPluginKaraoke {
		private MonPannel panPlug;
		private int initIndex;
		private boolean debutPlug = true;

		/** Constructeur du plugin
		 */
		public Hilight(){
				super();
				panPlug = new MonPannel(textEvents, timeEvents, text);
		}
		
		
		/**  Permet de connaitre le nom du plugin
		 */
		public String getName() {
				return "Visual karaoke";
		}


		/** est appelee lorsque un nouveau 'mot' doir etre affiche 
		 */
		public synchronized void actualise(int index){
				if (((Integer)textEvents.elementAt(index)).intValue() != VisualPluginKaraoke.RIEN){
						panPlug.changeChaine(trouveLigne(index),trouveProchaineLigne(index), index);
				}
				else
						if (debutPlug == true){
								int ind = index;
								while ( (ind >=0) && (((Integer)textEvents.elementAt(ind)).intValue() == VisualPluginKaraoke.RIEN)){
										ind--;
								}
						panPlug.changeChaine(trouveLigne(ind),trouveProchaineLigne(ind), ind);
						debutPlug = false;
						}
				panPlug.changeIndice(index);				
		}
		

		/** est appelee lorsque un changement de paroles a lieu
		 */
		public synchronized void changeParoles(Vector t, Vector txe, Vector tme){
				super.changeParoles(t, txe, tme);
				if ((text != null) && (text.size() != 0)){
						panPlug.actuText(textEvents, timeEvents, text);
						panPlug.changeChaine(trouveLigne(0),trouveProchaineLigne(0), 0);
						panPlug.afficheActu();
				}
		}


		/** Chaque plugin visuel doit creer un JPanel
		 */
		public JPanel creeJPanel(){
				//JPanel panPlug = new JPanel();
				return panPlug;
		}
		


		/** classe creant une JPanel etant ImageObserver
		 */
		private class MonPannel extends JPanel implements ImageObserver{
				private  final  Color bg = Color.white;
				private  final  Color fg = Color.black;
				private  final  Color red = Color.red;
				private  final  Color white = Color.white;

				private int oldWidth = 50;
				private int oldHeight = 50;
				private int hauteurTexte = 50;
				
				private Graphics2D graph = null;
				private Font font = new Font("serif", Font.PLAIN, 11);
				private FontMetrics fontMetrics;
				private	String chaine = ".";
				private	String nextChaine = ".";

				private	String chnGchaine;
				private	String chnGnextChaine;
				private int chnGinitIndex;

				private Vector textEvents;
				private Vector timeEvents;
				private Vector text;
				private int initIndex = 0;
				private int finIndex = 0;
				private int index = 0;
				private int tailleTotaleMots = 0;
				private Vector tailleMots = null;
				private Vector contourMots = null;
				
				private FontRenderContext frc = null;
				private TextLayout zoneTexte;

				private boolean changementDeChaine = false;
				private boolean afficheActuel = false;
				private boolean finRepaint = true;
				private Toolkit tk = getToolkit();
				private Image image = tk.getImage(MonPannel.class.getResource("logo2.jpg"));
				

				/** Constructeur du JPanel
				 */
				public MonPannel(Vector te ,Vector tm ,Vector t ){
						textEvents = te;
						timeEvents = tm;
						text = t;
						
						setBackground(bg);
						setForeground(fg);
				}


				/** Change les paroles de la chanson en cours
				 */
				public void actuText(Vector te ,Vector tm ,Vector t ){
						textEvents = te;
						timeEvents = tm;
						text = t;
						globalMaxFont();
				}
				


				/** calcule la police optimale pour afficher toutes les lignes de la chanson en cours
				 */
				public void globalMaxFont(){
						//System.out.println("Debut globalMaxFont");				
						boolean fontFits;
						FontMetrics fontMetrics = null;
						int size;
						String name;
						int style;
						
						String resText = "";
						Dimension d = getSize();
						int maxWidth = d.width;
						int maxHeight = d.height;

					
						if ((textEvents != null) && (text != null) && (graph != null)){
								font = graph.getFont();
								name = font.getName();
								style = font.getStyle();
												size = 60;
												graph.setFont(font = new Font(name, style, size));
												fontMetrics = graph.getFontMetrics();
									for (int i = 0; i < textEvents.size(); i++) {
										switch (((Integer)textEvents.elementAt(i)).intValue()) {
										case VisualPluginKaraoke.RIEN : 
												resText = resText.concat((String)(text.elementAt(i)));
												break;
										case VisualPluginKaraoke.NOUVELLE_LIGNE : 
										case VisualPluginKaraoke.NOUVEAU_PAR : 
												fontFits = false;
												while ( !fontFits ) {
														if (fontMetrics.stringWidth(resText)< (maxWidth- 50)) {
																fontFits = true;
														}
														else {
																graph.setFont(font = new Font(name, style, --size));
																fontMetrics = graph.getFontMetrics();
														}
												}
												resText ="";
												resText = resText.concat((String)(text.elementAt(i)));
												break;
										}
								}
						}
						if (fontMetrics != null)
								hauteurTexte = fontMetrics.getHeight() ;
				}

				/** change les deux lignes a afficher
				 */
				public void changeChaine(String str, String str2, int initInd){
						changementDeChaine = true;
						chnGchaine = str;
						chnGnextChaine = str2;
						chnGinitIndex = initInd;
				}
				
				/** change l'indice de la syllabe prononcee
				 */
				public void changeIndice( int ind){
						if (finRepaint == true){
								finRepaint = false;
								if (changementDeChaine == true){
										chaine = chnGchaine;
										nextChaine = chnGnextChaine;
										initIndex = chnGinitIndex;
										calculeTailles();
										changementDeChaine = false;
								}
								if ((initIndex <= ind) && (ind <= finIndex)){
										index = ind;
										repaint();
								}
								else {
										finRepaint = true;
								}
						}
				}	



				/** calcule les tailles de chaque lignes dans la police actuelle (optimale)
				 */
				public void calculeTailles(){
						if ((textEvents != null) && (text != null) && (text.size() != 0) &&  (graph != null)){

								FontMetrics fontMetrics = graph.getFontMetrics();
								frc = graph.getFontRenderContext();
								
								int i = initIndex + 1;
								float lengx = 0;
								tailleMots = new Vector();
								contourMots = new Vector();
								tailleMots.add(new Float(lengx));
								lengx = (float) fontMetrics.stringWidth((String)(text.elementAt(initIndex)));
								while ( ( i < textEvents.size()) && (((Integer)textEvents.elementAt(i)).intValue() == VisualPluginKaraoke.RIEN)){
										tailleMots.add(new Float(lengx));
										lengx += (float) fontMetrics.stringWidth((String)(text.elementAt(i)));
										i++;
								}
								finIndex = i-1;
								tailleTotaleMots = (int)lengx;
								
								float	sw = oldWidth/2-((float)tailleTotaleMots)/2;
								float	sh = (float)(oldHeight*0.5) - (hauteurTexte / 2);
								TextLayout textlay;
								Shape sha;
								
								int j = initIndex;
								for (i = 0; i< tailleMots.size(); i++){
										textlay = new TextLayout((String)(text.elementAt(j)), font, frc);
										sha = textlay.getOutline(AffineTransform.getTranslateInstance(sw + ((Float)tailleMots.elementAt(i)).floatValue() ,sh));
										contourMots.add(sha);
										j++;
								}
						}	
						
				}


				/** methode paint affichant le JPanel
				 */
				public void paint(Graphics g) {
						finRepaint = false;
						Graphics2D g2 = (Graphics2D) g;
						
						Dimension d = getSize();
						int maxWidth = d.width;
						int maxHeight = d.height;
						float sw;
						float sh;
						Shape sha;
						if ((graph == null) || (maxWidth != oldWidth) || (maxHeight != oldHeight)) {
								oldWidth = maxWidth;
								oldHeight = maxHeight;
								graph = g2;
								graph.setFont(font);
								globalMaxFont();
								calculeTailles();
						}

						g2.setFont(font);
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						frc = g2.getFontRenderContext();

						g2.drawImage(image, 0, 0, maxWidth,maxHeight, this);

						
						if ((text != null) && (text.size() != 0)  && (contourMots != null) && (contourMots.size() != 0)){
								if (afficheActuel == true){
										for (int i = initIndex; i<= finIndex ; i++){
												sha = (Shape)contourMots.elementAt(i - initIndex);
												g2.setColor(Color.black);
												g2.draw(sha);
												g2.setColor(Color.red);
												g2.fill(sha);
										}
										afficheActuel = false;
								}
								else{

										for (int i = initIndex; i< index ; i++){
												sha = (Shape)contourMots.elementAt(i - initIndex);
												g2.setColor(Color.black);
												g2.draw(sha);
												g2.setColor(Color.cyan);
												g2.fill(sha);
										}
										
										sha = (Shape)contourMots.elementAt(index - initIndex);
										g2.setColor(Color.black);
										g2.draw(sha);
										g2.setColor(Color.yellow);
										g2.fill(sha);
										
										for (int i = (index + 1); i<= finIndex ; i++){
												sha = (Shape)contourMots.elementAt(i - initIndex);
										g2.setColor(Color.black);
										g2.draw(sha);
										g2.setColor(Color.red);
										g2.fill(sha);
										}
										
								}
								
								zoneTexte = new TextLayout(nextChaine, font, frc);
								FontMetrics fontMetrics = graph.getFontMetrics();
								sw = oldWidth/2-((float)fontMetrics.stringWidth(nextChaine))/2;
								sh = (float)(oldHeight*0.5)  + (hauteurTexte / 2);
	
								sha = zoneTexte.getOutline(AffineTransform.getTranslateInstance(sw ,sh));
								g2.setColor(Color.black);
								g2.draw(sha);
								g2.setColor(Color.red);
								g2.fill(sha);
								
						}
						Color fg3D = Color.red;
						g2.setPaint(fg3D);
						g2.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
						g2.draw3DRect(3, 3, d.width - 7, d.height - 7, false);
						g2.setPaint(fg);
								
						finRepaint = true;	
				}



				/** affiche l'etat actuel des lignes
				 */
				public void afficheActu(){
						chaine = chnGchaine;
						nextChaine = chnGnextChaine;
						initIndex = chnGinitIndex;
						calculeTailles();
						afficheActuel = true;
						repaint();
				}


				/** methode repaint reaffichant le JPanel
				 */
				public void repaint(Graphics g) {
						paint(g);
				}
		}
}
