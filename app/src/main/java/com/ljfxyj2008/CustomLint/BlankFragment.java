package com.ljfxyj2008.CustomLint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/10/28.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * Demo Activity with multiple errors that should be scanned by custom lint rules.
 */
public class BlankFragment extends Fragment {

    private HashMap<Integer, String> myMap = new HashMap<>();


    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myMap.put(1, "adf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         return inflater.inflate(R.layout.afragment_blank, container, false);
       /* return funa(inflater, container,
                savedInstanceState);*/
    }






    private View funa(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {
        return funb(inflater, container,
                savedInstanceState);
    }

    private View funb(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {
        return inflater.inflate(R.layout.afragment_blank, container, false);
    }

}
