/**
 * 
 */
package si.delavnicaFNM.game;

import java.io.Serializable;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import android.graphics.Color;

/**
 * @author Tim
 *
 */
public class Ekipa implements Serializable{
	transient static final int MAX_NO_PLAYERS = 4;
	
	private int id;
	private String ime;
	private int stIgralcev=2;
	private Vector<Igralec> igralci=new Vector<Igralec>();
	transient private Color barva=new Color();
	private int tocke;
	private Vector<Integer> vrstniRed=new Vector<Integer>();
	private int treIgr;
	Ekipa(int aID,String aIme,int aStIg,Vector<String> aImena){
		id=aID;
		ime=aIme;
		stIgralcev=aStIg;
		tocke=0;
		treIgr=0;
		dolociIgralce(aImena);
		dolociBarvo();
		dolociVrstniRed();
	}
	
	private void dolociVrstniRed() {
		vrstniRed=new Vector<Integer>(igralci.size());
		for(int i=0;i<igralci.size();i++){
        	vrstniRed.add(i);
        }
       Collections.shuffle(vrstniRed,new Random());
		
	}

	private void dolociIgralce(Vector<String> aImena) {
		for(int i=0;i<stIgralcev;i++){
			igralci.add(new Igralec(i,aImena.get(i)));
		}
	}

	public void dolociBarvo(){
		barva = new Color();
		switch(id){
			case 0: barva.rgb(255, 0, 0); break;
			case 1: barva.rgb(0, 0, 255); break;
			case 2: barva.rgb(0, 255, 0); break;
			case 3: barva.rgb(255, 255, 0); break;
			case 4: barva.rgb(255, 0, 255); break;
			case 5: barva.rgb(0, 255, 255); break;
			default: barva.rgb(0, 0, 0);
		}
	}
	
	public int vrniID(){
		return id;
	}
	
	public String vrniIme(){
		return ime;
	}
	
	public int vrniStIgralcev(){
		return stIgralcev;
	}
	
	public Color vrniBarvo(){
		return barva;
	}
	
	public int vrniTocke(){
		return tocke;
	}
	
	public int povecajTocke(int aP){
		tocke+=aP;
		return tocke;
	}
	public Igralec vrniIgralca(int ID){
		return igralci.get(ID);
	}
	
	public int vrniNaslednjegaIgralca(){
		int next=vrstniRed.get(treIgr);
		treIgr=(treIgr+1)%vrstniRed.size();
		
		return next;
	}
}
