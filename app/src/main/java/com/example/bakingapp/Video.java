package com.example.bakingapp;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bakingapp.fragment.Fragment2;
import com.example.bakingapp.model.Step;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;

public class Video extends AppCompatActivity {

    @BindView(R.id.video_details)
    RelativeLayout relativeLayout;

    private Fragment2 fragment2;
    private List<Step> steps;
    private String VID,CURRENT;
    public static int current = -1;
    public static long current_time = 0;

    private final String P="pos",T="time";

    private String TAG = "d99";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        fragment2 = new Fragment2();
        VID = getString(R.string.vid);
        CURRENT = getString(R.string.current);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.video_details,fragment2)
                .commit();

        if (savedInstanceState!=null){


            current = savedInstanceState.getInt(P);
            current_time = savedInstanceState.getLong(T);

            Log.d(TAG, "savedInstanceState " + current);

        }

        if (getIntent().getExtras()!=null){

            Log.d(TAG, "getIntent()" );
            steps = Parcels.unwrap(getIntent().getParcelableExtra(VID));

            if (savedInstanceState==null)
            current = getIntent().getIntExtra(CURRENT,0);
        }

    }

    public List<Step> getSteps() {
        return steps;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //store the data
        outState.putInt(P,fragment2.getCurrent());
        outState.putLong(T,fragment2.getSimpleExoPlayer().getCurrentPosition());

        Log.d(TAG, "onSave()" + outState.getLong(T,0L));


    }


}
