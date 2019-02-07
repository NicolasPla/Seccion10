package com.example.seccion10.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.seccion10.Activities.Fragments.MapFragment;
import com.example.seccion10.Activities.Fragments.WelcomeFragment;
import com.example.seccion10.R;

public class MainActivity extends AppCompatActivity {

    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Para evitar que se vuelva a ejecutar si rota la pantalla//
        if (savedInstanceState == null) {
            currentFragment = new WelcomeFragment();
            changeFragment(currentFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_welcome:
                currentFragment = new WelcomeFragment();
                break;

            case R.id.menu_map:
                currentFragment = new MapFragment();
                break;
        }
        changeFragment(currentFragment);
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_contaier, fragment)
                .commit();

    }
}
