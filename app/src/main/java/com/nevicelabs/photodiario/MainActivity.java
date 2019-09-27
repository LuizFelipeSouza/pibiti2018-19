package com.nevicelabs.photodiario;

import android.Manifest;
import android.app.SearchManager;
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

        /* Verificamos se há alguma intent sendo passada ao iniciar a Activity.
         * Neste caso, estamos verificando se há alguma busca sendo feita.
        */
        if(getIntent() != null) {
            Intent intent = getIntent();
            if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                // buscar(query);
            }
        }

        // Esta opção faz com que a busca seja ativada quando o usuário começa a digitar
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
    }

    /**
     * Este método é chamado após o fechamento da galeria. É utilizado para
     * tratar a imagem selecionada pelo usuário.
     * @param requestCode O código de identificação da chamada à galeria.
     * @param resultCode O código retornado pela chamada à galeria.
     * @param data Os dados retornados pela chamada à galeria. Neste caso,
     *             irá conter o endereço URI da imagem selecionada.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_CONTEXT_CODE && resultCode == RESULT_OK) {
            // A URI da imagem selecionada está presente na variável data
            if (data != null) {
                Bundle bundle = new Bundle();
                bundle.putString("uri", data.getDataString());
                uri = data.getDataString();
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

    /**
     * Método utilizado para verificar se as permissões necessárias para o
     * funcionamento do aplicativo foram concedidas.
     * @return true, quando as permissões forem concedidas e false, caso contrário.
     */
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
            this.view = view;
        }
    }

    /**
     * Método chamado ao clique do botão enviar em EditorFragment.
     *
     * @param view a view que invocou o método. No caso, um buttom
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void enviarPostagem(View view) {
        EditText editText = findViewById(R.id.legenda_da_imagem);
        String legenda = editText.getText().toString();
        Date horaAtual = Calendar.getInstance().getTime();

        Postagem post = new Postagem(uri, legenda, horaAtual);

        inserirPostagem(post);

        ActionEditorFragmentToGaleriaActivityFragment action =
                EditorFragmentDirections.actionEditorFragmentToGaleriaActivityFragment(post);

        Navigation.findNavController(view).navigate(action);
    }

    /**
     * Método sobrescrito da interface OnPostagemSelecionadaListener
     * declarada em GaleriaFragment. É chamado quando um item da lista de
     * postagens (a RecyclerView) é selecionado. Aqui passamos as
     * informações da postagem selecionada para que possams abri-la em
     * PostagemFragment.
     *
     * @param uri O endereço URI da postagem selecionada;
     */
    @Override
    public void onPostagemSeleionada(Uri uri) {}

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    /**
     * Este método é eecutado quando o usuário digita uma string de busca.
     * A partir dessa string, é feita uma busca no Provedor de Documentos. De acordo
     * com este guia: https://developer.android.com/guide/topics/providers/content-provider-basics.html?hl=pt-BR
     * creio que uma possível implementação teria o formato abaixo.
     *
     * ATENÇÂO: a implementação atual deste método é apenas um exemplo, não a
     * versão final. Estou apenas anotando o que está no guia.
     * @param v
     */
    public void botaoBusca(View v) {
        onSearchRequested();
    }

    /**
     * Método chamado quando o botão enviarPostagem é clicado
     * em EditorFragment. Persiste a postagem no provedor de documentos.
     *
     * @param post A postagem a ser persistida
     */
    private void inserirPostagem(Postagem post) {

    }
}
