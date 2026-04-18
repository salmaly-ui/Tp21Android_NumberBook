package com.example.phonebookpro;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdaptateurPersonne
        extends RecyclerView.Adapter<AdaptateurPersonne.LigneVue> {

    private List<Personne> liste;

    public AdaptateurPersonne(List<Personne> liste) {
        this.liste = liste;
    }

    @NonNull @Override
    public LigneVue onCreateViewHolder(@NonNull ViewGroup parent, int vt) {
        android.view.View vue = android.view.LayoutInflater
                .from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new LigneVue(vue);
    }

    @Override
    public void onBindViewHolder(@NonNull LigneVue holder, int pos) {
        Personne p = liste.get(pos);
        holder.ligneUn.setText(p.getNom());
        holder.ligneDeux.setText(p.getTelephone());
    }

    @Override
    public int getItemCount() { return liste.size(); }

    public void mettreAJour(List<Personne> nouvelleListе) {
        this.liste = nouvelleListе;
        notifyDataSetChanged();
    }

    static class LigneVue extends RecyclerView.ViewHolder {
        TextView ligneUn, ligneDeux;
        public LigneVue(@NonNull android.view.View v) {
            super(v);
            ligneUn   = v.findViewById(android.R.id.text1);
            ligneDeux = v.findViewById(android.R.id.text2);
        }
    }
}
