package com.example.personalrestaurantguide.ui.list;

import android.view.*; import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.personalrestaurantguide.R;
import com.example.personalrestaurantguide.data.RestaurantEntity;
import java.util.*;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.VH> {
    public interface OnItemClick { void onClick(RestaurantEntity item); }
    private List<RestaurantEntity> items = new ArrayList<>();
    private final OnItemClick click;

    public RestaurantAdapter(List<RestaurantEntity> initial, OnItemClick click) {
        if (initial != null) items = initial; this.click = click;
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        View view = LayoutInflater.from(p.getContext()).inflate(R.layout.item_restaurant, p, false);
        return new VH(view);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        RestaurantEntity e = items.get(pos);
        h.tvName.setText(e.name);
        h.tvTags.setText(e.tagsCsv == null ? "" : e.tagsCsv.replace(",", " • "));
        h.tvRating.setText(starsFor(e.rating));
        h.card.setOnClickListener(v -> click.onClick(e));
    }
    @Override public int getItemCount() { return items.size(); }

    public void replaceData(List<RestaurantEntity> newItems){ items = newItems==null? new ArrayList<>(): newItems; notifyDataSetChanged(); }
    public RestaurantEntity getItem(int position){ return (position>=0 && position<items.size()) ? items.get(position) : null; }
    public void removeAt(int position){ if(position>=0 && position<items.size()){ items.remove(position); notifyItemRemoved(position); } }

    private String starsFor(int n){ if(n<0)n=0; if(n>5)n=5; return "★★★★★".substring(0, n); }

    static class VH extends RecyclerView.ViewHolder {
        CardView card; TextView tvName, tvTags, tvRating;
        VH(@NonNull View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.cardRoot);
            tvName = itemView.findViewById(R.id.tvName);
            tvTags = itemView.findViewById(R.id.tvTags);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
