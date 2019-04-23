package si.delavnicaFNM.game;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Random;

public class Kartica {
	public static final String KEY_ID = "id";
	public static final String KEY_AKTIVNOST = "aktivnost";
	public static final String KEY_ZAHTEVNOST = "zahtevnost";
	public static final String KEY_POJEM = "pojem";
	public static final int BAZA_VERZIJA = 1;
	public static final int PANTOMIMA = 0;
	public static final int GOVOR = 1;
	public static final int RISANJE = 2;
	public static final int LAHKO = 0;
	public static final int SREDNJE = 1;
	public static final int TEZKO = 2;
	private static final String DATABASE_NAME = "kartice";
	private static final String DATABASE_TABLE = "kartica";
	public static final String USTVARI = "create table kartica (id integer primary key autoincrement,"
			+ "aktivnost integer not null, zahtevnost integer not null, pojem text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private String pojem;
	private int aktivnost;
	private int zahtevnost;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, BAZA_VERZIJA);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(USTVARI);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS kartica");
			onCreate(db);
		}
	}

	public Kartica open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// ---insert a title into the database---
	public long insertTitle(int aktivnost, int zahtevnost, String pojem) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_AKTIVNOST, aktivnost);
		initialValues.put(KEY_ZAHTEVNOST, zahtevnost);
		initialValues.put(KEY_POJEM, pojem);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// ---deletes a particular title---
	public boolean deleteTitle(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
	}

	// ---retrieves all the titles---
	public Cursor getAllTitles() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_AKTIVNOST,
				KEY_ZAHTEVNOST, KEY_POJEM }, null, null, null, null, null);
	}

	// ---retrieves a particular title---
	public Cursor getTitle(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
				KEY_AKTIVNOST, KEY_ZAHTEVNOST, KEY_POJEM }, KEY_ID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ---updates a title---
	public boolean updateTitle(long rowId, int aktivnost, int zahtevnost,
			String pojem) {
		ContentValues args = new ContentValues();
		args.put(KEY_AKTIVNOST, aktivnost);
		args.put(KEY_ZAHTEVNOST, zahtevnost);
		args.put(KEY_POJEM, pojem);
		return db.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}

	public Kartica(Context context) {
		// super(context);
		this.context = context;
		DBHelper = new DatabaseHelper(context);
		long id;
		open();
		Cursor c = getAllTitles();
		if (c.getCount() <= 0) {
			id = this.insertTitle(PANTOMIMA, SREDNJE, "hiša");
			id = this.insertTitle(GOVOR, TEZKO, "govorjenje");
			id = this.insertTitle(RISANJE, TEZKO, "olje");
		} else {
			Log.v("aplikacija", "pise");
		}
		close();

		pojem = dobiBesedo();
	}

	public static String readRawTextFile(Context ctx, int resId) {
		InputStream inputStream = ctx.getResources().openRawResource(resId);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		int i;
		try {
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
		} catch (IOException e) {
			return null;
		}
		return byteArrayOutputStream.toString();
	}

	public String vrniBesedo() {
		return dobiBesedo();
	}

	private String dobiBesedo() {
		open();
		Cursor c = getAllTitles();
		int x = c.getCount();
		int y = new Random().nextInt(x);

		if (c.moveToPosition(y)) {
			aktivnost = c.getInt(1);
			zahtevnost = c.getInt(2);
			pojem = c.getString(3);
		}

		close();

		return pojem;
	}
}
