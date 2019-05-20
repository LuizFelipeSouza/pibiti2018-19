package com.nevicelabs.photodiario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<GaleriaViewHolder> {

    private final List<Postagem> mPostagens;

    public LinearAdapter(ArrayList<Postagem> postagens) {
        mPostagens = postagens;
    }

    @NonNull
    @Override
    public GaleriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GaleriaViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_viewholder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GaleriaViewHolder holder, int position) {
        holder.titulo.setText(mPostagens.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
