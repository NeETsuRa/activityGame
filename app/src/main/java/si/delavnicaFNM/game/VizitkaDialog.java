package si.delavnicaFNM.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class VizitkaDialog extends Dialog implements OnClickListener {
	public VizitkaDialog(Context context) {
		super(context);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        View v = findViewById(R.id.btnZapriVizitko);
        v.setOnClickListener(this);
    }

	public void onClick(View v) {
		if (v.getId()==R.id.btnZapriVizitko) {
			this.dismiss();
		}
		
	}
}
