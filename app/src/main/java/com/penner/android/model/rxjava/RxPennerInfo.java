package com.penner.android.model.rxjava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by penneryu on 16/1/19.
 */
public class RxPennerInfo {

    public RxPennerInfo(String name, String value) {
        this.name = name;
        this.value = value;

        this.values = new ArrayList<>(10);
        for (int i = 0; i < 10;i ++) {
            this.values.add(value + i);
        }
    }

    public String name;
    public String value;
    public List<String> values;
}
