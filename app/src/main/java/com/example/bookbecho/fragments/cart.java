package com.example.bookbecho.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.bookbecho.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference database , cartList;
    String productDescription , productTitle , productPrice , productImageUrl;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public cart() {
        // Required empty public constructor
    }


    public static cart newInstance(String param1, String param2) {
        cart fragment = new cart();
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

//        String key = this.getArguments().getString("nodeKey");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        //CartList ref
        cartList = FirebaseDatabase.getInstance().getReference().child("CartList");

//        database.child(key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    productDescription = snapshot.child("description").getValue().toString();
//                    productImageUrl = snapshot.child("photo").getValue().toString();
//                    productTitle = snapshot.child("title").getValue().toString();
//                    productPrice = snapshot.child("price").getValue().toString();
//
//                    //Setting the values to widget
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });






        return view;
    }
}