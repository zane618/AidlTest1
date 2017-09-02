package l.zane.com.appclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import l.zane.com.aidltest1.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private EditText ed1, ed2;
    private TextView tv1;
    private Button button;
    private IMyAidlInterface iMyAidlInterface;
    private ServiceConnection conn = new ServiceConnection() {
        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }
        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //app一启动就绑定
        bindService();
        sendBroadcast(ShortCutMag.getShortcutToDesktopIntent(this));
    }

    private void initView() {
        ed1 = (EditText) findViewById(R.id.et_1);
        ed2 = (EditText) findViewById(R.id.et_2);
        tv1 = (TextView) findViewById(R.id.tv_1);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int num1 = Integer.parseInt(ed1.getText().toString());
                    int num2 = Integer.parseInt(ed2.getText().toString());
                    int result = iMyAidlInterface.add(num1, num2);
                    tv1.setText(result + "");
                } catch (Exception e) {
                    e.printStackTrace();
                    tv1.setText("炸了");
                }
//                Intent intent = new Intent();
//                intent.setAction("com.a.a.a");
//                sendBroadcast(intent);
            }
        });
    }

    private void bindService() {
        //获取服务端
        Intent intent = new Intent();
        //必须 显示Intent启动 绑定服务
        intent.setComponent(new ComponentName("l.zane.com.aidltest1", "l.zane.com.aidltest1.IAidlService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }
}
