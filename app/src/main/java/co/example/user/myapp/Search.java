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

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;

public class Search extends AppCompatActivity {
    String[] data = {};

    /** Called when the activity is first created. */



    TextView tV;
    Button button;
    EditText cityField;
    EditText dateField;
    SendData conn;

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
        SendData sender = new SendData();
        sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.getCategories={}";
        sender.execute();
        while(sender.resultString == null){
            try{
                wait(1);
            }
            catch(Exception e)
            {

            }
        }
        Gson gson = new Gson();
        if(sender.resultString != null) {
            String res = sender.resultString.replace("\\\"", "\"");//тут убираем неправильные кавычки вида: \n
            res = res.replace("{\"response\":\"[{", "[{"); //убираем лишнее слово response
            res = res.replace("}]\"}", "}]"); // меняем конец ответа, чтобы тоже не был
            MyCategory[] cat = gson.fromJson(res, MyCategory[].class);
            int i = 0;
            for (MyCategory myCat: cat) {
                data[i] = myCat.name;
                i++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setAdapter(adapter);
            // заголовок
            spinner.setPrompt("Title");
            // выделяем элемент
            spinner.setSelection(4);
        }

        View.OnClickListener search = new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (cityField.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Введите Город.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(dateField.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Введите дату.", Toast.LENGTH_LONG).show();
                    return;
                }
                conn.result = 1;
//                conn = new SendData();
//                conn.server = "http://193.105.65.66:1080/~h2oop/?iteam.getCategories={}";
//                try {
//                    conn.execute();
//                }catch(Exception e) {
//                }

            }
        };
        button.setOnClickListener(search);
    }

    class SendData extends AsyncTask<Void, Void, Void> {

        String resultString = null;
        public String server;
        public int result = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void  doInBackground(Void... params) {
            try {

               String myURL = server;

                String parammetrs = " ";
                byte[] data = null;
                InputStream is = null;



                try {
                    URL url = new URL(server);
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

                    resultString = "MalformedURLException:" + e.getMessage();
                } catch (IOException e) {

                   resultString = "IOException:" + e.getMessage();
                } catch (Exception e) {

                    resultString = "Exception:" + e.getMessage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (conn.result == 1) { // код, если мы отправляем запрос search
                String res = conn.resultString.replace("\\\"", "\"");//тут убираем неправильные кавычки вида: \n
                res = res.replace("{\"response\":\"[{", "[{"); //убираем лишнее слово response
                res = res.replace("}]\"}", "}]"); // меняем конец ответа, чтобы тоже не был лишним
                Gson gson = new Gson();
                MyEvent[] events = gson.fromJson(res, MyEvent[].class);
                //Intent intent = new Intent(this)
            }else if(conn.result == 0){

            }
        }
    }
}
