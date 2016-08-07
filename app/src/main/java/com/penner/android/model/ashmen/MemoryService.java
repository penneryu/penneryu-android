package com.penner.android.model.ashmen;

import android.os.MemoryFile;

import com.penner.android.util.LogUtils;

import java.io.IOException;

/**
 * Created by penneryu on 16/2/23.
 */
public class MemoryService extends IMemoryService.Stub {

    private final static String LOG_TAG = "com.penner.android.model.ashmen.MemoryService";
    private MemoryFile file = null;

    public MemoryService() {
        try {
            file = new MemoryFile("Ashmem", 4);
            setValue(0);
        } catch(IOException ex) {
            LogUtils.i(LOG_TAG, "Failed to create memory file.");
            ex.printStackTrace();
        }
    }

    @Override
    public String getContentValue() {
        LogUtils.i(LOG_TAG, "Get File ContentValue.");
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        InputStream inputStream = file.getInputStream();
//        byte[] bytes = new byte[255];
//        int count;
//        try {
//            while ((count = inputStream.read(bytes, 0, 255)) > 0) {
//                outStream.write(bytes, 0, count);
//            }
//        } catch(IOException ex) {
//            LogUtils.i(LOG_TAG, "Failed to get file ContentValue.");
//            ex.printStackTrace();
//        }
        byte[] buffer = new byte[4];
        int val = -1;
        try {
            file.readBytes(buffer, 0, 0, 4);
            val = (buffer[0] << 24) | ((buffer[1] & 0xFF) << 16) | ((buffer[2] & 0xFF) << 8) | (buffer[3] & 0xFF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(val);
    }

    @Override
    public void setValue(int val) {
        if (file == null) {
            return;
        }

        byte[] buffer = new byte[4];
        buffer[0] = (byte)((val >>> 24) & 0xFF);
        buffer[1] = (byte)((val >>> 16) & 0xFF);
        buffer[2] = (byte)((val >>> 8) & 0xFF);
        buffer[3] = (byte)(val & 0xFF);

        try {
            file.writeBytes(buffer, 0, 0, 4);
            LogUtils.i(LOG_TAG, "Set value " + val + " to memory file. ");
        }
        catch(IOException ex) {
            LogUtils.i(LOG_TAG, "Failed to write bytes to memory file.");
            ex.printStackTrace();
        }
    }
}
