package ila.fr.codisintervention.activities;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import ila.fr.codisintervention.R;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class SecondMainActivity extends AppCompatActivity {
    private static final String TAG = "SecondMainActivity";

    private ServiceConnection webSocketServiceConnection;
    private ServiceConnection modelServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Here we call the aware interface to perform the binding at our place, we gain an instance of ServiceConnection
        Log.d(TAG, "Bind to services");
        //modelServiceConnection = bindModelService();
        //webSocketServiceConnection = bindWebSocketService();
    }

//    @Override
//    public void onModelServiceConnected() {
//        Log.i(TAG, "ModelServiceConnected");
//    }
//
//    @Override
//    public void setModelService(ModelServiceBinder.IMyServiceMethod modelService) {
//        Log.d(TAG, "Here we gain an instance of modelService: " + modelService);
//    }
//
//    @Override
//    public void onWebSocketServiceConnected() {
//        Log.d(TAG, "WebSocketServiceConnected");
//    }
//
//    @Override
//    public void setWebSocketService(WebSocketServiceBinder.IMyServiceMethod webSocketService) {
//        Log.d(TAG, "Here we gain an instance of webSocketService: " + webSocketService);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //We unbind from the registered services
//        unbindModelService(modelServiceConnection);
//        unbindWebSocketService(webSocketServiceConnection);
    }

    public void onClick(View view) {
        Log.d(TAG, "On Click");
    }
}
