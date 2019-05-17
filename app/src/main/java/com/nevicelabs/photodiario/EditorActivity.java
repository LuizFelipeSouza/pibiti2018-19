package com.nevicelabs.photodiario;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.IOException;

public class EditorActivity extends AppCompatActivity {

    DocumentsProvider docProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        Uri uriImagem = intent.getData();

        try {
            Bitmap imagemBitmap = montarBitmap(uriImagem);
            exibirImagem(imagemBitmap);
        } catch (IOException e) {

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
}

