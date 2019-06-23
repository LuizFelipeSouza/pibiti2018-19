package com.nevicelabs.photodiario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.GaleriaViewHolder> {

    private final List<Postagem> mPostagens;

    LinearAdapter(ArrayList<Postagem> postagens) {
        mPostagens = postagens;
    }

    static class GaleriaViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        ImageView foto;
        TextView legenda;

        GaleriaViewHolder(View v) {
            super(v);
            titulo = v.findViewById(R.id.titulo_viewholder);
            foto = v.findViewById(R.id.foto_id);
            legenda = v.findViewById(R.id.legenda_da_imagem);
        }
    }

    @NonNull
    @Override
    public GaleriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GaleriaViewHolder(LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.layout_viewholder, parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull GaleriaViewHolder holder, int position) {
        holder.titulo.setText(mPostagens.get(position).getTitulo());
    }

    @Override
    public int getItemCount() {
        return mPostagens.size();
    }

    public void atulizarLista(Postagem post) {
        inserirItem(post);
    }

    private void inserirItem(Postagem postagem) {
        mPostagens.add(postagem);
        notifyItemInserted(getItemCount());
    }
}
