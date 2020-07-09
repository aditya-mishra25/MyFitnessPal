package com.example.myfitnesspal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavigation();
    }

    private void setUpNavigation() {
        navigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment= (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        NavigationUI.setupWithNavController(navigationView,navHostFragment.getNavController());

    }
}