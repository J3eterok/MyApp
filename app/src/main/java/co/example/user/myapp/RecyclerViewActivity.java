package co.example.user.myapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
class GetMyEvents{
    public int id;
    public String datetime;
}
public class RecyclerViewActivity extends AppCompatActivity {
    private List<Card> cards;
    private RecyclerView rv;
    private MyEvent[] myEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        SendData sender = new SendData();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String currentDateandTime = sdf.format(new Date());
        GetMyEvents sendObj = new GetMyEvents();
        sendObj.id = getIntent().getIntExtra("userId", 1);
        LoggedUser.Id = sendObj.id;
        sendObj.datetime = currentDateandTime;
        Gson gson = new Gson();
        sender.result = 0;
        sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.getMyEvent="+gson.toJson(sendObj);
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
        res = res.replace("{\"response\":\"[{", "[{"); //убираем лишнее слово response
        res = res.replace("}]\"}", "}]"); // меняем конец ответа, чтобы тоже не был лишним
        try{
            myEvents = gson.fromJson(res, MyEvent[].class);
        }
        catch(Exception e)
        {
            myEvents = new MyEvent[]{};
        }
        rv=(RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        initializeData();
        initializeAdapter();
    }
    private void initializeData(){
        cards = new ArrayList<>();
        for(MyEvent event:myEvents) {
            String id = "" + event.id;
            cards.add(new Card(id, event.name, event.datetime, event.city, event.category.toString()));
        }
    }

    public void ShowMyEvent(View view) //Метод открытия карточки
    {
        Intent intent = new Intent(getBaseContext(), EventView_Subscriber.class);
        View parentView = (View)view.getParent();
        TextView eventId = (TextView)parentView.findViewById(R.id.eventId);
        intent.putExtra("eventId", eventId.getText().toString());
        String res = eventId.getText().toString();
        startActivity(intent);
    }
    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(cards);
        rv.setAdapter(adapter);
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
            if (this.result == 1) {
            }else if(this.result == 0){

            }
        }
    }
}
