package co.example.user.myapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Search extends AppCompatActivity {
    String[] data = {"спорт", "концерты", "развлечения", "прогулка", "другое"};

    /** Called when the activity is first created. */



    TextView tV;
    Button button;
    EditText cityField;
    EditText dateField;
    HttpURLConnection conn;

    /** Called when the activity is first created. */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // найдем View-элементы
        tV= (TextView) findViewById(R.id.tV);
        button = (Button) findViewById(R.id.button);
        cityField = (EditText) findViewById(R.id.editText);
        dateField = (EditText) findViewById(R.id.editText2);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(4);

        View.OnClickListener search = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (cityField.getText().toString() == ""){
                    Toast.makeText(getBaseContext(), "Введите Город.", Toast.LENGTH_LONG);
                    return;
                }
            }
        };
    }

    class SendData extends AsyncTask<Void, Void, Void> {

        String resultString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                String myURL = "http://10.0.2.2:8000/api/?iteam.search={\"city\":\""+cityField.getText()+"\",\"datetime\":\""+cityField.getText()+"\"}";

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
                    data = null;
                    conn.connect();
                    int responseCode= conn.getResponseCode();


                    // передаем ответ сервер
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    if (responseCode == 200) {    // Если все ОК (ответ 200)
                        is = conn.getInputStream();

                        byte[] buffer = new byte[8192]; // размер буфера


                        // Далее так читаем ответ
                        int bytesRead;



                        while ((bytesRead = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, bytesRead);
                        }


                        data = baos.toByteArray();
                        resultString = new String(data, "UTF-8");  // сохраняем в переменную ответ сервера, у нас "OK"



                    } else {
                    }

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
}
