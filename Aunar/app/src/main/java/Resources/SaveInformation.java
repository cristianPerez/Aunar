package Resources;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;

import co.qualitysolutions.aunar.R;

public class SaveInformation extends AsyncTask<String, Void, Void> {
	
	private WebService connection;
	private Activity activity;
	private SharedPreferences sharedpreferences;
	
	public SaveInformation(Activity activity){
		this.connection = new WebService();
		this.activity = activity;
		this.sharedpreferences = activity.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected Void doInBackground(String... params){
		JSONArray answer;
		if(this.thereIsInternet()){
				this.connection.setUrl("http://aunar.qualitysolutions.co/controlador/fachada.php");

				answer = this.connection.conectar(params);
				try {
					if(answer.getJSONObject(0).getString("mensaje").equals("1")){


					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
		}
		else{
			//Toast
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		
	}
	
	private boolean thereIsInternet() {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}
}