package com.badrul.user.machbazarfinalver5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.badrul.user.machbazarfinalver5.Menu.a1;
import static com.badrul.user.machbazarfinalver5.Menu.a2;
import static com.badrul.user.machbazarfinalver5.Menu.a3;
import static com.badrul.user.machbazarfinalver5.Menu.stringName;
import static com.badrul.user.machbazarfinalver5.Menu.stringQua;
import static com.badrul.user.machbazarfinalver5.Menu.stringSol;


public class FishAdapter extends ArrayAdapter<String> {
    String[]mtit;
    String[]mdata;
    String[]mpr;
    String[]mqua;
    String[]mQ;
    int [] imgs;
    Context c;
    static int comp;
    static String mName;
    static String mSol;
    static String mQuant;


    FishAdapter(Context context, int[]imgs, String[]tit, String[]data, String[]pr, String[]qua, String[]q){
        super(context,R.layout.itemlayout,R.id.names,tit);
        this.c=context;

        this.imgs=imgs;
        this.mtit=tit;
        this.mdata=data;
        this.mpr=pr;
        this.mqua=qua;
        this.mQ=q;

    }



    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) c.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.itemlayout,parent,false);

        ImageView imageView=row.findViewById(R.id.imageView2);
        final TextView title=row.findViewById(R.id.names);


        TextView detail=row.findViewById(R.id.details);
        final TextView pri=row.findViewById(R.id.prices);
        final TextView quantity=row.findViewById(R.id.quants);
        TextView un=row.findViewById(R.id.unit);
        final TextView add=row.findViewById(R.id.adds);
        final TextView soll=row.findViewById(R.id.sol);
        final TextView sub=row.findViewById(R.id.subs);
        Button memo=row.findViewById(R.id.memos);

        imageView.setImageResource(imgs[position]);
        title.setText(mtit[position]);
        detail.setText(mdata[position]);
        pri.setText(mpr[position]);
        quantity.setText(mqua[position]);
        soll.setText(mQ[position]);

        final String min=quantity.getText().toString().trim();
        final int minn=Integer.parseInt(min);
        final int max=10;
        comp=minn;


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=quantity.getText().toString().trim();
                comp=Integer.parseInt(s);
                if (minn>=comp){
                    sub.setEnabled(false);
                    quantity.setText(s);



                }
                if(minn<comp){
                    sub.setEnabled(true);
                    add.setEnabled(true);
                    comp=comp-1;
                    String s1=Integer.toString(comp).trim();
                    quantity.setText(s1);





                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //if (comp==minn){
                     //comp=comp+1;
                     //String s2=Integer.toString(comp).trim();
                     //quantity.setText(s2);

                 //}
                 if (comp==max){
                     add.setEnabled(false);
                 }
                 if(comp<max) {

                     add.setEnabled(true);
                     comp=comp+1;
                     String s2=Integer.toString(comp).trim();
                     quantity.setText(s2);
                     sub.setEnabled(true);
                 }

            }
        });
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName=title.getText().toString().trim();
                mQuant=quantity.getText().toString().trim();
                mSol=soll.getText().toString().trim();




                stringName=mName;
                stringQua=mQuant;
                stringSol=mSol;



                Menu.watch();
                a1.add(mName);
                a2.add(mQuant);
                a3.add(mSol);

                see();



            }
        });






       return row;
    }

    private void see() {
        Toast.makeText(getContext(),"Added to memo",Toast.LENGTH_SHORT).show();
    }


}
