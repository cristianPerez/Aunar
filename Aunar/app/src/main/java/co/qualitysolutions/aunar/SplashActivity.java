package co.qualitysolutions.aunar;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	    Handler HANDLER = new Handler();
	    HANDLER.postDelayed(new Runnable(){
	           public void run() {
	                finish();
	                startActivity (new Intent(getApplicationContext(), Login.class));
	                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	           }
	    }, 2000);
	
}
}
