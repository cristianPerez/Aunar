package co.qualitysolutions.aunar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Andres on 05/10/2014.
 */
public class Fragment_2 extends Fragment implements OnItemClickListener {

    private ListView lstBodegas;
    private JSONArray jsonBodegas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.lstBodegas = (ListView)getView().findViewById(R.id.lstBodegas);
        this.lstBodegas.setOnItemClickListener(this);
        String json = "[{'id_bodega':'1','capacidad':'200000','cantidad':'120000','id_entidad':'1'}]";

        try {
            this.jsonBodegas = new JSONArray(json);
            displayRoutes(new JSONArray(json));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayRoutes(JSONArray routes){
        ArrayList<String> listBodegaNames = new ArrayList<String>();
        //this.plannedRoutesActive = new JSONArray();
        for(int i=0; i<routes.length(); i++){
            try {
                    listBodegaNames.add("Capacidad: "+routes.getJSONObject(i).getString("capacidad"));

            } catch (JSONException e) {
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), simple_list_item_1, listBodegaNames);
        this.lstBodegas.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            Toast.makeText(getActivity(),this.jsonBodegas.getJSONObject(position).getString("capacidad"),Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
        }



    }
}