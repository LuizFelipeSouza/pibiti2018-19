package com.nevicelabs.photodiario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        Intent intent = getIntent();

        configurarRecycler(intent);
    }

    private void configurarRecycler(Intent intent) {
        RecyclerView mRecyclerView = findViewById(R.id.galeria_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Postagem post = (Postagem) intent.getSerializableExtra("Postagem");

        ArrayList postagens = new ArrayList<Postagem>();
        postagens.add(post);

        LinearAdapter mAdapter = new LinearAdapter(postagens);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
