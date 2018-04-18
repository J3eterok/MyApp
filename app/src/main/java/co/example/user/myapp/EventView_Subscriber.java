package co.example.user.myapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class EventView_Subscriber extends AppCompatActivity {
    private TextView Name;
    private TextView Category;
    private TextView Date;
    private TextView Creator;
    private int userId;
    private String eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view__subscriber);
        /*ToDo: Реализовать запрос на сервер для получения данных о мероприятии*/
        SendData sender = new SendData();
        eventId = getIntent().getStringExtra("eventId");
        sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.getEvent={\"id\":\"" + eventId+"\"}";
        sender.result = 0;
        sender.execute();
        while(sender.resultString == null)
        {
            try{
                wait(1);
            }catch(Exception e)
            {

            }
        }
        String res = sender.resultString.replace("\\\"", "\"");//тут убираем неправильные кавычки вида: \n
        res = res.replace("{\"response\":\"{", "{"); //убираем лишнее слово response
        res = res.replace("}\"}", "}");
        Gson gson = new Gson();
        MyEvent event = gson.fromJson(res, MyEvent.class);
        Name = (TextView)findViewById(R.id.Name);
        Category = (TextView)findViewById(R.id.Category);
        Date = (TextView)findViewById(R.id.Date);
        //Creator = (TextView)findViewById(R.id.Creator); зачем ты это удалил, Максим?
        Name.setText(event.name);
        Category.setText(event.category.toString());
        Date.setText(event.datetime);
        //Creator.setText(event.creator.toString()); я вот совсем не пойму
    }

    public void ViewMembers(View view)
    {
        /*ToDo: Вызов activity со списком участников*/
    }

    public void SignUp(View view) {
        userId = getIntent().getIntExtra("userId", 1);
        if (userId == -1) {
            return;//пользователь не залогинен
        }
        SendData sender = new SendData();
        sender.result = 1;
        sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.addMember={\"id\":\""
                + userId + "\",\"eventId\":\"" + eventId + "\"}";
        sender.execute();
        while (sender.resultString == null) {
            try{
                wait(1);
            }catch (Exception e)
            {

            }
        }
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
            if (this.result == 1) { // код, если мы отправляем запрос search
                Toast.makeText(getBaseContext(),"Вы добавлены.", Toast.LENGTH_LONG);
            }else if(this.result == 0){

            }
        }
    }
}
