package co.example.user.myapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class Registr extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registr, null);

        final EditText email = (EditText) v.findViewById(R.id.editText1);
        final EditText pasw = (EditText) v.findViewById(R.id.editText2);
        final ImageButton btn = (ImageButton) v.findViewById(R.id.imageButton8);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fme = getFragmentManager();
                FragmentTransaction fte = fme.beginTransaction();
                android.support.v4.app.Fragment fragment2=null;
                fragment2 = new Login();
                fte.replace(R.id.fr_place, fragment2);
                fte.commit();

            }
        });

        return v;

    }

}
