package co.qualitysolutions.aunar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import Resources.WebService;
import android.view.View.OnClickListener;

import com.google.android.gcm.GCMRegistrar;


public class Login extends Activity implements OnClickListener {

    private ProgressDialog progress;
    private WebService conection;
    private SharedPreferences sharedpreferences;
    private EditText etxUser,etxPassword;
    private String user,password;
    private JSONArray answer;
    private Button entrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.progress = new ProgressDialog(this);
        this.conection = new WebService();
        this.sharedpreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        this.referenceControls();
        this.entrar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void referenceControls() {

        this.etxUser = (EditText) findViewById(R.id.user);
        this.etxPassword = (EditText) findViewById(R.id.password);
        this.entrar = (Button) findViewById(R.id.ingresar);

    }

    @Override
    public void onClick(View v){
        if(!this.etxUser.getText().toString().equals("") && !this.etxPassword.getText().toString().equals("")){
            new RequestLogin().execute(this.etxUser.getText().toString(),this.etxPassword.getText().toString());
        }
        else{

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.completar),Toast.LENGTH_LONG).show();


        }
    }

    public class RequestLogin extends AsyncTask<String, Void, Boolean> {

        private JSONArray answer;

        @Override
        protected void onPreExecute() {
            progress.setTitle(getResources().getString(R.string.titleProgres));
            progress.setMessage(getResources().getString(R.string.messageProgress));
            progress.setCancelable(true);
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            registrarId();
            if(sharedpreferences.getString("PUSH_ID","").equals(""))
                registrarId();

            String [] parameters = {"loguear","Usuario",params[0],params[1],sharedpreferences.getString("PUSH_ID","")};

                conection.setUrl("http://aunar.qualitysolutions.co/controlador/fachada.php");
               // conection.setUrl("http://192.168.1.14/aunar/controlador/fachada.php");
                this.answer = conection.conectar(parameters);
                try {
                    if(answer.getJSONObject(0).getInt("mensaje")==1){
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("USER",user);
                        editor.putString("PASSWORD",password);
                        editor.commit();
                        return true;
                    }
                    else{
                        return false;
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progress.dismiss();
            if(!result){
              Toast.makeText(getApplicationContext(),getResources().getString(R.string.loginMalo),Toast.LENGTH_LONG).show();
            }
            else{

                        etxUser.setText("");
                        etxPassword.setText("");
                        Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                        startActivity(intent);

            }
        }


        public void registrarId() {
            GCMRegistrar.register(getApplicationContext(), "1015699520759");
            Log.v("GCM", "Registrado");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("PUSH_ID", GCMRegistrar.getRegistrationId(getApplicationContext()));
            SystemClock.sleep(4000);
            editor.putString("PUSH_ID", GCMRegistrar.getRegistrationId(getApplicationContext()));
            editor.commit();
        }

    }
}