package com.penner.android.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.penner.android.R;
import com.penner.android.kotlin.model.main.MainRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PennerYu on 15/10/27.
 */
public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.main_fragment_penner, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<String> list = new ArrayList<String>();
        list.add("Fresco");
        list.add("Kotlin");
        list.add("LeanCloud");
        list.add("BottomTab");
        list.add("Service");
        list.add("Material");
        list.add("DataBinding");
        list.add("LargeImage");
        list.add("SurfaceView");
        list.add("RxJava");
        list.add("Ndk");
        recyclerView.setAdapter(new MainRecyclerAdapter(getContext(), list));
        return recyclerView;
    }
}
