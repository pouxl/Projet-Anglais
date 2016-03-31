package EnglishPopKaraoke.GestionKaraoke;

/** Objet Information karaoke representant des informations a echanger entre les plugins audio et les plugins visuels
 */
public class InformationKaraoke {
		private int typeInformation;
		private Object information;
		
		//diverses constantes pour une utilisation plus explicite
		public static final int typeInfoNull = 0;
		public static final int typeInfoNomChanson = 1;
		public static final int typeInfoAuteurChanson = 2;
		public static final int typeInfoDureeChanson = 3;
		public static final int typeNouveauxText = 4;
		public static final int typeRepeteInfoNomChanson = 5;
		public static final int typeRepeteInfoAuteurChanson = 6;
		public static final int typeRepeteInfoDureeChanson = 7;
		public static final int typeRepeteNouveauxText = 8;
		public static final int typeTemps = 9;
		public static final int typePlay = 10;
		public static final int typeStop = 11;
		
		
		/** Constructeur par default de l'objet InformationKaraoke
		 */
		public InformationKaraoke() {
				typeInformation = 0;
				information = null;
		}
		
		/** Constructeur secondaire de l'objet InformationKaraoke
		 */
		public InformationKaraoke(int typeInfo, Object info) {
				typeInformation = typeInfo ;
				information = info;
		}
		
		
		/** Methode de modification de l'objet InformationKaraoke
		 *  (correspond aux diverses constantes de la classe)
		 */
		public void modifieInformationKaraoke(int typeInfo, Object info) {
				typeInformation = typeInfo;
				information = info;
		}
		
		/** Permet de connaitre la nature de l'information
		 */
		public int getTypeInfo() {
				return typeInformation;
		}
		
		/** Rend l'information elle meme
		 */
		public Object getObjectInfo() {
				return information;
		}
}
