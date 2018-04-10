package co.example.user.myapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EventView_Creator extends AppCompatActivity {
    Button Edit;
    Button Delete;
    EditText Name;
    EditText Comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view__creator);

        /*ToDo: Реализовать запрос на сервер для получения данных о мероприятии*/

        Edit = findViewById(R.id.Edit);
        Name = findViewById(R.id.Name);
        Comment = findViewById(R.id.Comment);
        Edit.setOnClickListener(EditEvent);
    }

    // Удаение мероприятия
    public void Delete(View view)
    {
        /*ToDo: Реализовать запрос на сервер с целью удаления мероприятия*/
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
            String NameResponse = Name.getText().toString();
            String CommentResponse = Comment.getText().toString();

            /*ToDo: Реализовать запрос на сервер с целью сохранения отредактирванного мероприятия*/

            Edit.setText("Редактировать");
            Edit.setOnClickListener(EditEvent);
            Name.setEnabled(false);
            Comment.setEnabled(false);
        }
    };
}
