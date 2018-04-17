package co.example.user.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TestEvent extends AppCompatActivity {

    TextView cardName;
    TextView cardDate;
    TextView cardPlace;
    TextView cardCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event);

        cardName = (TextView)findViewById(R.id.text_name);
        cardDate = (TextView)findViewById(R.id.text_date);
        cardPlace = (TextView)findViewById(R.id.text_place);
        cardCategory = (TextView)findViewById(R.id.text_category);


        cardName.setText("myau");
        cardDate.setText("myau");
        cardPlace.setText("myau");
        cardCategory.setText("myau");
    }
}
