package co.example.user.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EventView_Subscriber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view__subscriber);

        /*ToDo: Реализовать запрос на сервер для получения данных о мероприятии*/

    }

    public void ViewMembers(View view)
    {
        /*ToDo: Вызов activity со списком участников*/
    }

    public void SignUp(View view)
    {
        /*ToDo: Запрос на сервер(Подпись пользователя на мероприятие)*/
    }
}
