package si.delavnicaFNM.game;

import java.util.Vector;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.TextSize;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class GameDataInputTeams extends Activity implements OnClickListener{
	static final int DI_NO_TEAMS = 0;
	static final int DI_TEAMS_DATA = 1; // to bo za branje podatkov
	static final int DI_SETTINGS = 2;
	
	static final int ID_TEAMNAME = 0x2000000;
	static final int ID_PLAYERNAMES = 0x22000000;
	
	static final int ID_NAMES_LAYOUT = 0x22200000;
	static final int ID_SETTINGS_LAYOUT = ID_NAMES_LAYOUT+1;
	
	static final int ID_SPINNER_LENGTH_GAME = 0x22220000;
	static final int ID_SPINNER_LENGTH_ROUND = ID_SPINNER_LENGTH_GAME+1;
	static final int ID_SPINNER_DIFFICULTY = ID_SPINNER_LENGTH_ROUND+1;
	
	static final int ID_NEXT_BTN = 0x10000000;
	static final int ID_ADD_PLAYER_BTN = ID_NEXT_BTN + 1;
	static final int ID_SETTINGS_BTN = ID_ADD_PLAYER_BTN+1;
	static final int ID_START_BTN = ID_SETTINGS_BTN+1;
	
	
	private int noOfTeams;
	private int state;
	
	private int currentTeamInput;
	private int noOfCurrTeamPlayers;
	
	Vector<Ekipa> ekipe = new Vector<Ekipa>();
	Igra igra;
	
	public GameDataInputTeams() {
		super();
		noOfTeams = 2;
		currentTeamInput=1;
		noOfCurrTeamPlayers=2;
		state = DI_NO_TEAMS;
		igra = null;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_data_input_teams);

        View btn2 = findViewById(R.id.btnTeamNo2);
        btn2.setOnClickListener(this);
        View btn3 = findViewById(R.id.btnTeamNo3);
        btn3.setOnClickListener(this);
        View btn4 = findViewById(R.id.btnTeamNo4);
        btn4.setOnClickListener(this);
        View btn5 = findViewById(R.id.btnTeamNo5);
        btn5.setOnClickListener(this);
        View btn6 = findViewById(R.id.btnTeamNo6);
        btn6.setOnClickListener(this);
    }
    
	public void onClick(View v) {
		boolean stateChanged = false;
		switch (v.getId()) {
		case R.id.btnTeamNo2:
			noOfTeams = 2;
			state = DI_TEAMS_DATA;
			stateChanged = true;
			break;
		case R.id.btnTeamNo3:
			noOfTeams = 3;
			state = DI_TEAMS_DATA;
			stateChanged = true;
			break;
		case R.id.btnTeamNo4:
			noOfTeams = 4;
			state = DI_TEAMS_DATA;
			stateChanged = true;
			break;
		case R.id.btnTeamNo5:
			noOfTeams = 5;
			state = DI_TEAMS_DATA;
			stateChanged = true;
			break;
		case R.id.btnTeamNo6:
			noOfTeams = 6;
			state = DI_TEAMS_DATA;
			stateChanged = true;
			break;
		case ID_ADD_PLAYER_BTN:
			LinearLayout temp = (LinearLayout)findViewById(ID_NAMES_LAYOUT);
			TextView tv = new TextView(this);
			noOfCurrTeamPlayers++;
			tv.setText("Igralec "+noOfCurrTeamPlayers+": ");
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			temp.addView(tv,  7+2*(noOfCurrTeamPlayers-3)); // indeks, kam dodamo!
			EditText et = new EditText(this);
			et.setText("Igralec "+noOfCurrTeamPlayers);
			et.setId(ID_PLAYERNAMES+currentTeamInput*4+noOfCurrTeamPlayers);
			temp.addView(et,  8+2*(noOfCurrTeamPlayers-3)); // indeks, kam dodamo
			
			if (Ekipa.MAX_NO_PLAYERS <= noOfCurrTeamPlayers)
				((Button)findViewById(ID_ADD_PLAYER_BTN)).setEnabled(false); // disable gumb za dodaj
			break;
		case ID_NEXT_BTN:
		case ID_SETTINGS_BTN:
			if(igra==null){
				Intent intent = getIntent();
				igra = (Igra)intent.getSerializableExtra("si.delavnicaFNM.game.Igra");
			}

			EditText et1;
			
			et1 = (EditText)findViewById(ID_TEAMNAME+currentTeamInput);
			String imeEkipe = et1.getText().toString();
			
			Vector<String> imenaigralcev = new Vector<String>();
			
			for(int i = 1; i<=noOfCurrTeamPlayers;i++){
				et1 = (EditText)findViewById(ID_PLAYERNAMES+currentTeamInput*4+i);
				imenaigralcev.add(et1.getText().toString());
			}

			igra.dodajEkipo(new Ekipa(currentTeamInput, imeEkipe, imenaigralcev.size(),
					imenaigralcev));
			if(v.getId()==ID_NEXT_BTN){
				stateChanged = true;
				state = DI_TEAMS_DATA;
				currentTeamInput++;
				noOfCurrTeamPlayers = 2;
			}else{
				stateChanged = true;
				state = DI_SETTINGS;
			}
			break;
		case ID_START_BTN:
			Spinner spinner = (Spinner)findViewById(ID_SPINNER_LENGTH_GAME);
			int select = spinner.getSelectedItemPosition();
			igra.dolociDolzinoIgre(getGameLength(select));
			
			spinner = (Spinner)findViewById(ID_SPINNER_LENGTH_ROUND);
			select = spinner.getSelectedItemPosition();
			igra.dolociDolzinoRunde(getRoundLength(select));
			
			spinner = (Spinner)findViewById(ID_SPINNER_DIFFICULTY);
			select = spinner.getSelectedItemPosition();
			igra.dolociTezavnost(getGameDifficulty(select));
			
			Intent resultIntent = new Intent();
			resultIntent.putExtra("test", igra);
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
		if (stateChanged) {
			switch (state) {
			case DI_NO_TEAMS:
				setContentView(R.layout.game_data_input_teams);
				break;
			case DI_TEAMS_DATA:
				teamDataInputLayout(currentTeamInput);
				break;
			case DI_SETTINGS:
				settingsDataInputLayout();
				break;
			}
		}
	}
	
	private int getGameDifficulty(int select) {
		int r=-1;
		switch(select){
			case 0:
				r = 1;
				break;
			case 1:
				r = 2;
				break;
			case 2:
				r = 3;
				break;
		}
		return r;
	}

	private int getRoundLength(int select) {
		int r=-1;
		switch(select){
			case 0:
				r = 30;
				break;
			case 1:
				r = 40;
				break;
			case 2:
				r = 50;
				break;
		}
		return r;
	}

	private int getGameLength(int select) {
		int r=-1;
		switch(select){
			case 0:
				r = 10;
				break;
			case 1:
				r = 20;
				break;
			case 2:
				r = 30;
				break;
		}
		return r;
	}

	private void teamDataInputLayout(int teamNo) {
		ScrollView sv = new ScrollView(this);
		sv.setBackgroundColor(GameProjectActivity.BG_COLOR);
		LinearLayout layout = new LinearLayout(this);
		layout.setId(ID_NAMES_LAYOUT);
		layout.setOrientation(LinearLayout.VERTICAL);
		sv.addView(layout);
		TextView tv = new TextView(this);
		tv.setText("Ekipa "+teamNo);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setTextSize(30);
		layout.addView(tv);
		tv = new TextView(this);
		tv.setText("Vnesi ime ekipe: ");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.addView(tv);
		EditText et = new EditText(this);
		et.setText("Ekipa "+teamNo);
		et.setId(ID_TEAMNAME + teamNo);
		layout.addView(et);
		tv = new TextView(this);
		tv.setText("Igralec 1: ");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.addView(tv);
		et = new EditText(this);
		et.setText("Igralec 1");
		et.setId(ID_PLAYERNAMES+teamNo*4+1);
		layout.addView(et);
		tv = new TextView(this);
		tv.setText("Igralec 2: ");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.addView(tv);
		et = new EditText(this);
		et.setText("Igralec 2");
		et.setId(ID_PLAYERNAMES+teamNo*4+2);
		layout.addView(et);
		
		LinearLayout vl = new LinearLayout(this);
		vl.setOrientation(LinearLayout.HORIZONTAL);
		Button b = new Button(this);
		b.setText("Dodaj igralca");
		b.setOnClickListener(this);
		b.setId(ID_ADD_PLAYER_BTN);
		vl.addView(b);
		if(teamNo != noOfTeams){
			b = new Button(this);
			b.setText("Naslednja ekipa");
			b.setOnClickListener(this);
			b.setId(ID_NEXT_BTN);
			vl.addView(b);
		}else{
			//vnesli smo zadnjo ekipo zato damo gumb za vnos nastavitev igre
			b = new Button(this);
			b.setText("Vnesi nastavitve igre");
			b.setOnClickListener(this);
			b.setId(ID_SETTINGS_BTN);
			vl.addView(b);
		}
		layout.addView(vl);
		this.setContentView(sv);
	}
	
	private void settingsDataInputLayout() {
		ScrollView sv = new ScrollView(this);
		sv.setBackgroundColor(GameProjectActivity.BG_COLOR);
		LinearLayout layout = new LinearLayout(this);
		layout.setId(ID_SETTINGS_LAYOUT);
		layout.setOrientation(LinearLayout.VERTICAL);
		sv.addView(layout);
		TextView tv = new TextView(this);
		tv.setText("Vnesi nastavitve igre");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setTextSize(30);
		layout.addView(tv);

		tv = new TextView(this);
		tv.setText("Izberi dolžino igre: ");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.addView(tv);
		Spinner spinner = new Spinner(this);
		spinner.setId(ID_SPINNER_LENGTH_GAME);
		String[] items = new String[] {"10","20","30"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
		layout.addView(spinner);
		
		tv = new TextView(this);
		tv.setText("Izberi dolžino runde: ");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.addView(tv);
		spinner = new Spinner(this);
		spinner.setId(ID_SPINNER_LENGTH_ROUND);
		items = new String[] {"30","40","50"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
		layout.addView(spinner);
		
		tv = new TextView(this);
		tv.setText("Izberi težavnost: ");
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.addView(tv);
		spinner = new Spinner(this);
		spinner.setId(ID_SPINNER_DIFFICULTY);
		items = new String[] {"lahko","srednje","težko"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
		layout.addView(spinner);
		
		LinearLayout vl = new LinearLayout(this);
		vl.setOrientation(LinearLayout.HORIZONTAL);
		Button b = new Button(this);
		b.setText("Prièni igro");
		b.setOnClickListener(this);
		b.setId(ID_START_BTN);
		vl.addView(b);
		layout.addView(vl);
		this.setContentView(sv);
	}
}
