package EnglishPopKaraoke.GestionKaraoke;


/** interface plugin karaoke
 *  chaque plugin doit implementer cette interface pour etre repere comme tel
 */
public interface PluginKaraoke {
		public static final int  RIEN = 0;
		public static final int  NOUVELLE_LIGNE = 1;
		public static final int  NOUVEAU_PAR = 2;
								
		/** Permet de connaitre le type du plugin ("audio" ou "visuel")
		 */
		public String getType();
		
		/**  Permet de connaitre le nom du plugin
		 */
		public String getName();
}
