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



/** Plugin Stroboscope : Affiche la 'ligne de paroles' en cours avec une taille
 *  maximale dans la fenetre 
 */
public class Classic extends VisualPluginKaraoke {
		private MonPannel panPlug;


		/** Constructeur du plugin
		 */
		public Classic(){
				super();
		}


		/** est appelee lorsque un nouveau 'mot' doir etre affiche 
		 */
		public void actualise(int index){
				if (((Integer)textEvents.elementAt(index)).intValue() != VisualPluginKaraoke.RIEN){
						panPlug.changeChaine(trouveLigne(index));
				}
		}
		
		/**  Permet de connaitre le nom du plugin
		 */
		public String getName() {
				return "Stroboscope";
		}


		/** Chaque plugin visuel doit creer un JPanel
		 */
		public JPanel creeJPanel(){
				panPlug = new MonPannel();
				//JPanel panPlug = new JPanel();
				return panPlug;
		}
		

		/** classe creant une JPanel etant ImageObserver
		 */
		private class MonPannel extends JPanel implements ImageObserver{
				private final  Color bg = Color.white;
				private final  Color fg = Color.black;
				private final  Color red = Color.red;
				private final  Color white = Color.white;
				private final  int minFontSize = 4;
				private final  int maxFontSize = 80;
				
				
				private int longeur;
				private int largeur;
				private FontMetrics fontMetrics;
				private	String chaine = "Bienvenue dans Karaoke Time";
				
				private TextLayout zoneTexte;
				
				private Toolkit tk = getToolkit();
				private Image image = tk.getImage(MonPannel.class.getResource("per005.jpg"));
				

				/** Constructeur du JPanel
				 */
				public MonPannel(){
						setBackground(bg);
						setForeground(fg);
						longeur = 200;
						largeur = 200;
				}


				/** change la ligne a afficher
				 */
				public void changeChaine(String str){
						chaine = str;
						repaint();
				}
				
				

				/** calcule la police optimale pour afficher la ligne en cours
				 */
				public Font maxFont(Graphics2D g2,
										 String longString,
										 int xSpace) {
						boolean fontFits = false;
						Font font = g2.getFont();
						FontMetrics fontMetrics = g2.getFontMetrics();
						int size = font.getSize();
						String name = font.getName();
						int style = font.getStyle();
						
						while ( !fontFits ) {
								if (fontMetrics.stringWidth(longString) >= xSpace) {
										fontFits = true;
								}
								else {
										if ( size >= maxFontSize ) {
												fontFits = true;
										}
										else {
												g2.setFont(font = new Font(name, style, ++size));
												fontMetrics = g2.getFontMetrics();
										}
								}
						}
						
						return font;
				}
				
				
				/** methode paint affichant le JPanel
				 */
				public void paint(Graphics g) {
						Graphics2D g2 = (Graphics2D) g;
						
						Dimension d = getSize();
						int maxWidth = d.width;
						int maxHeight = d.height;
						
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						
						FontRenderContext frc = g2.getFontRenderContext();
						g2.drawImage(image, 0, 0, maxWidth,maxHeight, this);
						Font f = maxFont(g2,chaine,maxWidth- 50);
						
						zoneTexte = new TextLayout(chaine, f, frc);
						float sw = (float) zoneTexte.getBounds().getWidth();
						float sh = (float) zoneTexte.getBounds().getHeight();
						Shape sha = zoneTexte.getOutline(AffineTransform.getTranslateInstance(maxWidth/2-sw/2,maxHeight*0.5+sh/2));
						g2.setColor(Color.red);
						g2.draw(sha);
						g2.setColor(Color.blue);
						g2.fill(sha);
							
						Color fg3D = Color.red;
						g2.setPaint(fg3D);
						g2.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
						g2.draw3DRect(3, 3, d.width - 7, d.height - 7, false);
						g2.setPaint(fg);
				}


				/** methode repaint reaffichant le JPanel
				 */
				public void repaint(Graphics g) {
						paint(g);
				}
				
		}
}
