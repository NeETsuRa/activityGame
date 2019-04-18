package si.delavnicaFNM.game;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Pricni extends Activity {
	Igra igra;
	int treEkipa; //trenutna ekipa
	int treIgralec; 
	Kartica karta;
	Timer timer;
	ProgressBar bar;
	Button slika,uganil,niUganil;
	CountDownTimer timerX;
	TextView ime;
	Vector<TextView> tocke;
	Vector<Integer> vrstniRed;

	public Pricni() {
		tocke = new Vector<TextView>(6);
	}
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pricni);
	       
			Intent intent = getIntent();
			igra = (Igra)intent.getSerializableExtra("podatki.v.igro");
			
			this.karta = new Kartica(this);
			
			tocke.add((TextView) findViewById(R.id.ekipa1));
	        tocke.add((TextView) findViewById(R.id.ekipa2));
	        tocke.add((TextView) findViewById(R.id.ekipa3));
	        tocke.add((TextView) findViewById(R.id.ekipa4));
	        tocke.add((TextView) findViewById(R.id.ekipa5));
	        tocke.add((TextView) findViewById(R.id.ekipa6));
			
	        treEkipa=0;
	        vrstniRed=new Vector<Integer>(igra.vrniStEkip());
	        for(int i=0;i<igra.vrniStEkip();i++){
	        	vrstniRed.add(i);
	        	tocke.get(i).setVisibility(View.VISIBLE);
	        }
	       Collections.shuffle(vrstniRed,new Random());
	       treIgralec= igra.vrniEkipo(vrniIdTreEkipe()).vrniNaslednjegaIgralca();
			
	        
	        bar = (ProgressBar) findViewById(R.id.cas);
	        slika=(Button) findViewById(R.id.kartica);
	        slika.setBackgroundDrawable(getResources().getDrawable(R.drawable.hrbtna_stran_kartice));
	        uganil=(Button) findViewById(R.id.uganil);
	        niUganil=(Button) findViewById(R.id.niUganil);
	        ime=(TextView) findViewById(R.id.imeIgralca);
	        
	        
	        
	        uganil.getBackground().setColorFilter(new LightingColorFilter(0x00EE00, 0x000000));
	       
	        uganil.setEnabled(false);
	        niUganil.setEnabled(false);
	        izpisIgralca();
	        
	        // 
	
	 }
	
	public void klikKartica(View v){
		
		uganil.setEnabled(true);
		niUganil.setEnabled(true);
		
		
		//slika.setBackgroundResource(R.drawable.sprednja_stran_kartice);
		slika.setBackgroundDrawable(getResources().getDrawable(R.drawable.sprednja_stran_kartice));
		slika.setText(karta.vrniBesedo());
		slika.setClickable(false);

		
		
		
		timerX =  new CountDownTimer(igra.vrniDolzinoRunde()*1000, 10) {
	           	
	           	@Override
	   			public void onTick(long millisUntilFinished) {
	   				 bar.setProgress((int) millisUntilFinished/(igra.vrniDolzinoRunde()*10) );
	   			}
	   			
	   			@Override
	   			public void onFinish() {
	   				ponastaviKarto();
	   			}
	   		}.start();
	   	bar.setVisibility(View.VISIBLE);
	}
	
	public void ponastaviKarto(){
		slika.setBackgroundDrawable(getResources().getDrawable(R.drawable.hrbtna_stran_kartice));
		slika.setText("");
		slika.setClickable(true);
		bar.setVisibility(View.INVISIBLE);
		bar.setProgress(100);
		 
		timerX.cancel();
		
		niUganil.setEnabled(false);
		uganil.setEnabled(false);
		
		treEkipa=(treEkipa+1)%igra.vrniStEkip();
		treIgralec=igra.vrniEkipo(vrniIdTreEkipe()).vrniNaslednjegaIgralca();
		izpisIgralca();
		
		
		
		
	}
	
	public void klikUganil(View v){
		int rez=5;
		rez+=3*((double)bar.getProgress()/(double)bar.getMax());
		
		igra.vrniEkipo(vrniIdTreEkipe()).povecajTocke(rez);
		igra.vrniEkipo(vrniIdTreEkipe()).vrniIgralca(treIgralec).povecajStTock(rez);
		tocke.get(vrniIdTreEkipe()).setText(""+igra.vrniEkipo(vrniIdTreEkipe()).vrniTocke());
		
		ponastaviKarto();
	}
	public void klikNiUganil(View v){
		ponastaviKarto();
	}

	public void izpisIgralca(){
		ime.setText(igra.vrniEkipo(vrniIdTreEkipe()).vrniIme()+" : "+igra.vrniEkipo(vrniIdTreEkipe()).vrniIgralca(treIgralec).vrniIme());
		
	}
	 public void onTimerChanged(long millisUntilFinished)
	 {
		 ProgressBar bar = (ProgressBar) findViewById(R.id.cas);
		 bar.setMax(10000);
		 bar.setProgress((int) millisUntilFinished);
		 
	 }
	 public int vrniIdTreEkipe(){
		 return vrstniRed.get(treEkipa);
	 }
	
	
	
		
}
