package com.example.bakingapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.Details;
import com.example.bakingapp.R;
import com.example.bakingapp.VideoTitleAdapter;
import com.example.bakingapp.VideoTitleClick;

public class Fragment1 extends Fragment {


    RecyclerView recyclerView;
    private VideoTitleAdapter videoTitleAdapter;
    private Context context;

    public Fragment1() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (!(context instanceof VideoTitleClick))
            throw new RuntimeException("please implement VideoTitleClick in " + context.getClass());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Details details = (Details) context;

        View view = inflater.inflate(R.layout.details_frag1, container, false);
        recyclerView = view.findViewById(R.id.detailsRecyclerView);

        videoTitleAdapter = new VideoTitleAdapter((VideoTitleClick) context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(videoTitleAdapter);
        if(details.getSteps()!=null)
            videoTitleAdapter.setSteps(details.getSteps());
        return view;
    }




}
