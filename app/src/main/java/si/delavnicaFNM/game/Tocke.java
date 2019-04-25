package si.delavnicaFNM.game;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
}
