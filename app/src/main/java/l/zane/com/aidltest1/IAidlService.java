package l.zane.com.aidltest1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import l.zane.com.aidltest1.aidl.IMyAidlInterface;

public class IAidlService extends Service {
    private static final String TAG = "IAidlService";
    private Context context = this;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 当客户端绑定到该服务的时候 会执行
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "服务端--onBind");
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e(TAG, "我是服务器...正在计算");
            Intent intent = new Intent();
            intent.putExtra("hahah", "sb");
            intent.setAction("com.a.a.a");
            sendBroadcast(intent);
            return num1 + num2;
        }
    };
}
