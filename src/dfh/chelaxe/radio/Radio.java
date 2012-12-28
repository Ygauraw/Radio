package dfh.chelaxe.radio;

import java.net.HttpURLConnection;
import java.net.URL;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Radio extends Activity 
{
	public URL url;
	public HttpURLConnection urlConnection;
	public LocationManager lm;
	public String URL;	
	
	private static final int NOTIFY_ID = 1;	
	private int flag = 0;
	private String CHANEL = "chanel";	
	private ProgressBar pBChanel;
	private ImageButton iBPlay;
	private String RUri;
	private PendingIntent pi;		
	private NotificationManager mNotificationManager;
	private SharedPreferences sPref;
	private Editor ed;
	private String NameChanel;	
	private Spinner spinner;	
	private Radio mapp;	
	private myAdapter mAdapter;
	private String notchanel = "PIANO";
	private Integer notimage = R.drawable.ic_launcher;
	private String notradio = "Евгений Елисеев";
	private String[] mSign = new String[] { 
			"PIANO", "OPERA", "JAZZ", 
    		"TRANCE", "FASHION", "MINIMAL", 
    		"RELAX", "HOUSE", "DNB", 
    		"DUB", "UnFormat" 
    		};	
	private String[] mDate = new String[] { 
			"Евгений Елисеев", "Мария Каллас", "Евгений Пряхин", 
    		"Никита Антоневич", "Евгений Елисеев", "Никита Антоневич", 
    		"Евгений Пряхин", "Никита Антоневич", "Extaziee", 
    		"Extaziee", "Серёга Суставов" 
    		};	
	private Integer[] mImage = { 
			R.drawable.piano, R.drawable.ic_launcher, R.drawable.jazz,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, 
			R.drawable.ic_launcher, R.drawable.ic_launcher
			};	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_radio, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	switch(item.getItemId())
    	{
    		case R.id.menu_task:    			
    			moveTaskToBack(true);
    			break;
    		case R.id.menu_exit:
    			stopService(new Intent(this, RService.class).putExtra("pendingIntent", pi));
    			break;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    	return true;
    }
    
    public void pClick(View vw)
    {
    	if(flag == 0)
    	{
    		pBChanel.setVisibility(0);    		
        	startService(new Intent(this, RService.class).putExtra("Player", "pause").putExtra("pendingIntent", pi));
    	} else
    	{    	
    		pBChanel.setVisibility(0);
        	startService(new Intent(this, RService.class).putExtra("Player", "play").putExtra("pendingIntent", pi));
    	}
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    super.onActivityResult(requestCode, resultCode, data);
	    if (resultCode == 100) 
	    {
	    	pBChanel.setVisibility(8); 
	    	iBPlay.setVisibility(0);
	    	flag = 0;
        	iBPlay.setImageDrawable(getResources().getDrawable(R.drawable.pause));
	    }
	    
	    if(resultCode == 200)
	    {   	
	    	mNotificationManager.cancel(NOTIFY_ID);
	    	System.exit(0);
	    }
	    
	    if(resultCode == 300)
	    {   	
	    	pBChanel.setVisibility(8); 
	    	flag = 1;
        	iBPlay.setImageDrawable(getResources().getDrawable(R.drawable.play));
	    }
	    
	    if(resultCode == 400)
	    {   	
	    	pBChanel.setVisibility(8); 
	    	flag = 0;
        	iBPlay.setImageDrawable(getResources().getDrawable(R.drawable.pause));
	    }
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	Log.d("RADIO", "ChelInfo: Create Activity");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);        
        pBChanel = (ProgressBar) findViewById(R.id.pBChanel);
        iBPlay = (ImageButton) findViewById(R.id.iBPlay);
             
        pi = createPendingResult(1, new Intent(), 0);         
        
        mAdapter = new myAdapter(this);
        spinner = (Spinner) findViewById(R.id.sPChanel);
        spinner.setAdapter(mAdapter);
        spinner.setPrompt("КАНАЛЫ");
        spinner.setSelection(0);
        
        try
        {
	        sPref = getPreferences(MODE_PRIVATE);
	        NameChanel = sPref.getString(CHANEL, "");
	        
	        if(NameChanel.equalsIgnoreCase("opera"))
	        {
	        	spinner.setSelection(1);
	        	notchanel = mSign[1];
	        	notradio = mDate[1];
	        	notimage = mImage[1];
	        } 
	        else if(NameChanel.equalsIgnoreCase("jazz"))
	        {
	        	spinner.setSelection(2);
	        	notchanel = mSign[2];
	        	notradio = mDate[2];
	        	notimage = mImage[2];
	        } 
	        else if(NameChanel.equalsIgnoreCase("trance"))
	        {
	        	spinner.setSelection(3);
	        	notchanel = mSign[3];
	        	notradio = mDate[3];
	        	notimage = mImage[3];
	        } 
	        else if(NameChanel.equalsIgnoreCase("fashion"))
	        {
	        	spinner.setSelection(4);
	        	notchanel = mSign[4];
	        	notradio = mDate[4];
	        	notimage = mImage[4];
	        } 
	        else if(NameChanel.equalsIgnoreCase("minimal"))
	        {
	        	spinner.setSelection(5);
	        	notchanel = mSign[5];
	        	notradio = mDate[5];
	        	notimage = mImage[5];
	        } 
	        else if(NameChanel.equalsIgnoreCase("relax"))
	        {
	        	spinner.setSelection(6);
	        	notchanel = mSign[6];
	        	notradio = mDate[6];
	        	notimage = mImage[6];
	        } 
	        else if(NameChanel.equalsIgnoreCase("house"))
	        {
	        	spinner.setSelection(7);
	        	notchanel = mSign[7];
	        	notradio = mDate[7];
	        	notimage = mImage[7];
	        } 
	        else if(NameChanel.equalsIgnoreCase("dnb"))
	        {
	        	spinner.setSelection(8);
	        	notchanel = mSign[8];
	        	notradio = mDate[8];
	        	notimage = mImage[8];
	        } 
	        else if(NameChanel.equalsIgnoreCase("dub"))
	        {
	        	spinner.setSelection(9);
	        	notchanel = mSign[9];
	        	notradio = mDate[9];
	        	notimage = mImage[9];
	        } 
	        else if(NameChanel.equalsIgnoreCase("unformat"))
	        {
	        	spinner.setSelection(10);
	        	notchanel = mSign[10];
	        	notradio = mDate[10];
	        	notimage = mImage[10];
	        } 
	        else
	        {
	        	spinner.setSelection(0);
	        	notchanel = mSign[0];
	        	notradio = mDate[0];
	        	notimage = mImage[0];
	        }
	    }
        catch(Exception e)
        {        	   
        	spinner.setSelection(0);
        }
        
        mapp = this;
        
        spinner.setOnItemSelectedListener(
        	new AdapterView.OnItemSelectedListener() 
        	{
				public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) 
				{
					notchanel = mSign[selectedItemPosition];
		        	notradio = mDate[selectedItemPosition];
		        	notimage = mImage[selectedItemPosition];
					
					switch(selectedItemPosition)
					{
						case 0:
							Toast.makeText(getApplicationContext(), "Канал Piano", Toast.LENGTH_SHORT).show(); 

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "piano");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/piano.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 1:
							Toast.makeText(getApplicationContext(), "Канал Opera", Toast.LENGTH_SHORT).show(); 

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "opera");
					        ed.commit();
					        
					    	RUri = "http://46.45.14.1:8000/opera.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 2:
					    	Toast.makeText(getApplicationContext(), "Канал Jazz", Toast.LENGTH_SHORT).show();

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "jazz");
					        ed.commit();
					        
					    	RUri = "http://46.45.14.1:8000/jazz.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 3:
							Toast.makeText(getApplicationContext(), "Канал Trance", Toast.LENGTH_SHORT).show();

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "trance");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/trance.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 4:
							Toast.makeText(getApplicationContext(), "Канал Fashion", Toast.LENGTH_SHORT).show();

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "fashion");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/fashion.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 5:
							Toast.makeText(getApplicationContext(), "Канал Minimal", Toast.LENGTH_SHORT).show();

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "minimal");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/minimal.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 6:
							Toast.makeText(getApplicationContext(), "Канал Relax", Toast.LENGTH_SHORT).show();

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "relax");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/relax.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 7:
							Toast.makeText(getApplicationContext(), "Канал House", Toast.LENGTH_SHORT).show();

					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "house");
					        ed.commit();
					        
					    	RUri = "http://46.45.14.1:8000/house.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 8:
							Toast.makeText(getApplicationContext(), "Канал DNB", Toast.LENGTH_SHORT).show();
					    	
					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "dnb");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/dnb.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 9:
							Toast.makeText(getApplicationContext(), "Канал DUB", Toast.LENGTH_SHORT).show();
					    							
					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "dub");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/dub.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
							
						case 10:
							Toast.makeText(getApplicationContext(), "Канал UnFormat", Toast.LENGTH_SHORT).show();
					    							
					    	sPref = getPreferences(MODE_PRIVATE);
					        ed = sPref.edit();
					        ed.putString(CHANEL, "unformat");
					        ed.commit();
					    	
					    	RUri = "http://46.45.14.1:8000/unformat.mp3";
					    	pBChanel.setVisibility(0);
					    	iBPlay.setVisibility(8);
					    	startService(new Intent(mapp, RService.class).putExtra("Player", RUri).putExtra("pendingIntent", pi));
							break;
					}
				}

				public void onNothingSelected(AdapterView<?> parent) 
				{
					
				}
			}
		);
    }
    
    @Override
    protected void onPause()
    {
    	Log.d("RADIO", "ChelInfo: Pause Activity");
    	
    	super.onPause();  
    	
    	int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "Radio.RU";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        
        Intent notificationIntent = new Intent(this, Radio.class);        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
        contentView.setImageViewResource(R.id.notimage, notimage);
        contentView.setTextViewText(R.id.notradio, notchanel);
        contentView.setTextViewText(R.id.notchanel, notradio);
        
        notification.contentIntent = contentIntent;
        notification.contentView = contentView;
        
        notification.flags = 2;
    	mNotificationManager.notify(NOTIFY_ID, notification); 
    }
    
    protected class myAdapter extends BaseAdapter {
		private LayoutInflater mLayoutInflater;

		public myAdapter(Context ctx) {
			mLayoutInflater = LayoutInflater.from(ctx);
		}

		public int getCount() {
			return mSign.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public String getString(int position) {
			return mSign[position] + " (" + mDate[position] + ")";
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null)
				convertView = mLayoutInflater.inflate(R.layout.item, null);

			ImageView image = (ImageView) convertView.findViewById(R.id.chanelimage);
			image.setImageResource(mImage[position]);

			TextView sign = (TextView) convertView.findViewById(R.id.chhanelname);
			sign.setText(mSign[position]);

			TextView date = (TextView) convertView.findViewById(R.id.chhanelautor);
			date.setText(mDate[position]);
			return convertView;
		}
	}
}
