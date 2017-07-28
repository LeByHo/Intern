package org.androidtown.handycar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class fragment_a extends  Fragment {
    Button trans;
    ImageView carimg;
    TextView carnumber;
    TextView carname;
    TextView carfuel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_fragment,container, false);
        trans = (Button)rootView.findViewById(R.id.transbutton);
        carnumber=(TextView)rootView.findViewById(R.id.carnumber);
        carname=(TextView)rootView.findViewById(R.id.carname);
        carfuel=(TextView)rootView.findViewById(R.id.carfuel);
        carimg=(ImageView)rootView.findViewById(R.id.carimg);
       final String name=carname.getText().toString();
        String number=carnumber.getText().toString();
        String fuel=carfuel.getText().toString();


        trans.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           // MainActivity.intent = new Intent(getContext(),MainActivity.class);
           //     MainActivity.intent.putExtra("carname",name);
             //   startActivity(MainActivity.intent);

            }

        });
        return rootView;
    }

}
