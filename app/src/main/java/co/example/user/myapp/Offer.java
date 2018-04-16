package co.example.user.myapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
//puf
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
//        Intent intent = new Intent(this, Card_Search_View.class);
//        startActivity(intent);
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
        MyEvent event = new MyEvent();
        event.city = City.getText().toString();
        event.comment = "MyComment";
        event.name = Name.getText().toString();
        event.creator = "1";
        event.coord = "125.125";
        event.members = new String[]{};
        event.datetime = myDay + "/" + myMonth + "/" + myYear;
        event.address = Adress.getText().toString();
        event.category = "1";
        Gson gson = new Gson();
        String json = gson.toJson(event);
        SendData sender = new SendData();
        sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.createEvent="+json;
        sender.execute();
       // Intent intent = new Intent(this, EventView_Creator.class);
        //startActivity(intent);
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
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(), "Мероприятие создано.", Toast.LENGTH_SHORT).show();
        }
    }
}
