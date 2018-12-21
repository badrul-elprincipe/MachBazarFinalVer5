package com.badrul.user.machbazarfinalver5;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Session session;

    private String email,subject,msg;
    private ProgressDialog progressDialog;

    public SendMail(Context context, String email, String subject, String msg){
        this.context=context;
        this.email=email;
        this.subject=subject;
        this.msg=msg;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=ProgressDialog.show(context,"Sending Msg","Wait",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context,"Order Complete",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Properties props=new Properties();

        //gmail
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port","465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");

        //live
        //props.put("mail.smtp.host","smtp.live.com");
        //props.put("mail.smtp.socketFactory.port","587");
        //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        //props.put("mail.smtp.auth","true");
        //props.put("mail.smtp.port","587");

        session= Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            final String add="htntechlimited@gmail.com";
            final String pass="Tr@ctable2016";
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(add,pass);
            }
        });

        try {
            MimeMessage mm=new MimeMessage(session);
            final String add="htntechlimited@gmail.com";
            mm.setFrom(new InternetAddress(add));
            mm.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(email));
            mm.setSubject(subject);
            mm.setText(msg);

            Transport.send(mm);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;


    }

}
