package com.example.myfitnesspal;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
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
 * Use the {@link diet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class diet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    PieChart pieChart;
    String[] basic={"Roti","Naan","Garlic Naam"};
    //    String[] p;
    AutoCompleteTextView ac;
    CardView cv;
    String chartValue;
    EditText quan;
    CardView v2;
    LineChart lineChart;
    SeekBar sb;
    Button plus,minus,add,cancel,chart;
    ScrollView sc;
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

        cv =  getView().findViewById(R.id.cardView);
//        cv.setVisibility(View.GONE);
        //scrollview
        sc =getView().findViewById(R.id.scroll);
        //PieChart
//        b1=getView().findViewById(R.id.button);
        pieChart = getView().findViewById(R.id.pie);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10,10,10,5);
        pieChart.setDragDecelerationFrictionCoef(0f);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setHoleRadius(95f);
//        pieChart.setTransparentCircleRadius(64f);
        pieChart.setHoleRadius(59f);
        pieChart.setTransparentCircleRadius(64f);
//        pieChart.setEntryLabelColor(Color.BLACK);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(50,"Protein"));
        yValues.add(new PieEntry(20,"Fats"));
        yValues.add(new PieEntry(30,"Carbohydrates"));

        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "Diet Tracker");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
//        dataSet.setColors(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));
//        dataSet.setColor(255,67);

        pieChart.setCenterText("Diet Tracker");
        pieChart.setCenterTextSize(25f);
        dataSet.setColors(Color.rgb(255,187,0),Color.rgb(0,120,237),Color.rgb(255,120,0));
        pieChart.getLegend().setEnabled(true);

        PieData pieData = new PieData((dataSet));
        dataSet.setValueTextSize(0f);
        dataSet.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);

        ac=( AutoCompleteTextView )getView().findViewById(R.id.autoCompleteTextView2);
        final ArrayAdapter<String> ad=new ArrayAdapter(getActivity(),android.R.layout.select_dialog_item,basic);
        ac.setThreshold(1);
        ac.setAdapter(ad);
        chartValue= ac.getText().toString();
        ac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String food=ac.getText().toString();
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





