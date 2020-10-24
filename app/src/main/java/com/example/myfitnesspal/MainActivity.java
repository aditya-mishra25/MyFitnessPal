package com.example.myfitnesspal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference fdb;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavigation();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser n = firebaseAuth.getCurrentUser();
        Log.d("current", String.valueOf(n));
        if(n==null){
            Intent intent = new Intent(this,LoginPage.class);
            startActivity(intent);
        }
        else{
                String uid = firebaseAuth.getUid();
                firebaseDatabase = FirebaseDatabase.getInstance();
                fdb = firebaseDatabase.getReference("Water").child(uid);

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    final NotificationChannel channel = new NotificationChannel("MyNotification","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
                    channel.enableVibration(true);
                    channel.enableLights(true);
                    final NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);

                    fdb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String value = snapshot.child("00 toggle").getValue().toString();
                            if (value == "True") {
                                notificationManager.createNotificationChannel(channel);
                            }
                            else{
                                notificationManager.deleteNotificationChannel("MyNotification");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                FirebaseMessaging.getInstance().subscribeToTopic("general")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = "Successful";
                                if (!task.isSuccessful()) {
                                    msg = "Failed";
                                }
                                Log.d("TAG", msg);
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
        }
    }

    private void setUpNavigation() {
        navigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment= (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        NavigationUI.setupWithNavController(navigationView,navHostFragment.getNavController());
    }
}