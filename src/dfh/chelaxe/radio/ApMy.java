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
	@Override
	public void onCreate() 
	{
		super.onCreate();
		ACRA.init(this);		
	}
}