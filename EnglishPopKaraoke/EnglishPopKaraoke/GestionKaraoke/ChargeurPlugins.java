package EnglishPopKaraoke.GestionKaraoke;

import java.util.*;

import EnglishPopKaraoke.GestionKaraoke.PluginKaraoke;

import java.net.*;
import java.io.File;

/** Classe generique permettant de charger des plugins
 */
public class ChargeurPlugins {
		
		/** Permet de reperer des plugins de type 'type' presents dans un repertoire 'rep'
		 *  et de charger ainsi le nom des fichiers ainsi qua le nom des plugins eux memes
		 */
		static public void chargePlugins(String rep, String type, Vector pClass, Vector pName){
					URL url = ChargeurPlugins.class.getResource(rep);
				if (url == null){
			 		 System.out.println("Le repertoire de plugins specifie n'est pas correct");
				}
				else {
						File dir = new File(url.getFile());
						if (dir != null && dir.isDirectory()) {
								String list[] = dir.list();
								sort(list);
								for (int i = 0; i < list.length; i++) {
										if (list[i].indexOf('$') == -1 && list[i].endsWith(".class")) {
												String nomFich = "EnglishPopKaraoke.GestionKaraoke." + rep + "." + list[i].substring(0,list[i].indexOf('.'));
												try{
														Class c = Class.forName(nomFich);
														PluginKaraoke plug = null;

														try{
																plug = (PluginKaraoke)c.newInstance();
														}
														catch (ClassCastException e ){
																//Le fichier  n'est pas un plugin 'PluginKaraoke'
														}
														catch (InstantiationException e ){
																System.out.println("Le plugin " + nomFich + " ne peut pas etre instancie");
														}
														catch (IllegalAccessException e ){
																System.out.println("Le plugin " + nomFich + " est interdit d'acces");
														}
														if (plug.getType() == type) {
																pClass.add(c);
																pName.add(plug.getName());
														}
												}
												catch (NoClassDefFoundError e ){
														//Le fichier  n'est pas un plugin correct
												}														
												catch (ClassNotFoundException e){
														System.out.println("Le plugin " + nomFich + " est introuvable");
												}
										}
								}
								for (int i = 0; i < pName.size(); i++) {
										System.out.println (type + "Plugin " + (String) pName.elementAt(i) + " Trouve ");
								}
								if (pClass.size() == 0)
										System.out.println("Aucun Plugin trouve a l'adresse : " + url);
						} else {
								System.out.println("Le repertoire specifie n'est pas correct : " + url);
						}
				}
		}
		
		
		/**
		 * permet de trier un tableau de chaines
		 */ 
		private static void sort(String a[]) {
				for (int i = a.length; --i>=0; ) {
            boolean change = false;
            for (int j = 0; j<i; j++) {
                if (a[j].compareTo(a[j+1]) > 0) {
                    String T = a[j];
                    a[j] = a[j+1];
                    a[j+1] = T;
                    change = true;
                }
            }
            if (!change)
                return;
        }
    }
}
