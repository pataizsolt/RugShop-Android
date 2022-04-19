package com.mobileapp.rugshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.mobileapp.rugshop.CarpetListActivity;
import com.mobileapp.rugshop.R;
import com.mobileapp.rugshop.model.Carpet;

import java.util.ArrayList;

public class CarpetAdapter
        extends RecyclerView.Adapter<CarpetAdapter.ViewHolder>
        implements Filterable {
    // Member variables.
    private ArrayList<Carpet> mShoppingData;
    private ArrayList<Carpet> mSoppingDataAll;
    private Context mContext;
    private int lastPosition = -100;

    public CarpetAdapter(Context context, ArrayList<Carpet> itemsData) {
        this.mShoppingData = itemsData;
        this.mSoppingDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public CarpetAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.element, parent, false));
    }

    @Override
    public void onBindViewHolder(CarpetAdapter.ViewHolder holder, int position) {
        // Get current sport.
        Carpet currentItem = mShoppingData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentItem);




        if(holder.getAbsoluteAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAbsoluteAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mShoppingData.size();
    }


    /**
     * RecycleView filter
     * **/
    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private Filter shoppingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Carpet> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mSoppingDataAll.size();
                results.values = mSoppingDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Carpet item : mSoppingDataAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mShoppingData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView carpetName;
        private TextView carpetDescription;
        private TextView price;
        private TextView count;
        private TextView bought;
        private ImageView mCarpetImage;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            carpetName = itemView.findViewById(R.id.carpetName);
            carpetDescription = itemView.findViewById(R.id.carpetDescription);
            price = itemView.findViewById(R.id.price);
            count = itemView.findViewById(R.id.count);
            mCarpetImage = itemView.findViewById(R.id.carpetImage);


            //itemView.findViewById(R.id.add_to_cart).setOnClickListener(view -> ((CarpetListActivity)mContext).updateAlertIcon());
        }

        void bindTo(Carpet currentCarpet){
            carpetName.setText(currentCarpet.getName());
            carpetDescription.setText("Type: "+currentCarpet.getType()+"\nColor: "+currentCarpet.getColor()+"\nDimensions: "+currentCarpet.getWidth()+"cm x "+currentCarpet.getLength()+"cm");
            price.setText(currentCarpet.getPrice()+" EUR");
            count.setText("Avaliable: "+currentCarpet.getStock());
            Glide.with(mContext).load(currentCarpet.getImageResource()).into(mCarpetImage);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(view -> ((CarpetListActivity)mContext).updateCarpet(currentCarpet));

            // Load the images into the ImageView using the Glide library.




        }
    }
    /*private class CarpetViewHolder extends RecyclerView.ViewHolder{
        private TextView carpetName;
        private TextView carpetDescription;
        private TextView price;

        public CarpetViewHolder(@NonNull View itemView) {
            super(itemView);

            carpetName = itemView.findViewById(R.id.carpetName);
            carpetDescription = itemView.findViewById(R.id.carpetDescription);
            price = itemView.findViewById(R.id.price);
        }
    }*/
}

