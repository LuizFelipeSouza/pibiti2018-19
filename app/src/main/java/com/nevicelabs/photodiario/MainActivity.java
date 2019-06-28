package com.nevicelabs.photodiario;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int READ_CONTEXT_CODE = 42;
    private static final int CODIGO_PERMISSOES = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        configurarRecycler();
    }

    /**
     * Método executado quando o usuário seleciona uma imagem.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_CONTEXT_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;

            // A URI da imagem selecionada está presente na variável data
            if (data != null) {
                // Extraímos a URI atrabés do método getData()
                uri = data.getData();
                descreverImagem(uri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Se o rquqestCode for igual ao código de permissões, as permissões foram concedidas; prossiga
        if (requestCode == CODIGO_PERMISSOES) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Cria um intent que usa o navegador de arquivos do sistema para escolher um arquivo
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Especificamos que os arquivos devem ser de uma categoria "abrível"
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                // Especificamos que os arquivos podem ser imagens com qualquer extensão (.png, .jpg, etc.)
                intent.setType("image/*");

                startActivityForResult(intent, READ_CONTEXT_CODE);
            }
        }
    }

    private void configurarRecycler() {
        // Criei este método apenas para que a activity compilasse.
        // No método original (abaixo) passávamos um intent com as informações que iriam popular
        // a RecyclerView. Como agora estamos usando Navigation, creio que o intent não é mais
        // necessário. Daí verei mais à frente como este método vai ficar.

        RecyclerView mRecyclerView = findViewById(R.id.galeria_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Postagem post = (Postagem) intent.getSerializableExtra("Postagem");

        ArrayList postagens = new ArrayList<Postagem>();
        // postagens.add(post);

        LinearAdapter mAdapter = new LinearAdapter(postagens);
        mRecyclerView.setAdapter(mAdapter);

        if (!postagens.isEmpty()) {
            TextView mensagemPadrao = findViewById(R.id.empty_textview);
            mensagemPadrao.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void configurarRecycler(Intent intent) {
        /*
        RecyclerView mRecyclerView = findViewById(R.id.galeria_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        Postagem post = (Postagem) intent.getSerializableExtra("Postagem");

        ArrayList postagens = new ArrayList<Postagem>();
        postagens.add(post);

        LinearAdapter mAdapter = new LinearAdapter(postagens);
        mRecyclerView.setAdapter(mAdapter);

        if (!postagens.isEmpty()) {
            TextView mensagemPadrao = findViewById(R.id.empty_textview);
            mensagemPadrao.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        */
    }

    private boolean verificarPermissoes() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // Executamos o método (a definir) caso tenhamos acesso aos arquivos
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Faz algo ...

            return true;
        }
        else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODIGO_PERMISSOES);
        }
        return false;
    }

    /**
     * Método executado quando o floating button é pressionado. Abre a galeria e exibe as imagens
     * recentes ao usuário.
     * @param view O argumento é passado automaticamente pela view que chamou o método
     */
    public void buscarImagens(View view) {
        if (verificarPermissoes()) {
            // Cria um intent que usa o navegador de arquivos do sistema para escolher um arquivo
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            // Especificamos que os arquivos devem ser de uma categoria "abrível"
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            // Especificamos que os arquivos podem ser imagens com qualquer extensão (.png, .jpg, etc.)
            intent.setType("image/*");

            startActivityForResult(intent, READ_CONTEXT_CODE);
        }
    }

    /**
     * Este método é executado quando a imagem é selecionada. É responsável por encaminhar o usuário
     * para a EditorActivity.
     *
     * @param uriDaImagem O endereço URI da imagem selecionada pelo usuário na UI padrão do
     *                    Storage Access Framework
     */
    private void descreverImagem(Uri uriDaImagem) {
        Intent intent = new Intent(this, EditorActivity.class);
        intent.setData(uriDaImagem);
        startActivity(intent);
    }
}
