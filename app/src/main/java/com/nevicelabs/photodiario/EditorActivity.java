package com.nevicelabs.photodiario;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class EditorActivity extends AppCompatActivity {

    private Uri uriImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        this.uriImagem = intent.getData();

        try {
            Bitmap imagemBitmap = montarBitmap(uriImagem);
            exibirImagem(imagemBitmap);
        } catch (IOException e) {
            Log.i("EditorActivity", "Exceção");
        }
    }

    private Bitmap montarBitmap(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap imagem = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return imagem;
    }

    private void exibirImagem(Bitmap imagem) {
        ImageView imageView = findViewById(R.id.imagemSelecionadaId);
        imageView.setImageBitmap(imagem);
    }

    public void enviarImagem(View view) {
        EditText editText = findViewById(R.id.legenda_da_imagem);
        String legenda = editText.getText().toString();
        Date horaAtual = Calendar.getInstance().getTime();

        Postagem post = new Postagem(uriImagem, legenda, horaAtual);

        Intent intent = new Intent(this, GaleriaActivity.class);
        // A classe postagem deve implementar a interface Serializable
        intent.putExtra("Postagem", post);

        startActivity(intent);
    }
}

