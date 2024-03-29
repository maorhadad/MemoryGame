package com.hadadas.memorygame.bord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hadadas.memorygame.R;

import java.util.ArrayList;
import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {

    private List<Card> cards;
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;

    public CardsAdapter(Context context, List<Integer> uniqueIds) {
        this.mInflater = LayoutInflater.from(context);
        this.cards = initCardsFromIds(uniqueIds);
        this.cards.addAll(duplicateList());
    }

    private List<Card> duplicateList(){
        List<Card> dupCards = new ArrayList<>();
        for (Card card: cards) {
            Card tempCard = new Card(card);
            dupCards.add(tempCard);
        }
        return dupCards;
    }

    private List<Card> initCardsFromIds(List<Integer> uniqueIds){
        List<Card> tempCards = new ArrayList<>();
        for (Integer id:
             uniqueIds) {
            Card card = new Card(id);
            tempCards.add(card);
        }
        return tempCards;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.card, parent, false);

        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card card = cards.get(position);
        if(card.isFront()){
            setCardFront(holder, card );
            //Todo Populate with lazy loading
        }else{
            setCardBack(holder, card);
            holder.title.setText(String.valueOf(card.getId()));
        }
    }

    private void setCardFront(CardViewHolder holder, Card card){
        LinearLayout llBack = holder.itemView.findViewById(R.id.back);
        RelativeLayout RlFront = holder.itemView.findViewById(R.id.front);

        llBack.setVisibility(View.GONE);
        RlFront.setVisibility(View.VISIBLE);
    }

    private void setCardBack(CardViewHolder holder, Card card){
        LinearLayout llBack = holder.itemView.findViewById(R.id.back);
        RelativeLayout RlFront = holder.itemView.findViewById(R.id.front);

        llBack.setVisibility(View.VISIBLE);
        RlFront.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public Card getItem(int id) {
        return cards.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;


        public CardViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

}
