package co.example.user.myapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
//puf
public class Offer extends AppCompatActivity {

    private String[] Categories = {"Спорт", "Отдых", "Оккультизм"};//Массив категорий выпадающего списка
    private EditText Name, City, Comment;
    private TextView Date;

    private Spinner spCategories;

    Calendar calendar = new GregorianCalendar();
    private int DIALOG_DATE = 1;
    private int myYear = calendar.get(Calendar.YEAR);
    private int myMonth = calendar.get(Calendar.MONTH);
    private int myDay = calendar.get(Calendar.DAY_OF_MONTH);
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        // Создание выпадающего списка с категориями
        ArrayAdapter<String> CategoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories);
        spCategories = findViewById(R.id.Category);

        CategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Создание адаптера

        spCategories.setAdapter(CategoriesAdapter);// Установка адаптера


        // Диалог с выбором времени и даты
        tvDate = findViewById(R.id.Date);

        // Дефолтные инпуты
        Name = findViewById(R.id.Name);
        City = findViewById(R.id.City);
        Comment = findViewById(R.id.Comment);
        Date = findViewById(R.id.Date);

        //Запуск окна с карточками для отладки(после работы убрать)
        //Intent intent = new Intent(this, Card_Search_View.class);
        //startActivity(intent);
    }

    public void onclickDate(View view) {
        showDialog(DIALOG_DATE);
    }

    // Метод для выбора даты и времени в форме диалога
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBackDate, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBackDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            tvDate.setText(myDay + "/" + myMonth + "/" + myYear);
        }
    };

        // Метод кнопки "Создать" (Здесь отправляется запрос на сервер с целью записи в базу данных)
    public void Create(View view){
       /*String NameResponse = Name.getText().toString();
        String CityResponse = City.getText().toString();
        String CommentResponse = Comment.getText().toString();
        String DateResponse = Date.getText().toString();
        int SelectedCategoryResponse = spCategories.getSelectedItemPosition();*/

        /*ToDo: Отправление запроса на сервер с проверкой введенных данных*/

        // Переход на страницу с созданным мероприятием(Вид от создателя)
        Intent intent = new Intent(this, EventView_Creator.class);
        startActivity(intent);
    }
}
