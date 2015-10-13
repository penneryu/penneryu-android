package com.penner.android.data;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by PennerYu on 15/10/13.
 */
public abstract class HttpJsonFactoryBase<T> extends HttpFactoryBase<T> {

    @Override
    protected T AnalysisContent(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        JsonReader json = new JsonReader(reader);
        try {
            return AnalysisData(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
            json.close();
        }
    }

    protected abstract T AnalysisData(JsonReader reader) throws IOException;
}
