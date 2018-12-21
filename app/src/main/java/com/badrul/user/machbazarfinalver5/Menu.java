package com.badrul.user.machbazarfinalver5;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;


public class Menu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference userData;
    static String phonenumber;
    ListView listView;
    Button cart;

    int imgs[]={R.drawable.ilish,R.drawable.ilish,R.drawable.salt,R.drawable.salt,R.drawable.salt,R.drawable.poa,R.drawable.poa,R.drawable.poa,R.drawable.topse,R.drawable.topse};




    String qua[]={"3","2","2","2","2","2","2","2","2","2"};
    FishAdapter fishAdapter;
    static ArrayList<String> a1;
    static ArrayList<String>a2;
    static ArrayList<String>a3;
    static ArrayList<Integer>a4;
    static String stringName;
    static  String stringQua;
    static String stringSol;

    static DatabaseReference databaseReference;


    //static String id;
    static String contactNumber;
    static String s;




    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_menu);

        String[] tit={getResources().getString(R.string.fish_1),getResources().getString(R.string.fish_2),getResources().getString(R.string.fish_3),getResources().getString(R.string.fish_4),getResources().getString(R.string.fish_5),getResources().getString(R.string.fish_6),getResources().getString(R.string.fish_7),getResources().getString(R.string.fish_8),getResources().getString(R.string.fish_9),getResources().getString(R.string.fish_10)};

        String data[]={getResources().getString(R.string.fishdet_1),getResources().getString(R.string.fishdet_2),getResources().getString(R.string.fishdet_3),getResources().getString(R.string.fishdet_4),getResources().getString(R.string.fishdet_5),getResources().getString(R.string.fishdet_6),getResources().getString(R.string.fishdet_7),getResources().getString(R.string.fishdet_8),getResources().getString(R.string.fishdet_9),getResources().getString(R.string.fishdet_10)};

        String q[]={getResources().getString(R.string.fishq_p),getResources().getString(R.string.fishq_p),getResources().getString(R.string.fishq_p),getResources().getString(R.string.fishq_p),getResources().getString(R.string.fishq_p),getResources().getString(R.string.fishq_k),getResources().getString(R.string.fishq_k),getResources().getString(R.string.fishq_k),getResources().getString(R.string.fishq_k),getResources().getString(R.string.fishq_k)};


        String pr[]={getResources().getString(R.string.fishp_1),getResources().getString(R.string.fishp_2),getResources().getString(R.string.fishp_3),getResources().getString(R.string.fishp_4),getResources().getString(R.string.fishp_5),getResources().getString(R.string.fishp_6),getResources().getString(R.string.fishp_7),getResources().getString(R.string.fishp_8),getResources().getString(R.string.fishp_9),getResources().getString(R.string.fishp_10)};
        Intent intent=getIntent();
        mAuth=FirebaseAuth.getInstance();

        userData= FirebaseDatabase.getInstance().getReference("users");
        s=userData.push().getKey();
        phonenumber=mAuth.getCurrentUser().getPhoneNumber().toString();
        users users=new users(s,phonenumber);
        userData.child(s).setValue(users);

        contactNumber=intent.getStringExtra("contact");
        listView=findViewById(R.id.items);
        cart=findViewById(R.id.carts);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        a1=new ArrayList<String>();
        a2=new ArrayList<String>();
        a3=new ArrayList<String>();
        a4=new ArrayList<Integer>();

        databaseReference=FirebaseDatabase.getInstance().getReference("memoitem").child(s);

        fishAdapter=new FishAdapter(this,imgs,tit,data,pr,qua,q);


        listView.setAdapter(fishAdapter);









    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser==null){
            Intent authIntent=new Intent(Menu.this,Home.class);
            startActivity(authIntent);
            finish();
        }


    }
    @Override



    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){


            case  R.id.menuAbout:
                delete(s);
                a1.clear();
                a2.clear();
                a3.clear();
                startActivity(new Intent(Menu.this,About.class));
                break;

            case R.id.menuContact:
                delete(s);
                a1.clear();
                a2.clear();
                a3.clear();
                startActivity(new Intent(Menu.this,ContactDet.class));
                break;

            case R.id.menuEnglish:
                delete(s);
                a1.clear();
                a2.clear();
                a3.clear();
                setLocale("en");
                recreate();
                break;

            case R.id.menuBangla:
                delete(s);
                a1.clear();
                a2.clear();
                a3.clear();
                setLocale("bn");
                recreate();
                break;

            case R.id.menuExit:


                final AlertDialog.Builder builder=new AlertDialog.Builder(Menu.this);
                builder.setMessage("Are You Sure Want to Exit ?");
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
                        delete(s);
                        a1.clear();
                        a2.clear();
                        a3.clear();

                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);



                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

                break;




            case R.id.menuLogout:


                final AlertDialog.Builder builder1=new AlertDialog.Builder(Menu.this);
                builder1.setMessage("Are You Sure Want to Log Out ?");
                builder1.setCancelable(false);
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete(s);
                        a1.clear();
                        a2.clear();
                        a3.clear();


                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(Menu.this,Home.class));


                    }
                });
                AlertDialog alertDialog1=builder1.create();
                alertDialog1.show();


                break;

            default:








        }


        return super.onOptionsItemSelected(item);
    }

    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }
    public void loadLocale(){
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("My_Lang","");
        setLocale(language);
    }

    public void onBackPressed() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(Menu.this);
        builder.setMessage("Are You Sure Want to Exit ?");
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

                delete(s);
                a1.clear();
                a2.clear();
                a3.clear();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);



            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void delete(String s) {
        DatabaseReference dUser=FirebaseDatabase.getInstance().getReference("users").child(s);
        DatabaseReference dMitm=FirebaseDatabase.getInstance().getReference("memoitem").child(s);
        dUser.removeValue();
        dMitm.removeValue();

    }

    public void memo(View view) {

        if (a1.isEmpty() || a2.isEmpty() || a3.isEmpty() ){
            Toast.makeText(getApplicationContext(),"You have not bought anything",Toast.LENGTH_LONG).show();
        }
        else {



            Intent intent=new Intent(Menu.this,Memo.class);
            intent.putExtra("deln",s);

            startActivity(intent);

            a1.clear();
            a2.clear();
            a3.clear();
            //a4.clear();
            finish();


        }







    }
    public static void watch(){
        String sendName=stringName;
        String sendQua=stringQua;
        String sendSol=stringSol;
        String mId=databaseReference.push().getKey();


        memoitem memoitem=new memoitem(mId,sendName,sendQua,sendSol);
        databaseReference.child(mId).setValue(memoitem);



    }


}
