package com.example.myfitnesspal;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link water#newInstance} factory method to
 * create an instance of this fragment.
 */
public class water extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    int water;
    int empty;
    public int waterReceived;
    boolean state = false;
    PieChart pieChart;
    LineChart lineChart;
    CardView cv;
    ScrollView sc;
    Button button, add, cncl;
    ImageButton glass, bottle, bigBottle;
    FloatingActionButton floatingActionButton;
    EditText water_quantity;
    ArrayList<Entry> yvalues;
    int targetSet;
    TextView achived, target, updateTarget;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    HashMap<String, Integer> arr = new HashMap<String, Integer>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public water() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment water.
     */
    // TODO: Rename and change types and number of parameters
    public static water newInstance(String param1, String param2) {
        water fragment = new water();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TextViews
        achived = (TextView) getView().findViewById(R.id.achived);
        target = (TextView) getView().findViewById(R.id.target);
        updateTarget = (TextView)  getView().findViewById(R.id.updateTarget);
        //cardview
        firebaseAuth = FirebaseAuth.getInstance();
        final String uid = DBconnection();
        cv = (CardView) getView().findViewById(R.id.card);
        cv.setVisibility(View.GONE);
        //scrollview
        sc = (ScrollView) getView().findViewById(R.id.scroll);
        //PieChart
        pieChart = (PieChart) getView().findViewById(R.id.pie);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10,10,10,5);
        pieChart.setDragDecelerationFrictionCoef(0f);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(74f);
        pieChart.setCenterText("Water Tracker");
        pieChart.setCenterTextSize(25f);

        final String date = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date);
        final DatabaseReference myRef = firebaseDatabase.getReference().child("Water").child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                water = snapshot.child(date).getValue(Integer.class);
//                Log.d("fetched",String.valueOf(water));
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        targetSet = snapshot.child("target").getValue(Integer.class);
                        water = snapshot.child(date).getValue(Integer.class);
                        target.setText(String.valueOf(targetSet));
                        achived.setText(String.valueOf(water));

                        target.setText(String.valueOf(targetSet));
                        empty = targetSet-water; // fetch the target value for empty

                        Log.d("fetched2",String.valueOf(water));
                        final ArrayList<PieEntry> yValues = new ArrayList<>();
                        yValues.add(new PieEntry(water));
                        yValues.add(new PieEntry(empty));
                        pieChart.animateY(1000, Easing.EaseInOutCubic);

                        PieDataSet dataSet = new PieDataSet(yValues, "Water Tracker");
                        dataSet.setSliceSpace(2f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
                        pieChart.getLegend().setEnabled(false);

                        PieData pieData = new PieData((dataSet));
                        dataSet.setValueTextSize(0f);
                        dataSet.setValueTextColor(Color.LTGRAY);

                        pieChart.setData(pieData);

                        if (water == targetSet){
                            glass.setEnabled(false);
                            bottle.setEnabled(false);
                            bigBottle.setEnabled(false);
                        }
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

//        water = 0;
//        empty = 5000-water; // fetch the target value for empty
//        Log.d("fetched2",String.valueOf(water));
//        final ArrayList<PieEntry> yValues = new ArrayList<>();
//        yValues.add(new PieEntry(water));
//        yValues.add(new PieEntry(empty));
//        pieChart.animateY(1000, Easing.EaseInOutCubic);
//
//        PieDataSet dataSet = new PieDataSet(yValues, "Water Tracker");
//        dataSet.setSliceSpace(2f);
//        dataSet.setSelectionShift(5f);
//        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
//        pieChart.getLegend().setEnabled(false);
//
//        PieData pieData = new PieData((dataSet));
//        dataSet.setValueTextSize(0f);
//        dataSet.setValueTextColor(Color.LTGRAY);
//
//        pieChart.setData(pieData);

        //Line Graph
        /////////////////////////////////////////////////////////////////////////
        lineChart = (LineChart) getView().findViewById(R.id.Linechart);
        final Query chatQuery = myRef.orderByKey().limitToLast(8);
        chatQuery.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        arr = (HashMap<String, Integer>) dataSnapshot.getValue();
                        arr.remove("00 toggle");
                        arr.remove("target");
                        if(arr.size()>2){
                            TreeMap<String, Integer> sorted = new TreeMap<>();
                            sorted.putAll(arr);
                            for(Map.Entry<String, Integer> entry:sorted.entrySet()){
                                System.out.println("Key="+entry.getKey()+", Value="+entry.getValue());
                            }
                            System.out.println("TreeMap "+sorted);
                            Log.d("array", String.valueOf(dataSnapshot.getValue()));
                            String X[] = sorted.keySet().toArray(new String[0]);
                            Collection<Integer> Y =sorted.values();
                            Long[] y = Y.toArray(new Long[0]);
                            System.out.println("hashmap"+X[0]+" "+X[1]);
                            System.out.println("hashmap"+y[0]+" "+y[1]);

//                        lineChart = (LineChart) getView().findViewById(R.id.Linechart);

                            XAxis xAxis = lineChart.getXAxis();
                            YAxis yAxis = lineChart.getAxisLeft();
                            XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
                            xAxis.setPosition(position);
                            xAxis.enableGridDashedLine(2f, 7f, 0f);
                            xAxis.setAxisMaximum(5f);
                            xAxis.setAxisMinimum(0f);
                            xAxis.setLabelCount(6, true);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setGranularity(7f);
//                        xAxis.setLabelRotationAngle(315f);

                            lineChart.setDragEnabled(true);
                            lineChart.setScaleEnabled(false);

                            yvalues = new ArrayList<>();
                            for(int i=0; i<(X.length-1); i++){
                                yvalues.add(new Entry(i,y[i]));
                            }
//                        yvalues.add(new Entry(0,60f));

                            LineDataSet set1 = new LineDataSet(yvalues,"Water Intake Level");
                            set1.setFillAlpha(110);
                            ArrayList<LineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(set1);

                            lineChart.getDescription().setEnabled(true);
                            Description description = new Description();

                            description.setText("Last 7 Days");
                            description.setTextSize(15f);

                            lineChart.setDescription(description);

                            LineData data = new LineData(set1);
                            lineChart.setData(data);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("array", "Empty");
                }
            });

        //button
        button = (Button) getView().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state==false){
                    cv.setVisibility(View.VISIBLE);
                    state = true;
                    sc.scrollTo(0,sc.getBottom());//need to scroll down after onclick!
                }
                else{
                    cv.setVisibility(View.GONE);
                    state=false;
                }
            }
        });
        glass = (ImageButton) getView().findViewById(R.id.glass);
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWaterLevel(250);
            }
        });
        bottle = (ImageButton) getView().findViewById(R.id.smallBottle);
        bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWaterLevel(500);
            }
        });
        bigBottle = (ImageButton) getView().findViewById(R.id.bigBottle);
        bigBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWaterLevel(750);
            }
        });
        floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.custom);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("custom","___ml");
//                final Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.custom_btn_dialouge);
//                dialog.show();
                final ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_btn_dialouge, viewGroup, false);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                add = dialogView.findViewById(R.id.add);
                cncl = dialogView.findViewById(R.id.del);
                water_quantity = dialogView.findViewById(R.id.qty);
//                Log.d("quantity",qty);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String qty = water_quantity.getText().toString();
                        if(qty.equals("")){
                            Toast.makeText(getActivity(),"Please input some value",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d("Add",qty+"ml");
                            if(Integer.parseInt(qty)>targetSet-water){
                                updateWaterLevel(targetSet-water);
                                alertDialog.dismiss();
                            }
                            else{
                                updateWaterLevel(Integer.parseInt(qty));
                                alertDialog.dismiss();
                            }
                        }
                    }
                });
                cncl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        updateTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.update_target, viewGroup, false);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                add = dialogView.findViewById(R.id.add);
                cncl = dialogView.findViewById(R.id.del);
                water_quantity = dialogView.findViewById(R.id.qty);
//                Log.d("quantity",qty);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String qty = water_quantity.getText().toString();
                        if(qty.equals("")){
                            Toast.makeText(getActivity(),"Please input some value",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Log.d("Add",qty+"ml");
                            myRef.child("target").setValue(Integer.parseInt(qty));
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String tar = snapshot.child("target").getValue().toString();
                                    water = snapshot.child(date).getValue(Integer.class);
                                    updateWaterLevel(0);
                                    targetSet = Integer.parseInt(tar);
                                    target.setText(String.valueOf(targetSet));
                                    Log.d("Updated value", String.valueOf(targetSet));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            alertDialog.dismiss();//change code here
                        }
                    }
                });
                cncl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }

        });
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
        return inflater.inflate(R.layout.fragment_water, container, false);

    }
    public String DBconnection(){
        firebaseAuth = FirebaseAuth.getInstance();
        final String currentuser  = firebaseAuth.getUid();
        Log.d("UID",currentuser);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = firebaseDatabase.getReference("Water").child(currentuser);
        final String date = date();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(date)){
                    water = snapshot.child(date).getValue(Integer.class);
                }
                else{
                    water = 0;
                    ref.child(date).setValue(water);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         // need to check the initialization.
        return currentuser;
    }
    public String date(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }
    public void updateWaterLevel(int qty){
        final String uid = DBconnection();
        Log.d("Glass", String.valueOf(qty));
        ArrayList<PieEntry> values = new ArrayList<>();
        int cal = qty;
        final String date = date();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.d("DATE",date);
        DatabaseReference myRef = firebaseDatabase.getReference().child("Water").child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                water = snapshot.child(date).getValue(Integer.class);
                targetSet = snapshot.child("target").getValue(Integer.class);
                Log.d("fetched",String.valueOf(water));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Sorry there was some problem retrieving the data",Toast.LENGTH_LONG).show();
            }
        });
        water =water+cal;
//        empty =empty-cal;

//        achived  = (TextView) getView().findViewById(R.id.achived);
        achived.refreshDrawableState();
        achived.setText(String.valueOf(water));
        target.setText(String.valueOf((targetSet)));
//        target.setText(String.valueOf(empty));
        if(qty>0){
            myRef.child(date).setValue(water);
        }
        values.add(new PieEntry(water));
        values.add(new PieEntry(empty));
        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(values, "Water Tracker");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.rgb(0,191,254),Color.rgb(237,236,237));
        pieChart.getLegend().setEnabled(false);

        PieData pieData = new PieData((dataSet));
        dataSet.setValueTextSize(0f);
        dataSet.setValueTextColor(Color.LTGRAY);
        pieChart.setData(pieData);

        Log.d("target", String.valueOf(targetSet));
        Log.d("water", String.valueOf(water));

        if (targetSet-water < 250){
            glass.setEnabled(false);
            bottle.setEnabled(false);
            bigBottle.setEnabled(false);
        }
        else if(targetSet-water < 500){
            glass.setEnabled(true);
            bottle.setEnabled(false);
            bigBottle.setEnabled(false);
        }
        else if(targetSet-water < 700){
            glass.setEnabled(true);
            bottle.setEnabled(true);
            bigBottle.setEnabled(false);
        }
        else{
            glass.setEnabled(true);
            bottle.setEnabled(true);
            bigBottle.setEnabled(true);
        }
    }
//////////////////////////////////////date
//    static class SortByDate implements Comparator<DateItem> {
//        @Override
//        public int compare(DateItem a, DateItem b) {
//            return a.datetime.compareTo(b.datetime);
//        }
//    }
//    static class DateItem {
//        String datetime;
//
//        DateItem(String date) {
//            this.datetime = date;
//        }
//    }
////////////////////////////////////
}