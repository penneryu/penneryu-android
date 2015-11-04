package com.penner.android.data.bottomtab;

/**
 * Created by PennerYu on 15/11/4.
 */
public class MessageInfo {

    public static final int TEXT_TYPE = 0;
    public static final int FILE_TYPE = 1;
    public static final int COMMENT_TYPE = 2;
    public static final int SYSTEM_TYPE = 3;

    public static final int SEND_DIRECT = 0;
    public static final int RECEIVED_DIRECT = 1;

    public static final int SUCESS_STATE = 0;
    public static final int FAIL_STATE = 1;
    public static final int INPROGRESS_STATE = 2;
    public static final int CREATE_STATE = 3;

    public int id;
    public int type;
    public int direct;
    public int state;
    public int convId;
    public int flags;
    public long time;
    public String fromId;
    public String toId;
    public String body;
}
