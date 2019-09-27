package com.nevicelabs.photodiario;

import android.content.ContentValues;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
    public class PostagemDAO {

    private ContentValues mNovosValores;

    @Query("SELECT * FROM postagem")
    List<Postagem> loadAllByIds(int[] postagemIds) {
        return null;
    }

    @Insert
    public void inserirPostagem(){
        mNovosValores = new ContentValues();
    }
}
