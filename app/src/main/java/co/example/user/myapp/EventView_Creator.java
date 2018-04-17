package co.example.user.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Locale;

public class EventView_Creator extends AppCompatActivity {
    Button Edit;
    Button Delete;
    EditText Name;
    EditText Comment;
    EditText Category;
    EditText Date;
    EditText Address;
    private MyEvent receivedEvent = null;
    //ToDo: вместо eventId = 3 нужно передать параметр нажатого мероприятия
    private int eventId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view__creator);
        //eventId = 1;//getIntent().getExtras("eventId");
        SendData sender = new SendData();
        sender.result = 0;
        sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.getEvent={\"id\":\""+eventId+"\"}";
        sender.execute();
        while(sender.resultString == null)
        {
            try {
                wait(1);
            }catch(Exception e)
            {

            }
        }
        String res = sender.resultString.replace("\\\"", "\"");//тут убираем неправильные кавычки вида: \n
        res = res.replace("{\"response\":\"{", "{"); //убираем лишнее слово response
        res = res.replace("}\"}", "}"); // меняем конец ответа, чтобы тоже не был лишним
        Gson gson = new Gson();
        receivedEvent = gson.fromJson(res, MyEvent.class);
        Edit = findViewById(R.id.Edit);
        Name = findViewById(R.id.Name);
        Comment = findViewById(R.id.Comment);
        Edit.setOnClickListener(EditEvent);
        Category = findViewById(R.id.Category);
//        Address = findViewById(R.id.Adress);
        Date = findViewById(R.id.Date);
        Name.setText(receivedEvent.name);
        Comment.setText(receivedEvent.comment);
        Category.setText(receivedEvent.category.toString());
        Address.setText(receivedEvent.address);
        Date.setText(receivedEvent.datetime);
    }

    // Удаение мероприятия
    public void Delete(View view)
    {
        /*ToDo: Реализовать запрос на сервер с целью удаления мероприятия*/
        SendData sender = new SendData();
        //eventId = 3;//getIntent().getExtras("eventId");
        sender.result = 2;
        sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.deleteEvent={\"id\":\""+eventId+"\"}";
        sender.execute();
    }

    public void ViewMembers(View view)
    {
        /*ToDo: Вызов activity со списком участников*/
    }

    // Тригер для активации изменяемых инпутов
    View.OnClickListener EditEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Edit.setText("Сохранить");
            Edit.setOnClickListener(SaveEvent);
            Name.setEnabled(true);
            Comment.setEnabled(true);
        }
    };

    // Сохранение измененных инпутов
    View.OnClickListener SaveEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            receivedEvent.category = Category.getText().toString();
            receivedEvent.datetime = Date.getText().toString();
            receivedEvent.name = Name.getText().toString();
            receivedEvent.comment = Comment.getText().toString();
            receivedEvent.comment = receivedEvent.comment.replace(" ", "%20");
            Edit.setText("Редактировать");
            Edit.setOnClickListener(EditEvent);
            Name.setEnabled(false);
            Comment.setEnabled(false);
            SendData sender = new SendData();
            Gson gson = new Gson();
            sender.result = 1;
            sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.editEvent="+gson.toJson(receivedEvent);
            sender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            while(sender.resultString == null)
            {
                try {
                    wait(1);
                }catch(Exception e)
                {

                }
            }
        }
    };
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
                Toast.makeText(getBaseContext(), "Мероприятие изменено.", Toast.LENGTH_LONG).show();
            }else if(this.result == 2){
                Toast.makeText(getBaseContext(), "Мероприятие удалено.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
