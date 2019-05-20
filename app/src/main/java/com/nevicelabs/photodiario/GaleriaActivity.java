package com.nevicelabs.photodiario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class GaleriaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearAdapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private ArrayList<Postagem> postagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        // O intent contemdo o URI da imagem e sua legenda e a data de publicação.
        Intent intent = getIntent();
    }

    private void configurarRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        postagens = new ArrayList<>();

        // mAdapter = new ArrayAdapter(this, linearLayoutManager, postagens);
        mAdapter = new LinearAdapter(new ArrayList<Postagem>(0));
        mRecyclerView.setAdapter(mAdapter);


    }
}
