package com.xing.game.gogogo.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xing.game.gogogo.R;

/**
 * Created by wangxing on 15/12/14.
 */
public class FragmentWelcome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        return view;
    }
}
