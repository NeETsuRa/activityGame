package si.delavnicaFNM.game;


import android.os.CountDownTimer;


public class Timer {
	Pricni pricni;
	private int casTime; //Èas ki ga nastavimo na zaèetku igre -> setTimer(int);
	private long timeLeft; //preostanek èasa - preostanek sekund, ki še jih ima igralecc na voljo
	private CasCount cas;
	public Timer (Pricni pricni, int casTime)
	{
		cas = new CasCount((casTime*1000), 1000);
		this.pricni = pricni;
	}
	
	public class CasCount extends CountDownTimer{
    	public CasCount(long millisInFuture, long countDownInterval) {
    	super(millisInFuture, countDownInterval);
    	}
    	@Override
    	public void onFinish() //Ko ès poteèe..
    	{ 
    		timeLeft = 0;	
    	}
    	@Override
    	public void onTick(long millisUntilFinished) { //Ko èas teèe..
    		timeLeft = millisUntilFinished/1000; //shranimo preostanek sekund
    		pricni.onTimerChanged(millisUntilFinished);
    	}
    }
	public void setTimer(int newTimer)
	{
		casTime = newTimer;
	}
    
	public int getTimer()
	{
		return casTime;
	}
	
	public void startTimer()
	{
		cas.start();
	}
    
	public void stopTimer()
	{
		//long preostanek = timeLeft;
		cas.cancel();
//		return preostanek;
	}
    public int resetTimer()
    {	
    	cas.cancel();
    	return casTime;
    }

}
