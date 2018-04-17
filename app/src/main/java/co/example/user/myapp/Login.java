package co.example.user.myapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, null);

        final EditText email = (EditText) v.findViewById(R.id.editText1);
        final EditText pasw = (EditText) v.findViewById(R.id.editText2);
        final ImageButton btn = (ImageButton) v.findViewById(R.id.imageButton);
//        final String email = email.getText().toString();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent SecAct = new Intent(v.getContext(), RecyclerViewActivity.class);
                startActivity(SecAct);

                doInBackground(email, pasw, v);

                if(email.getText().toString() == "" || pasw.getText().toString() == ""){
                    Toast.makeText(getActivity(), "Напишите Ваш email и password",
                            Toast.LENGTH_SHORT).show();


                    return;
                    //    почему-то не работает
                }

                else{

//                    Intent SecAct = new Intent(v.getContext(), RecyclerViewActivity.class);
//                    startActivity(SecAct);

//                    doInBackground(email, pasw, v);
                }

            }
        });




        return v;
    }
    String resultString = null;


    protected String doInBackground(EditText email, EditText pasw, View v) {
        try {


            String myURL = "http://193.105.65.66:1080/~h2oop/?iteam.login={\"email\":\""+email.getText()+"\",\"hash\":\""+pasw.getText()+"\"}";

            String parammetrs = " ";
            byte[] data = null;
            InputStream is = null;



            try {
                URL url = new URL(myURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.getBytes().length));
                conn.setDoOutput(true);
                conn.setDoInput(true);


                // конвертируем передаваемую строку в UTF-8
                data = parammetrs.getBytes("UTF-8");


                OutputStream os = conn.getOutputStream();


                // передаем данные на сервер
                os.write(data);
                os.flush();
                os.close();
                conn.connect();
                String responseId= conn.getResponseMessage();


                // передаем ответ сервер
                ByteArrayOutputStream baos = new ByteArrayOutputStream();


                // не забыть это убрать, добавил когда проверял




//                //тут надо поменять
//                if (responseId == "error") {    // Если все ОК (ответ 200)
//                    Toast.makeText(getActivity(), "Проверьте правильность данных",
//                            Toast.LENGTH_SHORT).show();
//
//
//                } else {
//                    // убрать коммент
////                    Intent i = new Intent(v.getContext(), MyMyEvent.class);
////                    i.putExtra("key", responseId);
//
//                    Intent SecAct = new Intent(v.getContext(), MyEvento.class);
//                    startActivity(SecAct);
//
//
//
//                }


                conn.disconnect();

            } catch (MalformedURLException e) {

                //resultString = "MalformedURLException:" + e.getMessage();
            } catch (IOException e) {

                //resultString = "IOException:" + e.getMessage();
            } catch (Exception e) {

                //resultString = "Exception:" + e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}