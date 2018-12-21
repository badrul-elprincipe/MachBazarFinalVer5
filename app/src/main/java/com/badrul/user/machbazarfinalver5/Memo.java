package com.badrul.user.machbazarfinalver5;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.support.v7.app.ActionBar;

public class Memo extends AppCompatActivity {
    TextView di;
    TextView ken;
    ListView listView1;
    Button checkOut;
    static DatabaseReference databaseReference1;
    MemoAdapter memoAdapter;
    ActionBar actionBar;
    TextView ins;
    EditText nm;
    EditText adm;
    String send;
    static String dlt;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        Intent intent=getIntent();
        dlt=intent.getStringExtra("deln");

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3D4238")));

        auth=FirebaseAuth.getInstance();


        ins=findViewById(R.id.htext);
        nm=findViewById(R.id.dname);
        adm=findViewById(R.id.daddress);



        di=findViewById(R.id.dtext);
        ken=findViewById(R.id.kena);
        checkOut=findViewById(R.id.con);
        listView1=findViewById(R.id.list3);
        memoAdapter=new MemoAdapter(this);
        listView1.setAdapter(memoAdapter);
        //String num=auth.getCurrentUser().getPhoneNumber().toString();


        databaseReference1= FirebaseDatabase.getInstance().getReference("memoitem").child(dlt);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                memoAdapter.list.clear();
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    memoAdapter.list.add(d.getValue(memoitem.class));
                    memoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(Memo.this);
                builder.setMessage("Are You Want to Remove It ?");
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
                        memoAdapter.list.remove(position);
                        memoAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Item Removed",Toast.LENGTH_LONG).show();


                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                return true;


            }
        });








    }

    public void finish(View view) {


        ordering();
    }

    private void ordering() {


        if (memoAdapter.list.isEmpty()){
            final AlertDialog.Builder builder=new AlertDialog.Builder(Memo.this);
            builder.setMessage("You Have No Items Left to Order You Have to Go Back");
            builder.setCancelable(false);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    delete(dlt);

                    Intent intent=new Intent(Memo.this,Menu.class);
                    startActivity(intent);
                    finish();

                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        else {

            String s1=nm.getText().toString().trim();
            String s2=adm.getText().toString().trim();
            String s3=auth.getCurrentUser().getPhoneNumber().toString();


            if(s1.isEmpty()){
                nm.setError("Name required");
                nm.requestFocus();
                return;
            }
            if(s2.isEmpty()){
                adm.setError("Address required");
                adm.requestFocus();
                return;
            }
            send="Order:"+"\n"+memoAdapter.list.get(0).getNaam().toString()+" "+memoAdapter.list.get(0).getKoyta().toString()+" "+memoAdapter.list.get(0).getKoto().toString();
            for (int i=1;i<memoAdapter.list.size();i++){
                send=send+"\n"+memoAdapter.list.get(i).getNaam().toString()+" "+memoAdapter.list.get(i).getKoyta().toString()+" "+memoAdapter.list.get(i).getKoto().toString();
            }
            send=send+"\n"+"Name: "+nm.getText();
            send=send+"\n"+"Phone Number: "+s3;
            send=send+"\n"+"Address: "+adm.getText();






            final AlertDialog.Builder builder=new AlertDialog.Builder(Memo.this);
            builder.setMessage("Confirm Order & Prepare Invoice ?");
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
                    delete(dlt);


                    Intent intent=new Intent(Memo.this,Invoice.class);
                    intent.putExtra("sql",send);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),"Forwarding to invoice",Toast.LENGTH_LONG).show();

                    finish();

                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }

    }



    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(Memo.this);
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
                delete(dlt);

                Intent intent=new Intent(Memo.this,Menu.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();








    }

    private void delete(String dlt) {
        DatabaseReference dUser=FirebaseDatabase.getInstance().getReference("users").child(dlt);
        DatabaseReference dMitm=FirebaseDatabase.getInstance().getReference("memoitem").child(dlt);
        dUser.removeValue();
        dMitm.removeValue();
    }


}
