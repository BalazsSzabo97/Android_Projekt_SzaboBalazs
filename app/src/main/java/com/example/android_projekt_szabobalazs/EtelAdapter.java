package com.example.android_projekt_szabobalazs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EtelAdapter extends RecyclerView.Adapter<EtelAdapter.MyViewHolder> {

    private Context context ;
    private List<Etel> etelLista ;


    public EtelAdapter(Context context, List<Etel> etelLista) {
        this.context = context;
        this.etelLista = etelLista;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView nev;
        ImageView kep;
        TextView ar;
        TextView suly;
        public MyViewHolder(View itemView, final Context context) {
            super(itemView);
            nev = itemView.findViewById(R.id.nev);
            kep = itemView.findViewById(R.id.kep);
            ar = itemView.findViewById(R.id.ar);
            suly = itemView.findViewById(R.id.suly);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),KosarActivity.class);
                    intent.putExtra("ujEtel",nev.getText());
                    String aar = ar.getText().toString().substring(0,ar.getText().toString().length() - 7);
                    intent.putExtra("ujAr",aar);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    @NonNull
    @Override
    public EtelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, context) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EtelAdapter.MyViewHolder holder, int position) {
        Etel etel = etelLista.get(position);
        Picasso.get().load(etel.getKep()).into(holder.kep);
        holder.nev.setText(etel.getNev());
        holder.ar.setText(etel.getAr());
        holder.suly.setText(etel.getSuly());
    }

    @Override
    public int getItemCount() {
        return etelLista.size();
    }
}


