package com.example.bookbecho.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookbecho.R;
import com.example.bookbecho.adapters.FavoritesAdapter;
import com.example.bookbecho.adapters.productAdapterRV;
import com.example.bookbecho.models.productDataModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteProd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteProd extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FavoritesAdapter adapter;


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

    public FavoriteProd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteProd.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteProd newInstance(String param1, String param2) {
        FavoriteProd fragment = new FavoriteProd();
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
        String uid = FirebaseAuth.getInstance().getUid();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_prod, container, false);
        recyclerView = view.findViewById(R.id.favorites_rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<productDataModel> prodData =
                new FirebaseRecyclerOptions.Builder<productDataModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Favorites").child(uid), productDataModel.class)
                        .build();

        adapter = new FavoritesAdapter(prodData);
        recyclerView.setAdapter(adapter);




        return view;
    }
}