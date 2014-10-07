package Resources;

import android.app.Activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WebService extends Activity {

	private String url = "";
	private String resultado;
	private JSONArray jArray;
	private List<BasicNameValuePair> valores;
	private InputStream is;
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public synchronized JSONArray conectar(String[] parametros) {
		this.valores = new ArrayList<BasicNameValuePair>();
		try {


            /*HttpParams my_httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(my_httpParams, 3000);
            HttpConnectionParams.setSoTimeout(my_httpParams, 1);*/

            this.valores.add(new BasicNameValuePair("clase",parametros[1]));
            this.valores.add(new BasicNameValuePair("metodo",parametros[0]));

			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(this.url);
			UrlEncodedFormEntity encodeEntity = null;

            if(parametros[0].equals("loguear")){
				this.valores.add(new BasicNameValuePair("correo",parametros[2]));
				this.valores.add(new BasicNameValuePair("contrasena",parametros[3]));
                this.valores.add(new BasicNameValuePair("push_id",parametros[4]));
			}

			encodeEntity = new UrlEncodedFormEntity(valores,"UTF-8");
			if (encodeEntity != null){
				((HttpPost) request).setEntity(encodeEntity);
			}
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			this.is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");
			String line = "0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			this.is.close();
			this.resultado = sb.toString();
			try {
				this.jArray = new JSONArray(resultado);
				
			} catch (Exception e) {
				this.resultado="["+this.resultado+"]";
				this.jArray = new JSONArray(resultado);
			}
		}catch (ClientProtocolException e) {
			try {
				this.jArray = new JSONArray("[{'mensaje':0}]");
			} catch (JSONException e1) {
			}
		}catch (IOException e) {
			e.printStackTrace();
			try {
				this.jArray = new JSONArray("[{'mensaje':0}]");
			} catch (JSONException e1) {
			}
		}catch (Exception e) {
			try {
				this.jArray = new JSONArray("[{'mensaje':0}]");
			}catch (JSONException e1) {
			}
		}
		return this.jArray;
	}
}