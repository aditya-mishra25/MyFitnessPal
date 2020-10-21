package com.example.myfitnesspal;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import static android.graphics.Color.rgb;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    TextView name,height,weight,bmi,age;
    FirebaseAuth auth;
    FirebaseDatabase db;
    CardView card;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (TextView) getView().findViewById(R.id.name);
        height = (TextView) getView().findViewById(R.id.height);
        weight = (TextView) getView().findViewById(R.id.weight);
        bmi =(TextView) getView().findViewById(R.id.bmi);
        age = (TextView) getView().findViewById(R.id.age);
        card = (CardView) getView().findViewById(R.id.card);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();
        DatabaseReference user = db.getReference("Users").child(uid);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String n = snapshot.child("name").getValue().toString();
                name.setText(n);
                String h = snapshot.child("height").getValue().toString();
                height.setText(h+" "+"cm");
                String w = snapshot.child("weight").getValue().toString();
                weight.setText(w+" "+"kgs");
                String a = snapshot.child("age").getValue().toString();
                age.setText(a+" "+"years");
                String b = snapshot.child("bmi").getValue().toString();
                int bmii = Integer.parseInt(b);
                if(bmii<19){
                    bmi.setText("Under-weight");
                    card.setCardBackgroundColor(rgb(0,177,240));
                }
                else if(bmii>=19 & bmii<25){
                    bmi.setText("Healthy");
                    card.setCardBackgroundColor(rgb(146,209,79));
                }
                else if(bmii>=25 & bmii<30){
                    bmi.setText("Over-weight");
                    card.setCardBackgroundColor(rgb(255,192,0));
                }
                else if(bmii>=30 & bmii<40){
                    bmi.setText("Obese");
                    card.setCardBackgroundColor(rgb(236,128,5));
                }
                else if(bmii>=40){
                    bmi.setText("Extremely-obese");
                    card.setCardBackgroundColor(rgb(254,0,0));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}