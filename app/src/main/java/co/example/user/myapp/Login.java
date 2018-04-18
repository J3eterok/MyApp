package co.example.user.myapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
class LoginEntity //класс для удобства формирования json
{
    public String email;
    public String hash;
}
class UserId{
    public int id;
}
public class Login extends Fragment {

    private EditText email;
    private EditText pasw;
    private ImageButton btn;
    private Integer userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, null);

        email = (EditText) v.findViewById(R.id.editText1);
        pasw = (EditText) v.findViewById(R.id.editText2);
        btn = (ImageButton) v.findViewById(R.id.imageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(email.getText().toString() == "" || pasw.getText().toString() == ""){
                    Toast.makeText(getActivity(), "Напишите Ваш email и password",
                            Toast.LENGTH_SHORT).show();
                return;
                }
                LoginEntity loginEntity = new LoginEntity();
                loginEntity.email = email.getText().toString();
                loginEntity.hash = pasw.getText().toString();
                Gson gson = new Gson();
                SendData sender = new SendData();
                sender.server = "http://193.105.65.66:1080/~h2oop/?iteam.login="+gson.toJson(loginEntity);
                sender.execute();
                while(sender.resultString == null)
                {
                    try{wait(1);}
                    catch(Exception e)
                    {
                    }
                }
                String json = sender.resultString.replace("{\"response\":\"{\\\"", "{\"");
                json = json.replace("\\\"", "\"");
                json = json.replace("}\"}","}");
                UserId user;
                try {
                    user = gson.fromJson(json, UserId.class);//bcghавить, написать класс юзер
                    userId = user.id;
                }catch(Exception e)
                {
                    Toast.makeText(getContext(), "Неверные логин или пароль!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LoggedUser.Id = userId;
                Intent intent = new Intent(v.getContext(), RecyclerViewActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });




        return v;
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (this.result == 1) {
            }else if(this.result == 0){

            }
        }
    }

}