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

public class Offer extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private String[] Categories = {"Спорт", "Отдых", "Оккультизм"};//Массив категорий выпадающего списка
    private TextView CountInNumber;
    private SeekBar sbCountOfMembers;
    private EditText Name, Adress, City, Comment, Date, Time;

    private Spinner spCategories;

    Calendar calendar = new GregorianCalendar();
    private int DIALOG_DATE = 1;
    private int myYear = calendar.get(Calendar.YEAR);
    private int myMonth = calendar.get(Calendar.MONTH);
    private int myDay = calendar.get(Calendar.DAY_OF_MONTH);
    TextView tvDate;

    private int DIALOG_TIME=2;
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        // Создание выпадающего списка с категориями
        ArrayAdapter<String> CategoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories);
        spCategories = findViewById(R.id.Category);

        CategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Создание адаптера

        spCategories.setAdapter(CategoriesAdapter);// Установка адаптера

        // Реализация ползунка(кол-во мест)
        sbCountOfMembers = findViewById(R.id.CountSeekBar);
        sbCountOfMembers.setOnSeekBarChangeListener(this);

        CountInNumber = findViewById(R.id.CountInNumber);
        CountInNumber.setText("1");

        // Диалог с выбором времени и даты
        tvDate = findViewById(R.id.Date);
        tvTime = findViewById(R.id.Time);

        // Дефолтные инпуты
        Name = findViewById(R.id.Name);
        Adress = findViewById(R.id.Adress);
        City = findViewById(R.id.City);
        Comment = findViewById(R.id.Comment);
        Date = findViewById(R.id.Date);
        Time = findViewById(R.id.Time);

        //Запуск окна с карточками для отладки(после работы убрать)
        Intent intent = new Intent(this, Card_Search_View.class);
        startActivity(intent);
    }

    public void onclickDate(View view) {
        showDialog(DIALOG_DATE);
    }

    public void onclickTime(View view){ showDialog(DIALOG_TIME); }

    // Метод для выбора даты и времени в форме диалога
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBackDate, myYear, myMonth, myDay);
            return tpd;
        }
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBackTime, 00, 00, true);
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

    TimePickerDialog.OnTimeSetListener myCallBackTime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            tvTime.setText(hourOfDay + ":" + minute);
        }
    };

        // Метод кнопки "Создать" (Здесь отправляется запрос на сервер с целью записи в базу данных)
    public void Create(View view){
       /*String NameResponse = Name.getText().toString();
        String AdressResponse = Adress.getText().toString();
        String CityResponse = City.getText().toString();
        String CommentResponse = Comment.getText().toString();
        String DateResponse = Date.getText().toString();
        String TimeResponse = Time.getText().toString();
        String CountOfMembersResponse = CountInNumber.getText().toString();
        int SelectedCategoryResponse = spCategories.getSelectedItemPosition();*/

        /*ToDo: Отправление запроса на сервер с проверкой введенных данных*/

        // Переход на страницу с созданным мероприятием(Вид от создателя)
        Intent intent = new Intent(this, EventView_Creator.class);
        startActivity(intent);
    }
        // методы интерфейса ползунка(кол-во мест)
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        CountInNumber.setText(String.valueOf(sbCountOfMembers.getProgress()+1));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
