package com.nevicelabs.photodiario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int READ_CONTEXT_CODE = 42;
    private static final int CODIGO_PERMISSOES = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    // Método de acesso às fotos do dispositivo
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

    // Método executado quando o usuário seleciona uma imagem.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("", "Imagem selecionada");

        if (requestCode == READ_CONTEXT_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;

            // A URI da imagem selecionada está presente na variável data
            if (data != null) {
                // Extraímos a URI atrabés do método getData()
                uri = data.getData();
                Log.i("", "Pegamos o URI da imagem e ele é: " + uri);
                descreverImagem(uri);
            }
        }
    }

    // Método de criação de texto para foto selecionada
    private void descreverImagem(Uri uriDaImagem) {
        Intent intent = new Intent(this, EditorActivity.class);
        intent.setData(uriDaImagem);
        startActivity(intent);
    }

    private boolean verificarPermissoes() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // Executamos o método (a definir) caso tenhamos acesso aos arquivos
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Faz algo ...
            Log.i("", "As permissões foram garantidas");

            return true;
        }
        else {
            Log.i("", "As permissões não foram garantidas");
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODIGO_PERMISSOES);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Se o rquqestCode for igual ao código de permissões, as permissões foram concedidas; prossiga
        switch (requestCode) {
            case CODIGO_PERMISSOES:
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
}
