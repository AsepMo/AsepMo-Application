package com.asepmo.application;

import android.app.Application;
import android.content.Context;

import com.asepmo.R;
import com.asepmo.engine.graphics.typeface.CalligraphyConfig;
import com.asepmo.engine.widget.TextField;
import com.asepmo.engine.widget.CustomViewWithTypefaceSupport;

public class ApplicationMain extends Application
{
    private static Context context;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                      .setDefaultFontPath("fonts/Roboto-ThinItalic.ttf")
                                      .setFontAttrId(R.attr.fontPath)
                                      .addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class)
                                      .addCustomStyle(TextField.class, R.attr.textFieldStyle)
									  .build()); 
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
	}
    
}
