package co.example.user.myapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Card_Search_View extends AppCompatActivity {

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__search__view);
        //ToDo: Реализовать запрос с целью получения данных о мероприятии(Название, время, место, категория)
        String name_text = "Название мероприятия";
        String time_text = "Время";
        String adress_text = "Адрес";
        String category_text = "Категория";
        LinearLayout Main = findViewById(R.id.main_content);

        int n = 3; // Кол-во мероприятий, найденных при поиске

        for (int i = 0; i < n; i++) {
            LinearLayout.LayoutParams Event_Layout = new LinearLayout.LayoutParams(wrapContent, matchParent);
            Event_Layout.gravity = Gravity.CENTER;


            //LinearLayout.LayoutParams Event_inner = new LinearLayout.LayoutParams(wrapContent, wrapContent);

            TextView name = new TextView(this);
            name.setText(name_text);
            name.setTextColor(Color.parseColor("#000000"));
            name.setBackgroundResource(R.drawable.border);
            name.setOnClickListener(OpenEvent);
            Main.addView(name, Event_Layout);

            TextView time = new TextView(this);
            time.setText(time_text);
            time.setTextColor(Color.parseColor("#000000"));
            Main.addView(time, Event_Layout);

            TextView adress = new TextView(this);
            adress.setText(adress_text);
            adress.setTextColor(Color.parseColor("#000000"));
            Main.addView(adress, Event_Layout);

            TextView category = new TextView(this);
            category.setText(category_text);
            category.setTextColor(Color.parseColor("#000000"));
            category.setPadding(0, 0, 0, 30);
            Main.addView(category, Event_Layout);
        }
    }

    View.OnClickListener OpenEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //ToDO: открытие мероприятия
        }
    };
}
