package KaraokeTime.GraphiqueKaraoke;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/** Ecouteur minimal d'une interface graphique
 *  il permet de tester les differents boutons d'une interface
 */
class TestListener implements  ActionListener, ItemListener{
		
		/** analyse les actions 'bouton' executees par l'utilisateur sur l'interface
		 */
		public void actionPerformed(ActionEvent e) {
				String bouton = e.getActionCommand();
				System.out.println("Bouton " + bouton  + " appuye");
				if (bouton == "Quitter")
						System.exit(0);
				if (bouton == "Fermer")
						System.exit(0);
		}
		
		/** analyse les actions 'liste' executees par l'utilisateur sur l'interface
		 */
		public void itemStateChanged(ItemEvent e) {
				//c'est la que doit se faire le changement de plugin visuel
				if (e.getStateChange() == e.DESELECTED)
						System.out.println(e.getItem() + " Deselectionne");
				else
						System.out.println(e.getItem() + " Selectionne");
		}		
}
