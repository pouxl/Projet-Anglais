package EnglishPopKaraoke;

/** Objet Evenement karaoke representant des informations a echanger
 *  entre les differentes fenetres graphiques karaoke et leurs classes 
 *  de gestion correspondantes
 *  les fenetres graphiques observent ainsi un objet de type EvenementKaraoke
 */
public class EvenementKaraoke {
		private int typeEvenement;
		private Object evenement;
		
		//diverses constantes pour une utilisation plus explicite
		public static final int NouveauTitre = 0;
		public static final int NouvelObservable = 1;
		public static final int RepeteObservable = 2;
		public static final int NouveauJPanel = 3;
		public static final int RepeteTitre = 4;
		
		
		/** Constructeur de l'objet EvenementKaraoke
		 */
		public EvenementKaraoke() {
				typeEvenement = -1;
				evenement = null;
		}		
		
		/** Methode de modification de l'objet EvenementKaraoke
		 */
		public void modifieEvenementKaraoke(int t, Object i) {
				typeEvenement = t ;
				evenement = i;
		}
		
		/** Permet de connaitre la nature de l'information envoyee : 
		 *  NouveauTitre, NouvelObservable, RepeteObservable, NouveauJPanel, RepeteTitre
		 */
		public int getType() {
				return typeEvenement;
		}
		
		/** Rend l'information elle meme
		 */
		public Object getObject() {
				return evenement;
		}
}
