package com.penner.android.model.ashmen;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by penneryu on 16/2/23.
 */
public interface IMemoryService extends IInterface {

    static abstract class Stub extends Binder implements IMemoryService {
        private static final String DESCRIPTOR = "com.penner.android.model.ashmen.IMemoryService";

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMemoryService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }

            IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMemoryService) {
                return (IMemoryService) iInterface;
            }
            return new IMemoryService.Stub.Proxy(obj);
        }

        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_getFileDescriptor: {
                    data.enforceInterface(DESCRIPTOR);
                    String result = this.getContentValue();
                    reply.writeNoException();
                    if (result != null) {
                        reply.writeInt(1);
                        reply.writeString(result);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_setValue: {
                    data.enforceInterface(DESCRIPTOR);
                    int val = data.readInt();
                    setValue(val);
                    reply.writeNoException();
                    return true;
                }
            }

            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IMemoryService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public String getContentValue() throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                String result;
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_getFileDescriptor, data, reply, 0);
                    reply.readException();

                    if (0 != reply.readInt()) {
                        result = reply.readString();
                    } else {
                        result = null;
                    }
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return result;
            }

            @Override
            public void setValue(int val) throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    data.writeInterfaceToken(DESCRIPTOR);
                    data.writeInt(val);
                    mRemote.transact(Stub.TRANSACTION_setValue, data, reply, 0);
                    reply.readException();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
            }
        }

        static final int TRANSACTION_getFileDescriptor = IBinder.FIRST_CALL_TRANSACTION + 0;
        static final int TRANSACTION_setValue = IBinder.FIRST_CALL_TRANSACTION + 1;
    }

    String getContentValue() throws RemoteException;
    void setValue(int val) throws RemoteException;
}
