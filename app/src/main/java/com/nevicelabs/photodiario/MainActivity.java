package com.nevicelabs.photodiario;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.util.Date;

import com.nevicelabs.photodiario.EditorFragmentDirections.ActionEditorFragmentToGaleriaActivityFragment;
import com.nevicelabs.photodiario.GaleriaFragment.OnPostagemSelecionadaListener;

public class MainActivity extends AppCompatActivity implements OnPostagemSelecionadaListener {

    private static final int READ_CONTEXT_CODE = 42;
    private static final int CODIGO_PERMISSOES = 1111;
    private View view;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                uri = data.getDataString();
                Log.i("MainActivity",data.getDataString());
                Navigation.findNavController(this.view).navigate(R.id.action_mainActivityFragment_to_editorFragment, bundle);
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

    private boolean verificarPermissoes() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // Executamos o método (a definir) caso tenhamos acesso aos arquivos
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODIGO_PERMISSOES);
        }
        return false;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void enviarPostagem(View view) {
        EditText editText = findViewById(R.id.legenda_da_imagem);
        String legenda = editText.getText().toString();
        Date horaAtual = Calendar.getInstance().getTime();

        Postagem post = new Postagem(uri, legenda, horaAtual);

        ActionEditorFragmentToGaleriaActivityFragment action =
                EditorFragmentDirections.actionEditorFragmentToGaleriaActivityFragment(post);

        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onPostagemSeleionada(Uri uri) {

    }
}
