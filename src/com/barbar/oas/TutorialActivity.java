package com.barbar.oas;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TutorialActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        
        TextView textLink = (TextView)findViewById(R.id.textLink);
        textLink.setText(Html.fromHtml("<a href=https://github.com/svejdo1/OpenAnimalSounds\">" + getString(R.string.tutorial_projectpages) + "</a>"));
        textLink.setMovementMethod(LinkMovementMethod.getInstance());
        
        Button button = (Button)findViewById(R.id.buttonGotIt);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
    }
}
