package co.example.user.myapp;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*Адаптер для RecyclerView (Карточки с мероприятиями)*/

public class CARD_Adapter extends RecyclerView.Adapter<CARD_Adapter.PersonViewHolder>{

    public static  class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView eventName;
        TextView eventTime;
        TextView eventAdress;
        TextView eventCategory;
        TextView eventId;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            eventName = itemView.findViewById(R.id.eventName);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventAdress = itemView.findViewById(R.id.eventAdress);
            eventCategory = itemView.findViewById(R.id.eventCategory);
            eventId = itemView.findViewById(R.id.eventId);
        }
    }

    List<Card_Search_View.Event> events;

    CARD_Adapter(List<Card_Search_View.Event> events){
        this.events = events;
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cv, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.eventName.setText(events.get(i).name);
        personViewHolder.eventTime.setText(events.get(i).time);
        personViewHolder.eventAdress.setText(events.get(i).adress);
        personViewHolder.eventCategory.setText(events.get(i).category);
        personViewHolder.eventId.setText(events.get(i).id);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
