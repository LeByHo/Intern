package org.androidtown.handycar;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by LEE on 2017-07-25.
 */

public class f_Record_fragment extends Fragment {
    TextView textView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.f_record, container, false);
        textView = (TextView)rootView.findViewById(R.id.text);
        textView.setBackgroundColor(Color.rgb(178,235,244));
        return rootView;
    }
}
