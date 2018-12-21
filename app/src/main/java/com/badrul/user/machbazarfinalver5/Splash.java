package com.badrul.user.machbazarfinalver5;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    Connection_Deetector connection_deetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        connection_deetector=new Connection_Deetector(this);



        if (connection_deetector.isConnected()){
            Toast.makeText(this,"Connected",Toast.LENGTH_LONG).show();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);





                    finish();
                }
            }, 5000);





        }

        else {
            Toast.makeText(this,"Not Connected",Toast.LENGTH_LONG).show();
        }
    }

}

