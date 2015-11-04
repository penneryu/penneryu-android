package com.penner.android.data.bottomtab;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PennerYu on 15/11/4.
 */
public class ConversationInfo implements Parcelable {

    public static final int PERSONAL_TYPE = 0;
    public static final int CHANNEL_TYPE = 1;
    public static final int GROUP_TYPE = 2;

    public static final int TOP_FLAG = 1;
    public static final int DISTURB_FLAG = 2;

    public int id;
    public int type;
    public int unreadCount;
    public int flags;
    public long time;
    public long rank;

    public String userId;
    public String userName;
    public String avatarUrl;

    public String messageTips;

    public ConversationInfo() {
    }

    public ConversationInfo(Parcel parcel) {
        id = parcel.readInt();
        type = parcel.readInt();
        unreadCount = parcel.readInt();
        flags = parcel.readInt();
        time = parcel.readLong();
        rank = parcel.readLong();
        userId = parcel.readString();
        userName = parcel.readString();
        avatarUrl = parcel.readString();
        messageTips = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeInt(unreadCount);
        dest.writeInt(this.flags);
        dest.writeLong(time);
        dest.writeLong(rank);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(avatarUrl);
        dest.writeString(messageTips);
    }

    public static final Parcelable.Creator<ConversationInfo> CREATOR
            = new Parcelable.ClassLoaderCreator<ConversationInfo>() {

        @Override
        public ConversationInfo createFromParcel(Parcel source) {
            return new ConversationInfo(source);
        }

        @Override
        public ConversationInfo[] newArray(int size) {
            return new ConversationInfo[size];
        }

        @Override
        public ConversationInfo createFromParcel(Parcel source, ClassLoader loader) {
            return new ConversationInfo(source);
        }
    };
}
