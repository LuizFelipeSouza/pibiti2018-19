package com.nevicelabs.photodiario;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

public class PostagemDAO extends ContentResolver {

    private ContentValues mNovosValores;

    public PostagemDAO(Context context) {
        super(context);
    }

    public void inserirPostagem(){
        mNovosValores = new ContentValues();
    }
}
