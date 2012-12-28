package dfh.chelaxe.radio;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

public class RService extends Service 
{
	private MediaPlayer mPlayer;
	private PendingIntent pi;
	private BroadcastReceiver receiver;
	
    @Override 
    public IBinder onBind(Intent intent) 
	{ 
    	Log.d("RADIO", "ChelInfo: Bind Service");
    	
        return null; 
    } 

	@Override 
    public void onCreate() 
	{
		Log.d("RADIO", "ChelInfo: Create Service");
		
		super.onCreate();
		mPlayer = new MediaPlayer();
		
		receiver = new BroadcastReceiver() 
		{      
			@Override
			public void onReceive(Context context, Intent intent) 
			{
				if (intent != null && intent.getAction() != null)
				{	
				    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);				    
				    
				    if (TelephonyManager.EXTRA_STATE_RINGING.equals(state))
				    {
				    	Log.d("RADIO", "ChelInfo: Its Ringing");
				    	mPlayer.pause();
				    }
				    
				    if (TelephonyManager.EXTRA_STATE_IDLE.equals(state))
				    {
				        Log.d("RADIO", "ChelInfo: Its Idle");    
				        mPlayer.start();
				    }
				    
				    if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state))
				    {
				    	Log.d("RADIO", "ChelInfo: Its OffHook");
				    	mPlayer.pause();
				    }
				} 
			}
		};
		
		registerReceiver(receiver, new IntentFilter("android.intent.action.PHONE_STATE"));	
    } 
	
	@Override 
    public void onStart(final Intent intent, int startid) 
	{
		Log.d("RADIO", "ChelInfo: Start Service");
		
		pi = intent.getParcelableExtra("pendingIntent");    	
		
		if(intent.getStringExtra("Player").equalsIgnoreCase("pause")){
			mPlayer.pause();
    		try {
    			pi.send(300);
    		} catch (CanceledException e) {
    			Log.d("RADIO", "ChelError: " + e.getMessage());
    		}
		} else if(intent.getStringExtra("Player").equalsIgnoreCase("play")) 
		{
			mPlayer.start();
    		try {
    			pi.send(400);
    		} catch (CanceledException e) {
    			Log.d("RADIO", "ChelError: " + e.getMessage());
    		}
		} else
		{		
	        new Thread(new Runnable() 
	        {
	    		public void run() 
	    		{
	    			Looper.prepare();
	    			mPlayer.reset();    	
	            	try 
	            	{
	            		mPlayer.setDataSource(getApplicationContext(), Uri.parse(intent.getStringExtra("Player")));
	            		mPlayer.prepare();
					} catch (Exception e) 
					{
						Log.d("RADIO", "ChelError: " + e.getMessage());
					}
	            	mPlayer.start(); 
	            	
	        		try {
	        			pi.send(100);
	        		} catch (CanceledException e) {
	        			Log.d("RADIO", "ChelError: " + e.getMessage());
	        		}
	            }
	        }).start();		
		}
    } 
	
    @Override 
    public void onDestroy() 
	{
    	Log.d("RADIO", "ChelInfo: Destroy Service");
    	
    	try {
			pi.send(200);
		} catch (CanceledException e) {
			Log.d("RADIO", "ChelError: " + e.getMessage());
		}
    	unregisterReceiver(receiver);
    	mPlayer.stop();
    } 
}
