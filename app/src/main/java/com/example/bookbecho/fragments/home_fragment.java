package com.example.bookbecho.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //My Declarations
    RecyclerView recyclerView;
    String dummyString;
    productAdapterRV adapter;
    LinearLayoutManager linearLayoutManager;
    SearchView searchView , newsearchView;



    @Override
    public void onCreateOptionsMenu( @NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
//        MenuItem item = menu.findItem(R.id.search);
//         searchView = (SearchView) item.getActionView();
//         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//             @Override
//             public boolean onQueryTextSubmit(String query) {
//                 processSearch(query);
//                 return false;
//             }
//
//             @Override
//             public boolean onQueryTextChange(String newText) {
//                 processSearch(newText);
//                 return false;
//             }
//         });

    }
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


        FirebaseRecyclerOptions<productDataModel> prodData =
                new FirebaseRecyclerOptions.Builder<productDataModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), productDataModel.class)
                        .build();

        adapter = new productAdapterRV(prodData);
        recyclerView.setAdapter(adapter);

        //for Search Module
        newsearchView = (SearchView)view.findViewById(R.id.new_search);
        newsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearchTitle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearchTitle(newText);
                return false;
            }
        });

        return view;

    }
    private void processSearchTitle(String query){
        FirebaseRecyclerOptions<productDataModel> prodData =
                new FirebaseRecyclerOptions.Builder<productDataModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products")
                                .orderByChild("title").startAt(query).endAt(query + "\uf8ff" ), productDataModel.class)
                        .build();

        adapter = new productAdapterRV(prodData);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    private void processSearchCollege(String query){
        FirebaseRecyclerOptions<productDataModel> prodData =
                new FirebaseRecyclerOptions.Builder<productDataModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products")
                                .orderByChild("title").startAt(query).endAt(query + "\uf8ff" ), productDataModel.class)
                        .build();

        adapter = new productAdapterRV(prodData);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }







}