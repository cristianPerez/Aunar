package co.qualitysolutions.aunar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Resources.SaveInformation;

/**
 * Created by Andres on 05/10/2014.
 */
public class Fragment_1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_1, container, false);

    }

    public String[] ciudades()
    {
        new SaveInformation(getActivity()).execute("ciudades","");

        return null;
    }

}