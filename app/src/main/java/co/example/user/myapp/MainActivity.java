package co.example.user.myapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Change (View view){
        Fragment fragment=null;

        switch (view.getId()){
            case R.id.imageButton4:
                fragment = new Login();

                break;
            case R.id.imageButton2:
                fragment = new Registr();
                break;
        }

        FragmentManager fme = getSupportFragmentManager();
        FragmentTransaction fte = fme.beginTransaction();

        fte.replace(R.id.fr_place, fragment);
        fte.commit();


    }
}
