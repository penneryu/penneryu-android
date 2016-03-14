package com.penner.android;

import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import com.penner.android.base.BaseActivity;
import com.penner.android.model.rxjava.RxPennerInfo;
import com.penner.android.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    @Bind(R.id.rxjava_txt)
    TextView mRxjavaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        getToolbar().setNavigationIcon(R.drawable.ic_close_24dp);

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

//        new NoUiThread().start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new NoUiThread().start();
    }

    private RxPennerInfo getInfoByValue(String value) {
        return new RxPennerInfo(value + " penneryu", value);
    }

    class NoUiThread extends Thread {
        @Override
        public void run() {
//            Looper.prepare();
//            TextView tx = new TextView(RxJavaActivity.this);
//            tx.setText("non-UiThread update textview");

//            WindowManager windowManager = RxJavaActivity.this.getWindowManager();
//            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                    200, 200, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
//                    WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.OPAQUE);
//            windowManager.addView(tx, params);
            mRxjavaTextView.setText("Penner");
//            Looper.loop();
        }
    }
}
