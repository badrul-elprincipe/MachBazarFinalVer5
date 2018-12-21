package com.badrul.user.machbazarfinalver5;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.app.ActionBar;

public class Invoice extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    Button po;
    Button gb;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        Intent intent=getIntent();
        String s=intent.getStringExtra("sql");
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3D4238")));
        actionBar.setTitle(getResources().getString(R.string.inv_title));

        textView=findViewById(R.id.ht);
        textView1=findViewById(R.id.bt);
        po=findViewById(R.id.or);
        gb=findViewById(R.id.bck);

        textView1.setText(s);


    }

    public void place(View view) {
        String ss=textView1.getText().toString().trim();
        if (ss.isEmpty()){
            final AlertDialog.Builder builder=new AlertDialog.Builder(Invoice.this);
            builder.setMessage("Invoice Is Empty Please Go Back To Menu");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();


                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
        else{
            sent();
            textView1.setText("");
        }



    }

    private void sent() {
        String r="machbazaronline@gmail.com";
        String sub="Order from app";
        String msg=textView1.getText().toString();
        SendMail sm=new SendMail(this,r,sub,msg);

        sm.execute();
    }

    public void back(View view) {
        String ss=textView1.getText().toString().trim();

        if (ss.isEmpty()){
            final AlertDialog.Builder builder=new AlertDialog.Builder(Invoice.this);

            builder.setMessage("Return to Menu ?");
            builder.setCancelable(false);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(Invoice.this,Menu.class);
                    startActivity(intent);
                    finish();


                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
        else{
            final AlertDialog.Builder builder=new AlertDialog.Builder(Invoice.this);
            builder.setMessage("Are you sure want to cancel your order and return to Menu ?");

            builder.setCancelable(false);
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(Invoice.this,Menu.class);
                    startActivity(intent);
                    finish();


                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();


        }


    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(Invoice.this);
        builder.setMessage("Cancel Order & Go Back ?");
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intent=new Intent(Invoice.this,Menu.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
