/**
 * 
 */
package si.delavnicaFNM.game;

import java.io.Serializable;

public class Igralec implements Serializable {
		private int id;
		private String ime;
		private int stAktivnosti;
		private int tocke;
		
		Igralec(int aID,String aIme){
			id=aID;
			ime=aIme;
			stAktivnosti=0;
			tocke=0;
		}
		void povecajStTock(int toc){
			tocke+=toc;
		}
		void povecajStAktivnosti(){
			stAktivnosti++;
		}
		String vrniIme(){
			return ime;
		}
		int vrniID(){
			return id;
		}
		int vrniStAktivnosti(){
			return stAktivnosti;
		}
}
