package ru.mirea.tourist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.homeId);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
    }
    Home homeFragment = new Home();
    Search searchFragment = new Search();
    About aboutFragment = new About();
    Profile profileFragment = new Profile();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,aboutFragment).commit();
                return true;
            case R.id.homeId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                return true;
            case R.id.searchId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,searchFragment).commit();
                return true;
            case R.id.profileId:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
                return true;
        }
        return false;
    }
}
