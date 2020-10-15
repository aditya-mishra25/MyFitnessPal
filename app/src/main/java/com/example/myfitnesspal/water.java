package com.example.myfitnesspal;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link water#newInstance} factory method to
 * create an instance of this fragment.
 */
public class water extends Fragment {

    boolean state = false;
    PieChart pieChart;
    LineChart lineChart;
    CardView cv;
    ScrollView sc;
    Button button, add, cncl;
    ImageButton glass, bottle, bigBottle;
    FloatingActionButton floatingActionButton;
    EditText water_quantity;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        //cardview
        cv = (CardView) getView().findViewById(R.id.card);
        cv.setVisibility(View.GONE);
        //scrollview
        sc = (ScrollView) getView().findViewById(R.id.scroll);
        //PieChart
        pieChart = (PieChart) getView().findViewById(R.id.pie1);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10,10,10,5);
        pieChart.setDragDecelerationFrictionCoef(0f);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(59f);
        pieChart.setTransparentCircleRadius(64f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(70));
        yValues.add(new PieEntry(40));
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

        //Line Graph
        lineChart = (LineChart) getView().findViewById(R.id.Linechart);
//        lineChart.setOnChartGestureListener(MainActivity.this);
//        lineChart.setOnChartValueSelectedListener(MainActivity.this);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        ArrayList<Entry> yvalues = new ArrayList<>();
        yvalues.add(new Entry(0,60f));
        yvalues.add(new Entry(1,50f));
        yvalues.add(new Entry(2,70f));
        yvalues.add(new Entry(3,30f));
        yvalues.add(new Entry(4,60f));
        yvalues.add(new Entry(5,55f));
        yvalues.add(new Entry(6,65f));

        LineDataSet set1 = new LineDataSet(yvalues,"Data Set 1");
        set1.setFillAlpha(110);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(set1);
        lineChart.setData(data);

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
                Log.d("Glass","250ml");
            }
        });
        bottle = (ImageButton) getView().findViewById(R.id.smallBottle);
        bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bottle","500ml");
            }
        });
        bigBottle = (ImageButton) getView().findViewById(R.id.bigBottle);
        bigBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BigBottle","750ml");
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
//    public void glass(View view){
//        Log.d("Glass","250ml");
//    }
//    public void smallBottle(View view){
//        Log.d("bottle","500ml");
//    }
//    public void bigBottle(View view){
//        Log.d("BigBottle","750ml");
//    }

}