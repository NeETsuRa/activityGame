package si.delavnicaFNM.game;

import android.os.CountDownTimer;

public class Timer {
	Pricni pricni;
	private int casTime;
	private long timeLeft;
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
    	public void onFinish()
    	{ 
    		timeLeft = 0;	
    	}
    	@Override
    	public void onTick(long millisUntilFinished) {
    		timeLeft = millisUntilFinished/1000;
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
		cas.cancel();
	}
    public int resetTimer()
    {	
    	cas.cancel();
    	return casTime;
    }
}
