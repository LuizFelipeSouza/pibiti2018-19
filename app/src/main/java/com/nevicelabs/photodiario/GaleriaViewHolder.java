package com.nevicelabs.photodiario;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GaleriaViewHolder extends RecyclerView.ViewHolder {

    public TextView titulo;

    public GaleriaViewHolder(@NonNull View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.titulo_id);
    }
}
