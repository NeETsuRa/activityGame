package si.delavnicaFNM.game;

import java.util.Vector;

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Tocke extends Dialog implements OnClickListener{

	public View next;
	public View restart;
	public View exit;
	public TextView seznam;
	private Igra igra;
	
	public Tocke(Context context) {
		super(context);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tocke);
        next = findViewById(R.id.naprejIzpisTock);
        next.setOnClickListener(this);
    }

	public void onClick(View v) {
		if (v.getId()==R.id.naprejIzpisTock) {
			this.dismiss();
		}
	}
	
	/*
	public void izpisTock() {
				TextView izpisText = (TextView) findViewById(R.id.izpis);
//				for (int i=0; i<stEkip; i++){
//					izpisText.setText(ekipe.get(i).vrniIme() + ": " + ekipe.get(i).vrniTocke();
//				}
				izpisText.setText("BananaFighters" + ": " + "120");
				izpisText.setText("KiviShock" + ": " + "350");
				izpisText.setText("LovelyKittens" + ": " + "210");
				izpisText.setText("GreenPie" + ": " + "170");
				izpisText.setText("YoMamma" + ": " + "80");
		
	}
*/
	

}
