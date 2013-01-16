package dfh.chelaxe.radio;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.acra.ReportingInteractionMode;
import android.app.Application;


@ReportsCrashes(formKey = "dDZOR1BzbG9xMzN6RlhadFBQSFRyRlE6MQ", 
	mode = ReportingInteractionMode.TOAST,
	forceCloseDialogAfterToast = false,
	resToastText = R.string.crash_toast_text) 

public class ApMy extends Application
{	
	private String logkey = "RADIO";
	
	public String LOGKEY()
	{
		return this.logkey;
	}
	
	private String[] clock;
	
	public String[] GetClock()
	{
		return this.clock;
	}
	
	public void SetClock(String[] clocks)
	{
		this.clock = clocks;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		ACRA.init(this);	
		
		this.clock = new String[]{};
	}
}