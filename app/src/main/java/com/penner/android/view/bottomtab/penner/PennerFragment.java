package com.penner.android.view.bottomtab.penner;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.penner.android.R;
import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.data.bottomtab.LocalConversationFactory;
import com.penner.android.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by PennerYu on 15/10/14.
 */
public class PennerFragment extends Fragment implements Action1<List<ConversationInfo>> {

    RecyclerView recyclerView;
    List<ConversationInfo> conversationInfos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View rootView = inflater.inflate(R.layout.bottom_fragment_penner, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.bootom_penner_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LocalConversationFactory factory = new LocalConversationFactory(getActivity());

        factory.sqlBriteFindRecords(this);
//        conversationInfos = factory.findRecords();
//        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), conversationInfos));

        List<ConversationInfo> conversationInfos = new ArrayList<>(2);
        ConversationInfo info1 = new ConversationInfo();
        info1.userId = "1";
        info1.userName = "PennerYu";
        info1.time = System.currentTimeMillis();
        info1.rank = System.currentTimeMillis();
        info1.messageTips = "Hello World!";

        ConversationInfo info2 = new ConversationInfo();
        info2.unreadCount = 1;
        info2.userId = "2";
        info2.userName = "PengYu";
        info2.time = System.currentTimeMillis();
        info2.rank = System.currentTimeMillis();
        info2.messageTips = "Hello World! Hello World! Hello World! Hello World!";

        conversationInfos.add(info1);
        conversationInfos.add(info2);
        factory.insertRecord(conversationInfos);

        return rootView;
    }

    @Override
    public void call(List<ConversationInfo> conversationInfos) {
        LogUtils.d("PennerFragment", String.valueOf(conversationInfos.size()));
        LogUtils.d("PennerFragment", String.valueOf(Looper.getMainLooper().equals(Looper.myLooper())));
        this.conversationInfos = conversationInfos;
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
