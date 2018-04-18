package co.example.user.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


public class Card_Search_View extends AppCompatActivity {

    public class Event { // Класс, описывающий событие
        String name, time, adress, category, id;

        Event(String id, String name, String time, String adress, String category) {
            this.name = name;
            this.time = time;
            this.adress = adress;
            this.category = category;
            this.id = id;
        }
    }

    private List<Event> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__search__view);

        RecyclerView cards = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager mngr = new LinearLayoutManager(this);
        cards.setLayoutManager(mngr);

        events = new ArrayList<>();
        String json = getIntent().getStringExtra("json");
        json = json.replace("\"members\":{", "\"members\":[{");
        json = json.replace("},\"category\"", "}],\"category\"");
        Gson gson = new Gson();
        MyEvent[] arrayEvents = null;
        try {
            arrayEvents = gson.fromJson(json, MyEvent[].class);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Мероприятия не найдены.", Toast.LENGTH_LONG).show();
            return;
        }
        //ToDo: Реализовать запрос с целью получения данных о мероприятии(Название, время, место, категория)

        for (MyEvent event : arrayEvents) {
            String myId = "" + event.id;
            events.add(new Event(myId, event.name, event.datetime, event.city, event.category.toString()));// Подставить параметры мероприятия
        }

        CARD_Adapter adapter = new CARD_Adapter(events);
        cards.setAdapter(adapter);
    }
//        String json = getIntent().getStringExtra("json");
//        Gson gson = new Gson();
//        MyEvent[] events = null;
//        try {
//            events = gson.fromJson(json, MyEvent[].class);
//        }catch(Exception e)
//        {
//            Toast.makeText(getBaseContext(), "Мероприятия не найдены.", Toast.LENGTH_LONG).show();
//            return;
//        }

//        for(MyEvent event:events) {
//            LinearLayout.LayoutParams Event_Layout = new LinearLayout.LayoutParams(wrapContent, matchParent);
//            Event_Layout.gravity = Gravity.CENTER;
//
//
//            //LinearLayout.LayoutParams Event_inner = new LinearLayout.LayoutParams(wrapContent, wrapContent);
//
//            TextView name = new TextView(this);
//            name.setText(event.name);
//            name.setTextColor(Color.parseColor("#000000"));
//            name.setBackgroundResource(R.drawable.border);
//            name.setOnClickListener(OpenEvent);
//            Main.addView(name, Event_Layout);
//
//            TextView time = new TextView(this);
//            time.setText(event.datetime);
//            time.setTextColor(Color.parseColor("#000000"));
//            Main.addView(time, Event_Layout);
//
//            TextView adress = new TextView(this);
//            adress.setText(event.city);
//            adress.setTextColor(Color.parseColor("#000000"));
//            Main.addView(adress, Event_Layout);
//
//            TextView category = new TextView(this);
//            category.setText(event.comment);
//            category.setTextColor(Color.parseColor("#000000"));
//            category.setPadding(0, 0, 0, 30);
//            Main.addView(category, Event_Layout);
//        }

    public void ShowEvent(View view) //Метод открытия карточки
    {
        Intent intent = new Intent(this, EventView_Subscriber.class);
        TextView eventId = view.findViewById(R.id.eventId);
        intent.putExtra("eventId", eventId.getText().toString());
        String res = eventId.getText().toString();
        startActivity(intent);
    }

    public void goBarto (View view) {

        switch (view.getId()) {
            case R.id.imageButton9:
                Intent SecAct = new Intent(this, RecyclerViewActivity.class);
                startActivity(SecAct);


                break;
            case R.id.imageButton11:
                Intent ThAct = new Intent(this, Search.class);
                startActivity(ThAct);
                break;

            case R.id.imageButton8:
                Intent fourAct = new Intent(this, Offer.class);
                startActivity(fourAct);
                break;

        }


    }
}
