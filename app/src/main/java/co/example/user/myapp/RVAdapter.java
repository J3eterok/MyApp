package co.example.user.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewHolder> {

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView cardName;
        TextView cardDate;
        TextView cardPlace;
        TextView cardCategory;

        CardViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            cardName = (TextView)itemView.findViewById(R.id.text_name);
            cardDate = (TextView)itemView.findViewById(R.id.text_date);
            cardPlace = (TextView)itemView.findViewById(R.id.text_place);
            cardCategory = (TextView)itemView.findViewById(R.id.text_category);
        }
    }

    List<Card> cards;

    RVAdapter(List<Card> cards) {this.cards = cards;}

    @Override
    public int getItemCount() {
        return cards.size();
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }
    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.cardName.setText(cards.get(i).name);
        cardViewHolder.cardDate.setText(cards.get(i).date);
        cardViewHolder.cardPlace.setText(cards.get(i).place);
        cardViewHolder.cardCategory.setText(cards.get(i).category);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
