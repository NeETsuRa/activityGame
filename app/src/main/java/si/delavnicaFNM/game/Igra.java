package si.delavnicaFNM.game;

import java.io.Serializable;
import java.util.Vector;

public class Igra implements Serializable {
	transient final static int STANJE_NOVAIGRA = 0;
	
	transient final static int MAX_TEAM_NO = 6;
	
	private int stEkip;
	private Vector<Ekipa> ekipe;
	private int tezavnost;
	private int dolzinaIgre;
	private int dolzinaRunde;
	transient private int stanje; //glede na stanje bodo razlicna okna
	
	public Igra(){
		this.stanje = STANJE_NOVAIGRA; // stanje za dolocitev ekip, tezavnosti, dolzine igre, ...
		ekipe = new Vector<Ekipa>();
		
	}
	
	public void dolociSteviloEkip(int stEkip){
		this.stEkip = stEkip;
	}
	
	public void dodajEkipo(Ekipa ekipa){
		ekipe.add(ekipa);
	}
	
	public void dolociTezavnost(int tezavnost){
		this.tezavnost = tezavnost;
	}
	
	public void dolociDolzinoIgre(int dolzinaIgre){
		this.dolzinaIgre = dolzinaIgre;
	}
	
	public void dolociDolzinoRunde(int dolzinaRunde){
		this.dolzinaRunde = dolzinaRunde;
	}
	public int vrniDolzinoRunde(){
		return dolzinaRunde;
	}
	public int vrniDolzinoIgre(){
		return dolzinaIgre;
	}
	public int vrniTezavnost(){
		return tezavnost;
	}
	
	
	public int vrniStEkip() {
		return ekipe.size();
	}
	public Vector<Ekipa> vrniEkipe() {
		return ekipe;
	}
	public Ekipa vrniEkipo(int ID) {
		return ekipe.get(ID);
	}
	
}

