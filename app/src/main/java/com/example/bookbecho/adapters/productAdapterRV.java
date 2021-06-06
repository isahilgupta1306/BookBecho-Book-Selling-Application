package com.example.bookbecho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookbecho.R;
import com.example.bookbecho.models.productDataModel;

import java.util.ArrayList;

public class productAdapterRV extends RecyclerView.Adapter<productAdapterRV.myViewHolder>{

    ArrayList<productDataModel> dataHolder;

    public productAdapterRV(ArrayList<productDataModel> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_rv,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  productAdapterRV.myViewHolder holder, int position) {
        holder.productImage.setImageResource(dataHolder.get(position).getProductImage());
        holder.productPrice.setText(dataHolder.get(position).getProductPrice());
        holder.productDescription.setText(dataHolder.get(position).getProductDescription());
        holder.productTitle.setText(dataHolder.get(position).getProductTitle());

    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
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
