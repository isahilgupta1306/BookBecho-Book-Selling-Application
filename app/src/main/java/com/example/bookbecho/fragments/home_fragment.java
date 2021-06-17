package com.example.bookbecho.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookbecho.R;
import com.example.bookbecho.adapters.productAdapterRV;
import com.example.bookbecho.models.productDataModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fragment extends Fragment {



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //My Declarations
    RecyclerView recyclerView;
    ArrayList<productDataModel> productDataHolder;
    String dummyString;
    productAdapterRV adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    public home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_fragment newInstance(String param1, String param2) {
        home_fragment fragment = new home_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.product_rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //initializing new dummy model

//        productDataModel obj1 = new productDataModel(R.drawable.dummy_book , "HC Verma" , dummyString , "250");
//        productDataHolder.add(obj1);
//
//        productDataModel obj2 = new productDataModel(R.drawable.dummy_book , "HC Verma" , dummyString , "250");
//        productDataHolder.add(obj2);
//
//        productDataModel obj3 = new productDataModel(R.drawable.dummy_book , "HC Verma" , dummyString , "250");
//        productDataHolder.add(obj3);
//
//        productDataModel obj4 = new productDataModel(R.drawable.dummy_book , "HC Verma" , dummyString , "250");
//        productDataHolder.add(obj4);
//
//        productDataModel obj5 = new productDataModel(R.drawable.dummy_book , "HC Verma" , dummyString , "250");
//        productDataHolder.add(obj5);



        FirebaseRecyclerOptions<productDataModel> prodData =
                new FirebaseRecyclerOptions.Builder<productDataModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), productDataModel.class)
                        .build();

        adapter = new productAdapterRV(prodData);
        recyclerView.setAdapter(adapter);


        return view;

    }




}