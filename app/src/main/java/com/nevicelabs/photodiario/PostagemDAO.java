package com.nevicelabs.photodiario;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
    public interface PostagemDAO {

    /**
     * Retorna todas as postagens no banco de dados.
     *
     * @return Um cursor que permite a navegação entre as postagens.
     */
    @Query("SELECT * FROM " + Postagem.TABLE_NAME)
    public Cursor selectAll();

    /**
     * Insere uma única postagem no banco de dados.
     *
     * @param postagem A postagem a ser inserida
     * @return O id da postagem que acabou de ser inserida.
     */
    @Insert
    long inserirPostagem(Postagem postagem);
}
