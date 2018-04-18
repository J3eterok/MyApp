package co.example.user.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FutureEvent extends AppCompatActivity {
    private List<Card> cards;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_event);
        rv=(RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        initializeData();
        initializeAdapter();
    }
    private void initializeData(){

        //парсинг для future event
        cards = new ArrayList<>();
//        cards.add(new Card("Hockey", "27.11.1998", "Kazan", "Sport"));
//        cards.add(new Card("Shashlyki", "23.05.2013", "Forest", "Relax"));
//        cards.add(new Card("Shopping", "17.03.2025", "Moskow", "Relax"));
//        cards.add(new Card("Reading", "06.06.2006","Hell", "Other"));
//        cards.add(new Card("Swimming", "23,08,2008", "Bitch", "Sport"));
//        cards.add(new Card("Swimming", "23,08,2008", "Bitch", "Sport"));
//        cards.add(new Card("Swimming", "23,08,2008", "Bitch", "Sport"));
//        cards.add(new Card("Swimming", "23,08,2008", "Bitch", "Sport"));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(cards);
        rv.setAdapter(adapter);
    }

    public void goBare (View view) {

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

            case R.id.imageButton3:
                Intent FiveAct = new Intent(this, FutureEvent.class);
                startActivity(FiveAct);
                break;

            case R.id.imageButton5:
                Intent SecAc = new Intent(this, RecyclerViewActivity.class);
                startActivity(SecAc);

        }


    }
}
