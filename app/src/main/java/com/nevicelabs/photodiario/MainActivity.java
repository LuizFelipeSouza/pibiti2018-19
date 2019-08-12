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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditorFragment.OnFragmentInteractionListener {

    private static final int READ_CONTEXT_CODE = 42;
    private static final int CODIGO_PERMISSOES = 1111;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configurarRecycler();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_CONTEXT_CODE && resultCode == RESULT_OK) {
            // A URI da imagem selecionada está presente na variável data
            if (data != null) {
                Log.i("MainActivity", "URI recebida com êxito: " + data);
                Bundle bundle = new Bundle();
                bundle.putString("uri", data.getDataString());
                Log.i("MainActivity",data.getDataString());
                Navigation.findNavController(this.view).navigate(R.id.action_mainActivityFragment_to_editorFragment, bundle);
                // onFragmentInteraction(data.getData());
            }
        }
    }

    /**
     * Método executado após o usuário permitir o acesso à galeria de imagens ao aplicativo.
     * @param requestCode Um código que representa a solicitação de permissão
     * @param permissions Uma array de strings com as permissões solicitadas.
     * @param grantResults Uma array que indica as permissões garantidas e negadas pelo usuário.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Se o reuqestCode for igual ao código de permissões, as permissões foram concedidas; prossiga
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

    @Override
    public void onFragmentInteraction(Uri uri) { }

    private void configurarRecycler() {
        /* Criei este método apenas para que a activity compilasse.
        No método original (abaixo) passávamos um intent com as informações que iriam popular
        a RecyclerView. Como agora estamos usando Navigation, creio que o intent não é mais
        necessário. Daí verei mais à frente como este método vai ficar. */

        RecyclerView mRecyclerView = findViewById(R.id.galeria_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Postagem post = (Postagem) intent.getSerializableExtra("Postagem");

        ArrayList postagens = new ArrayList<Postagem>();
        // postagens.add(post);

        LinearAdapter mAdapter = new LinearAdapter(postagens);
        mRecyclerView.setAdapter(mAdapter);

        if (postagens.isEmpty()) {
            TextView mensagemPadrao = findViewById(R.id.empty_textview);
            mensagemPadrao.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

        // Aqui dizemos que cada item da RecyclerView é clicável
        mRecyclerView.addOnItemTouchListener(
                new RecyclerView.OnItemTouchListener() {
                    @Override
                    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                        return false;
                    }

                    @Override
                    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                        Snackbar.make(rv, "Replace with your own action", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                    }
                }
        );

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    /**
     * Método executado quando o floating button é pressionado. Solicita ao usuário permissão para
     * acesso à galeria de imagens. Em seguida, abre a galeria e exibe as imagens, caso seja dada a
     * permissão.
     *
     * @param view O argumento é passado automaticamente pela view que chamou o método. No caso. o
     *             FloatingButton.
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
            // Log.i("Busca de imagens", "view: " + view);
            this.view = view;
        }
    }

    public void enviarPostagem(View view) {
        Navigation.findNavController(view).navigate(R.id.action_editorFragment_to_mainActivityFragment);
    }

    private boolean verificarPermissoes() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // Executamos o método (a definir) caso tenhamos acesso aos arquivos
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Faz algo ...

            return true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODIGO_PERMISSOES);
        }
        return false;
    }
}
