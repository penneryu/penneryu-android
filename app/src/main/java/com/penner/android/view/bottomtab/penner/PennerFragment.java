package com.penner.android.view.bottomtab.penner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.penner.android.R;
import com.penner.android.model.bottomtab.penner.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PennerYu on 15/10/14.
 */
public class PennerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_fragment_penner, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            return;
        }

        RecyclerView recyclerView = (RecyclerView)getView().findViewById(R.id.bootom_penner_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String> list = new ArrayList<>();
        list.add("Data Binding");
        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), list));
    }
}
