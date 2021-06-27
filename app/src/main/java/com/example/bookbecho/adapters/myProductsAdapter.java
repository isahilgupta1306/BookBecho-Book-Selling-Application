package com.example.bookbecho.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookbecho.R;
import com.example.bookbecho.models.productDataModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class myProductsAdapter extends FirebaseRecyclerAdapter<productDataModel , myProductsAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public myProductsAdapter(@NonNull @NotNull FirebaseRecyclerOptions<productDataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myProductsAdapter.myViewHolder holder, int position, @NonNull @NotNull productDataModel model) {
        holder.productPrice.setText("₹" + model.getPrice());
        holder.productDescription.setText(model.getDescription());
        holder.productTitle.setText(model.getTitle());
        Glide.with(holder.productImage.getContext()).load(model.getPhoto()).into(holder.productImage);
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_rv,parent,false);
        return new myProductsAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productTitle , productDescription , productPrice ;
        CardView cardView;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.prodImage);
            productTitle = itemView.findViewById(R.id.prodTitle);
            productDescription = itemView.findViewById(R.id.prodDescription);
            productPrice = itemView.findViewById(R.id.prodPrice);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
