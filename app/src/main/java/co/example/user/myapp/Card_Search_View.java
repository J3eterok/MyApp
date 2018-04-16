package co.example.user.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


public class Card_Search_View extends AppCompatActivity {

    public class Event { // Класс, описывающий событие
        String name, time, adress, category;

        Event(String name, String time, String adress, String category) {
            this.name = name;
            this.time = time;
            this.adress = adress;
            this.category = category;
        }
    }

    private List<Event> events;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__search__view);

        RecyclerView cards = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager mngr = new LinearLayoutManager(this);
        cards.setLayoutManager(mngr);

        events = new ArrayList<>();

        int n=6;//кол-во мероприятий

        //ToDo: Реализовать запрос с целью получения данных о мероприятии(Название, время, место, категория)

        for(int i=0; i<n;i++)
        {
            events.add(new Event("Playing bingo with my grandpa", "1.06.2018", "Kazan", "Intelligence games"));// Подставить параметры мероприятия
        }

        CARD_Adapter adapter = new CARD_Adapter(events);
        cards.setAdapter(adapter);

        String json = getIntent().getStringExtra("json");
        Gson gson = new Gson();
        //MyEvent[] events = gson.fromJson(json, MyEvent[].class);
//        for(MyEvent event:events) {
//
//        }
    }

    public void ShowEvent(View view) //Метод открытия карточки
    {
        Intent intent = new Intent(this, EventView_Subscriber.class);
        startActivity(intent);
    }
}
