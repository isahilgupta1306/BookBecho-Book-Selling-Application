package com.example.bookbecho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookbecho.R;
import com.example.bookbecho.models.productDataModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class productAdapterRV extends FirebaseRecyclerAdapter< productDataModel,productAdapterRV.myViewHolder> {

//    ArrayList<productDataModel> dataHolder;


    public productAdapterRV(@NonNull @NotNull FirebaseRecyclerOptions<productDataModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_rv,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  myViewHolder holder, int position , productDataModel model) {
//        holder.productImage.setImageResource(model.getProductImage());
        holder.productPrice.setText(model.getPrice());
        holder.productDescription.setText(model.getDescription());
        holder.productTitle.setText(model.getTitle());
        Glide.with(holder.productImage.getContext()).load(model.getPhoto()).into(holder.productImage);

    }



    class myViewHolder extends RecyclerView.ViewHolder{

    ImageView productImage;
    TextView productTitle , productDescription , productPrice ;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.prodImage);
            productTitle = itemView.findViewById(R.id.prodTitle);
            productDescription = itemView.findViewById(R.id.prodDescription);
            productPrice = itemView.findViewById(R.id.prodPrice);
        }
    }
}
