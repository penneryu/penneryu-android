package com.penner.android;

import android.os.Bundle;

import com.penner.android.base.BaseActivity;
import com.penner.android.model.rxjava.RxPennerInfo;
import com.penner.android.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        List<RxPennerInfo> infoList = new ArrayList<>();
        infoList.add(new RxPennerInfo("ab", "abv"));
        infoList.add(new RxPennerInfo("ac", "acv"));
        infoList.add(new RxPennerInfo("ad", "adv"));
        infoList.add(new RxPennerInfo("ae", "aev"));
        infoList.add(new RxPennerInfo("ba", "bav"));
        infoList.add(new RxPennerInfo("bb", "bbv"));
        infoList.add(new RxPennerInfo("bc", "bcv"));
        infoList.add(new RxPennerInfo("bd", "bdv"));
        infoList.add(new RxPennerInfo("cd", "cdv"));
        infoList.add(new RxPennerInfo("ce", "cev"));

        Observable.from(infoList)
                .flatMap(rxPennerInfo -> Observable.from(rxPennerInfo.values))
                .filter(value -> value.endsWith("6") || value.endsWith("8"))
                .map(value -> getInfoByValue(value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rxPennerInfo1 -> LogUtils.d("rxjava", rxPennerInfo1.name));

    }

    private RxPennerInfo getInfoByValue(String value) {
        return new RxPennerInfo(value + " penneryu", value);
    }
}
