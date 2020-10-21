package com.example.myfitnesspal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link diet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class diet<cal_db> extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    PieChart pieChart,pie2,pie3,pie4;
    String[] basic={"Roti","Naan","Garlic Naam"};
    //    String[] p;
    AutoCompleteTextView ac;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    CardView cv;
    PieDataSet dataSet1;
    String chartValue;
    EditText quan;
    CardView v2;
    LineChart lineChart;
    SeekBar sb;
    Button plus,minus,add,cancel,chart;
    ScrollView sc;
    final String uid = DBconnection();
    int cal;
    int cal_empty;
    int cal_target;
    int pro;
    int pro_target;
    int pro_empty;
    int fat;
    int fat_empty;
    int fat_target;
    int carbo;
    int carbo_empty;
    int carbo_target;

    //variables created to fetch values from database eg()
    public int cal_db;
    int pro_db;
    int fat_db;
    int carbo_db;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public diet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment diet.
     */
    // TODO: Rename and change types and number of parameters
    public static diet newInstance(String param1, String param2) {
        diet fragment = new diet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        final String uid = DBconnection();
        cv =  getView().findViewById(R.id.cardView);
//        cv.setVisibility(View.GONE);
        //scrollview
        sc =getView().findViewById(R.id.scroll);
        //PieChart
//        b1=getView().findViewById(R.id.button);
        pieChart = getView().findViewById(R.id.pie1);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10,10,10,5);
        pieChart.setDragDecelerationFrictionCoef(0f);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setHoleRadius(95f);
//        pieChart.setTransparentCircleRadius(64f);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(74f);
        pieChart.setHoleRadius(90f);
        pieChart.setTransparentCircleRadius(64f);
//        pieChart.setEntryLabelColor(Color.BLACK);


        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(80));
        yValues.add(new PieEntry(20));
//        yValues.add(new PieEntry(30,"Carbohydrates"));

        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Calories");

        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
//        dataSet.setColors(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));
//        dataSet.setColor(255,67);
        int x=200;
        int y=800;

        pieChart.setCenterText("Diet Tracker");
        pieChart.setCenterTextSize(25f);
        dataSet.setColors(Color.rgb(255,187,0),Color.rgb(0,120,237),Color.rgb(255,120,0));
        pieChart.getLegend().setEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setCenterTextSize(10f);
        dataSet.setColors(Color.rgb(255,187,0),Color.rgb(237,236,237));
        pieChart.getLegend().setEnabled(false);

        PieData pieData = new PieData((dataSet));
        dataSet.setValueTextSize(0f);
        dataSet.setValueTextColor(Color.LTGRAY);

        pieChart.setData(pieData);

//2nd piechart
        pie2 = getView().findViewById(R.id.pie2);
        pie2.setUsePercentValues(false);
        pie2.getDescription().setEnabled(false);
        pie2.setExtraOffsets(10,10,10,5);
        pie2.setDragDecelerationFrictionCoef(0f);
        pie2.setTouchEnabled(false);
        pie2.setDrawHoleEnabled(true);
        pie2.setHoleColor(Color.WHITE);
        pie2.setHoleRadius(90f);
        pie2.setTransparentCircleRadius(64f);
        pie2.animateY(1000, Easing.EaseInOutCubic);
        ArrayList<PieEntry> yValues1 = new ArrayList<>();
        yValues1.add(new PieEntry(40));
        yValues1.add(new PieEntry(60));
        dataSet1 = new PieDataSet(yValues, "Proteins");
        dataSet1.setSliceSpace(2f);
        dataSet1.setSelectionShift(5f);
//        pie2.setCenterText("Proteins");

        int x1=200;
        int y1=800;

        pie2.setCenterTextSize(10f);
        dataSet1.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
        pie2.getLegend().setEnabled(false);

        PieData pieData1 = new PieData((dataSet1));
        dataSet1.setValueTextSize(0f);
        dataSet1.setValueTextColor(Color.BLACK);

        pie2.setData(pieData1);

        //3rd piechart
        pie3 = getView().findViewById(R.id.pie3);
        pie3.setUsePercentValues(false);
        pie3.getDescription().setEnabled(false);
        pie3.setExtraOffsets(10,10,10,5);
        pie3.setDragDecelerationFrictionCoef(0f);
        pie3.setTouchEnabled(false);
        pie3.setDrawHoleEnabled(true);
        pie3.setHoleColor(Color.WHITE);
        pie3.setHoleRadius(90f);
//        pie3.setCenterText("Fats");
        pie3.setTransparentCircleRadius(64f);
        pie3.animateY(1000, Easing.EaseInOutCubic);
        ArrayList<PieEntry> yValues11 = new ArrayList<>();
        yValues11.add(new PieEntry(25));
        yValues11.add(new PieEntry(75));
        PieDataSet dataSet11 = new PieDataSet(yValues, "Fats");
        dataSet11.setSliceSpace(2f);
        dataSet11.setSelectionShift(5f);

        int x2=200;
        int y2=800;
//        pie3.setCenterText("Fats"+"\n Consumed - "+x2+"\n left - "+y2);
        pie3.setCenterTextSize(10f);
        dataSet11.setColors(Color.rgb(191,0,254),Color.rgb(237,236,237));
        pie3.getLegend().setEnabled(false);

        PieData pieData11 = new PieData((dataSet11));
        dataSet11.setValueTextSize(0f);
        dataSet11.setValueTextColor(Color.BLACK);

        pie3.setData(pieData11);

        //4th piechart
        pie4 = getView().findViewById(R.id.pie4);
        pie4.setUsePercentValues(false);
        pie4.getDescription().setEnabled(false);
        pie4.setExtraOffsets(10,10,10,5);
        pie4.setDragDecelerationFrictionCoef(0f);
        pie4.setTouchEnabled(false);
        pie4.setDrawHoleEnabled(true);
        pie4.setHoleColor(Color.WHITE);
        pie4.setHoleRadius(90f);
//        pie4.setCenterText("Carbohydrates");
        pie4.setTransparentCircleRadius(64f);
        pie4.animateY(1000, Easing.EaseInOutCubic);
        ArrayList<PieEntry> yValues111 = new ArrayList<>();
        yValues111.add(new PieEntry(60));
        yValues111.add(new PieEntry(40));
        PieDataSet dataSet111 = new PieDataSet(yValues, "Carbohydrates");
        dataSet111.setSliceSpace(2f);
        dataSet111.setSelectionShift(5f);

        int x3=200;
        int y3=800;
//        pie4.setCenterText("Carbohydrates"+"\n Consumed - "+x3+"\n left - "+y3);
        pie4.setCenterTextSize(10f);
        dataSet111.setColors(Color.rgb(191,0,0),Color.rgb(237,236,237));
        pie4.getLegend().setEnabled(false);

        PieData pieData111 = new PieData((dataSet111));
        dataSet111.setValueTextSize(0f);
        dataSet111.setValueTextColor(Color.BLACK);

        pie4.setData(pieData111);





//        DbCode
//        Calories

        final String date = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date);
        final DatabaseReference myRef = firebaseDatabase.getReference().child("Diet").child(uid).child("calories");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cal_target=snapshot.child("target").getValue(Integer.class);
                        cal_target=3000;
                        cal=snapshot.child(date).getValue(Integer.class);
                        cal_empty=cal_target-cal;

                        Log.d("fetched2",String.valueOf(cal));
                        final ArrayList<PieEntry> yValues = new ArrayList<>();
                        yValues.add(new PieEntry(cal));
                        yValues.add(new PieEntry(cal_empty));
                        pieChart.animateY(1000, Easing.EaseInOutCubic);

                        PieDataSet dataSet = new PieDataSet(yValues, "Calories");
                        dataSet.setSliceSpace(2f);
                        dataSet.setSelectionShift(5f);
//                        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
                        dataSet.setColors(Color.rgb(255,187,0),Color.rgb(237,236,237));
                        pieChart.getLegend().setEnabled(false);
                        pieChart.setCenterText("Calories"+"\n Consumed - "+cal+"\n left - "+cal_empty);
                        PieData pieData = new PieData((dataSet));
                        dataSet.setValueTextSize(0f);
                        dataSet.setValueTextColor(Color.LTGRAY);

                        pieChart.setData(pieData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });


//        proteins
        final String date1 = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date1);
        final DatabaseReference myRef1 = firebaseDatabase.getReference().child("Diet").child(uid).child("proteins");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cal_target=snapshot.child("target").getValue(Integer.class);
                        pro_target=3000;
//                        pro=snapshot.child(date).getValue(Integer.class);
                        pro_empty=pro_target-pro;

                        Log.d("fetched2",String.valueOf(pro));
                        final ArrayList<PieEntry> yValues = new ArrayList<>();
                        yValues.add(new PieEntry(pro));
                        yValues.add(new PieEntry(pro_empty));
                        pie2.animateY(1000, Easing.EaseInOutCubic);

                        PieDataSet dataSet = new PieDataSet(yValues, "Proteins");
                        dataSet.setSliceSpace(2f);
                        dataSet.setSelectionShift(5f);
//                        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
//                        dataSet.setColors(Color.rgb(255,187,0),Color.rgb(237,236,237));
                        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
                        pie2.getLegend().setEnabled(false);
                        pie2.setCenterText("Proteins"+"\n Consumed - "+pro+"\n left - "+pro_empty);
                        PieData pieData = new PieData((dataSet));
                        dataSet.setValueTextSize(0f);
                        dataSet.setValueTextColor(Color.LTGRAY);

                        pie2.setData(pieData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });



//        fats
        final String date2 = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date2);
        final DatabaseReference myRef2 = firebaseDatabase.getReference().child("Diet").child(uid).child("fats");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cal_target=snapshot.child("target").getValue(Integer.class);
                        fat_target=3000;
//                        pro=snapshot.child(date).getValue(Integer.class);
                        fat_empty=pro_target-pro;

                        Log.d("fetched2",String.valueOf(fat));
                        final ArrayList<PieEntry> yValues = new ArrayList<>();
                        yValues.add(new PieEntry(fat));
                        yValues.add(new PieEntry(fat_empty));
                        pie3.animateY(1000, Easing.EaseInOutCubic);

                        PieDataSet dataSet = new PieDataSet(yValues, "Fats");
                        dataSet.setSliceSpace(2f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setColors(Color.rgb(191,0,254),Color.rgb(237,236,237));
                        pie3.getLegend().setEnabled(false);
                        pie3.setCenterText("Fats"+"\n Consumed - "+fat+"\n left - "+fat_empty);
                        PieData pieData = new PieData((dataSet));
                        dataSet.setValueTextSize(0f);
                        dataSet.setValueTextColor(Color.LTGRAY);

                        pie3.setData(pieData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });




//        carbo
        final String date3 = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date3);
        final DatabaseReference myRef3 = firebaseDatabase.getReference().child("Diet").child(uid).child("carbs");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cal_target=snapshot.child("target").getValue(Integer.class);
                        carbo_target=3000;
//                        pro=snapshot.child(date).getValue(Integer.class);
                        carbo_empty=pro_target-pro;

                        Log.d("fetched2",String.valueOf(carbo));
                        final ArrayList<PieEntry> yValues = new ArrayList<>();
                        yValues.add(new PieEntry(carbo));
                        yValues.add(new PieEntry(carbo_empty));
                        pie4.animateY(1000, Easing.EaseInOutCubic);

                        PieDataSet dataSet = new PieDataSet(yValues, "Carbs");
                        dataSet.setSliceSpace(2f);
                        dataSet.setSelectionShift(5f);
//                        dataSet.setColors(Color.rgb(191,0,254),Color.rgb(237,236,237));
                        dataSet.setColors(Color.rgb(191,0,0),Color.rgb(237,236,237));
                        pie4.getLegend().setEnabled(false);
                        pie4.setCenterText("Carbs"+"\n Consumed - "+carbo+"\n left - "+carbo_empty);
                        PieData pieData = new PieData((dataSet));
                        dataSet.setValueTextSize(0f);
                        dataSet.setValueTextColor(Color.LTGRAY);

                        pie4.setData(pieData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });
















        ac=( AutoCompleteTextView )getView().findViewById(R.id.autoCompleteTextView2);
        final ArrayAdapter<String> ad=new ArrayAdapter(getActivity(),android.R.layout.select_dialog_item,basic);
        ac.setThreshold(1);
        ac.setAdapter(ad);
        chartValue= ac.getText().toString();
        ac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String food=ac.getText().toString();
                Log.d("Food",food);
                // code edited
                DatabaseReference dietDetails = firebaseDatabase.getReference("Diet Details").child(food);
                dietDetails.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cal_db = snapshot.child("calories").getValue(Integer.class);
                        pro_db = snapshot.child("proteins").getValue(Integer.class);
                        carbo_db = snapshot.child("carbs").getValue(Integer.class);
                        fat_db = snapshot.child("fats").getValue(Integer.class);

                        System.out.println(cal_db+" "+pro_db+" "+carbo_db+" "+fat_db);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                Log.d("Claories DD", String.valueOf(cal_db));
                //
                final ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.add_diet_quant, viewGroup, false);

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();


                add = dialogView.findViewById(R.id.add);
//                cancel = dialogView.findViewById(R.id.cancel);
                quan=dialogView.findViewById(R.id.quan);
                plus = dialogView.findViewById(R.id.plus);
                minus = dialogView.findViewById(R.id.minus);
                plus.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View view) {
                        int p=Integer.parseInt(quan.getText().toString());
                        quan.clearComposingText();
                        int x=p+1;
                        quan.setText(""+x);

                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View view) {
                        int p=Integer.parseInt(quan.getText().toString());
                        quan.clearComposingText();
                        int x=p-1;
                        if (x<1) {
                        Toast.makeText(getActivity(),"quantity cannot be less than zero",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            quan.setText("" + x);
                        }
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String qty = quan.getText().toString();
                        if(qty.equals("")){
                            Toast.makeText(getActivity(),"Please input some value",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d("Add",qty+"ml");
                            int qtyy=Integer.parseInt(qty);
//                            add values
                            updateCalories(qtyy);
                            alertDialog.dismiss();//change code here
                        }

                        if(qty.equals("")){
                            Toast.makeText(getActivity(),"Please input some value",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d("Add",qty+"ml");
                            int qtyy=Integer.parseInt(qty);
                            updateproteins(qtyy);
                            alertDialog.dismiss();//change code here
                        }
                        if(qty.equals("")){
                            Toast.makeText(getActivity(),"Please input some value",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d("Add",qty+"ml");
                            int qtyy=Integer.parseInt(qty);
                            updatefats(qtyy);
                            alertDialog.dismiss();//change code here
                        }
                        if(qty.equals("")){
                            Toast.makeText(getActivity(),"Please input some value",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d("Add",qty+"ml");
                            int qtyy=Integer.parseInt(qty);
                            updatecarbs(qtyy);
                            alertDialog.dismiss();//change code here
                        }
                    }
                });
//                cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog.dismiss();
//                    }
//                });
            }
        });




//
//
//        addFood=getView().findViewById(R.id.button3);
//
//
        chart=getView().findViewById(R.id.chart);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String food=ac.getText().toString();
                final ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.foodchart, viewGroup, false);

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });






   }
    //  Date()
    public String date(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

//DBConnection


    public String DBconnection(){
        firebaseAuth = FirebaseAuth.getInstance();
        final String currentuser  = firebaseAuth.getUid();
        Log.d("UID",currentuser);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = firebaseDatabase.getReference("Diet").child(currentuser).child("calories");
        final DatabaseReference ref1 = firebaseDatabase.getReference("Diet").child(currentuser).child("proteins");
        final DatabaseReference ref2 = firebaseDatabase.getReference("Diet").child(currentuser).child("fats");
        final DatabaseReference ref3 = firebaseDatabase.getReference("Diet").child(currentuser).child("carbs");
        final String date = date();
//        calories
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(date)){
                    cal = snapshot.child(date).getValue(Integer.class);
                }
                else{
                    cal = 0;
                    ref.child(date).setValue(cal);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Proetins
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(date)){
                    pro = snapshot.child(date).getValue(Integer.class);
                }
                else{
                    pro = 0;
                    ref1.child(date).setValue(pro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        fats
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(date)){
                    fat = snapshot.child(date).getValue(Integer.class);
                }
                else{
                    fat = 0;
                    ref2.child(date).setValue(fat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        carbs
        ref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(date)){
                    carbo = snapshot.child(date).getValue(Integer.class);
                }
                else{
                    carbo = 0;
                    ref3.child(date).setValue(carbo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return currentuser;
    }

//calories

    public void updateCalories(int qty){
//        final String uid = DBconnection();

        ArrayList<PieEntry> values = new ArrayList<>();
        int cal1 = qty;
        final String date = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date);
        DatabaseReference myRef = firebaseDatabase.getReference().child("Diet").child(uid).child("calories");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cal = snapshot.child(date).getValue(Integer.class);
//                cal_target = snapshot.child("target").getValue(Integer.class);
                cal_target=2000;
                Log.d("fetched",String.valueOf(cal));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });
        cal =cal+cal1;

        if(qty>0){
            myRef.child(date).setValue(cal);
        }
        values.add(new PieEntry(cal));
        values.add(new PieEntry(cal_empty));
        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(values, "Calories");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
//        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
        dataSet.setColors(Color.rgb(255,187,0),Color.rgb(237,236,237));
        pieChart.getLegend().setEnabled(false);

        PieData pieData = new PieData((dataSet));
        dataSet.setValueTextSize(0f);
        dataSet.setValueTextColor(Color.LTGRAY);
        pieChart.setData(pieData);
    }


//Proteins
    public void updateproteins(int qty){


        ArrayList<PieEntry> values = new ArrayList<>();
        int cal11 = qty;
        final String date = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date);
        DatabaseReference myRef = firebaseDatabase.getReference().child("Diet").child(uid).child("proteins");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pro = snapshot.child(date).getValue(Integer.class);
//                pro_target = snapshot.child("target").getValue(Integer.class);
                pro_target=2000;
                Log.d("fetched",String.valueOf(pro));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });
        pro =pro+cal11;

        if(qty>0){
            myRef.child(date).setValue(pro);
        }
        values.add(new PieEntry(pro));
        values.add(new PieEntry(pro_empty));
        pie2.animateY(1000, Easing.EaseInOutCubic);

//        PieDataSet dataSet = new PieDataSet(values, "Proteins");
        dataSet1.setSliceSpace(2f);
        dataSet1.setSelectionShift(5f);
//        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
//        dataSet.setColors(Color.rgb(255,187,0),Color.rgb(237,236,237));
        pie2.getLegend().setEnabled(false);
        dataSet1.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));

        PieData pieData = new PieData((dataSet1));
        dataSet1.setValueTextSize(0f);
        dataSet1.setValueTextColor(Color.LTGRAY);
        pie2.setData(pieData);
    }



//fats

    public void updatefats(int qty){
//        final String uid = DBconnection();

        ArrayList<PieEntry> values = new ArrayList<>();
        int cal1 = qty;
        final String date = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date);
        DatabaseReference myRef = firebaseDatabase.getReference().child("Diet").child(uid).child("fats");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fat = snapshot.child(date).getValue(Integer.class);
//                cal_target = snapshot.child("target").getValue(Integer.class);
                fat_target=2000;
                Log.d("fetched",String.valueOf(fat));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });
        fat =fat+cal1;

        if(qty>0){
            myRef.child(date).setValue(fat);
        }
        values.add(new PieEntry(fat));
        values.add(new PieEntry(fat_empty));
        pie3.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(values, "Fats");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.rgb(191,0,254),Color.rgb(237,236,237));
        pie3.getLegend().setEnabled(false);

        PieData pieData = new PieData((dataSet));
        dataSet.setValueTextSize(0f);
        dataSet.setValueTextColor(Color.LTGRAY);
        pie3.setData(pieData);
    }


//carbs
    public void updatecarbs(int qty){
//        final String uid = DBconnection();

        ArrayList<PieEntry> values = new ArrayList<>();
        int cal1 = qty;
        final String date = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date);
        DatabaseReference myRef = firebaseDatabase.getReference().child("Diet").child(uid).child("carbs");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carbo = snapshot.child(date).getValue(Integer.class);
//                cal_target = snapshot.child("target").getValue(Integer.class);
                carbo_target=2000;
                Log.d("fetched",String.valueOf(carbo));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });
        carbo =carbo+cal1;

        if(qty>0){
            myRef.child(date).setValue(carbo);
        }
        values.add(new PieEntry(carbo));
        values.add(new PieEntry(carbo_empty));
        pie4.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(values, "Carbs");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
//        dataSet.setColors(Color.rgb(191,0,254),Color.rgb(237,236,237));
        dataSet.setColors(Color.rgb(191,0,0),Color.rgb(237,236,237));
        pie4.getLegend().setEnabled(false);

        PieData pieData = new PieData((dataSet));
        dataSet.setValueTextSize(0f);
        dataSet.setValueTextColor(Color.LTGRAY);
        pie4.setData(pieData);
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
        return inflater.inflate(R.layout.fragment_diet, container, false);
    }
}





