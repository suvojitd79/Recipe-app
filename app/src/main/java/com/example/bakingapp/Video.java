package com.example.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


        if (getIntent().getExtras()!=null){

            steps = Parcels.unwrap(getIntent().getParcelableExtra(VID));
            current = getIntent().getIntExtra(CURRENT,0);
        }

    }

    public List<Step> getSteps() {
        return steps;
    }

}
