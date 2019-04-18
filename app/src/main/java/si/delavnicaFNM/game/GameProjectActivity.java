package si.delavnicaFNM.game;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameProjectActivity extends Activity implements OnClickListener{
	static final int BG_COLOR = Color.rgb(0x59, 0x6f, 0x87);
	
	public Igra igra;
	final static int rezultatActivity = 103;
	final static int idPricni = 104;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //ustvarimo click listener
        View aboutBtn = findViewById(R.id.about_button);
        aboutBtn.setOnClickListener(this);
        aboutBtn.getBackground().setColorFilter(new LightingColorFilter(0x1464F4, 0x000000));
        View newGameBtn = findViewById(R.id.new_game_button);
        newGameBtn.setOnClickListener(this);
        newGameBtn.getBackground().setColorFilter(new LightingColorFilter(0x1464F4, 0x000000));
        View newSettingsBtn = findViewById(R.id.settings_button);
        newSettingsBtn.setOnClickListener(this);
        newSettingsBtn.getBackground().setColorFilter(new LightingColorFilter(0x1464F4, 0x000000));
        View newTocke = findViewById(R.id.izpisTock);
        newTocke.setOnClickListener(this);
        newTocke.getBackground().setColorFilter(new LightingColorFilter(0x1464F4, 0x000000));
    }
    
    @Override 
    public void onActivityResult(int requestCode, int resultCode, Intent data) {     
      super.onActivityResult(requestCode, resultCode, data); 
      switch(requestCode) { 
        case (rezultatActivity) : { 
          if (resultCode == Activity.RESULT_OK) { 
        	  igra = (Igra)data.getSerializableExtra("test");
        	  for(int i = 0; i<igra.vrniEkipe().size();i++)
        		  igra.vrniEkipe().get(i).dolociBarvo();
        	  /*
        	  	tukaj pride koda za pricni
              */
        	  
  			Intent i2 = new Intent(this, Pricni.class);
			i2.putExtra("podatki.v.igro",igra);
			//startActivity(i2);
			startActivityForResult(i2, idPricni);
          } 
          break; 
        } 
      } 
    }


	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.about_button:
//			Intent i1= new Intent(this,VizitkaActivity.class);
//			startActivity(i1);
			VizitkaDialog dlg = new VizitkaDialog(this);
			dlg.show();
			break;
		case R.id.new_game_button:
			igra = new Igra();
			Intent i2 = new Intent(this, GameDataInputTeams.class);
			i2.putExtra("si.delavnicaFNM.game.Igra",igra);
			//startActivity(i2);
			startActivityForResult(i2, rezultatActivity);
			break;
		case R.id.settings_button:
			Intent i3 = new Intent(this, Pricni.class);
			startActivity(i3);
			break;
		case R.id.izpisTock:
			Tocke toc = new Tocke(this);
			toc.show();
			break;
			
		}
		
		
	}
	/*
	public void izpisTock(View view)
	{
		Igra ig = new Igra();
		Tocke tocke = new Tocke(ig);
//		tocke.izpisTock();
		
	}
    */
}