package org.androidtown.handycar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by GE62 on 2017-07-28.
 */

public class f_price_fragment extends Fragment {
    TextView t1, t2, t3, t4;
    Bundle extra;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.f_info, container, false);
        t1 = (TextView) rootView.findViewById(R.id.textView);
        t2 = (TextView) rootView.findViewById(R.id.textView2);
        t3 = (TextView) rootView.findViewById(R.id.textView3);
        t4 = (TextView) rootView.findViewById(R.id.textView4);

        extra = getArguments();
        Log.d("ASD",extra+"");
        t1.setText(extra.getString("Allavg").toString());
        t2.setText(extra.getString("Silavg").toString());
        return rootView;
    }
}
