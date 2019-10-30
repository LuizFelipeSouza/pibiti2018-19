package com.nevicelabs.photodiario;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
    public interface PostagemDAO {

    /**
     * Retorna um cursor que fornece acesso a todas as postagens no banco de dados.
     *
     * @return Um cursor que permite a navegação entre as postagens.
     */
    @Query("SELECT * FROM " + Postagem.TABLE_NAME)
    Cursor getAll();

    /**
     * Faz a query de todas as postagens no banco de dados
     *
     * @return Uma List com todas as postagens do banco de dados.
     */
    @Query("SELECT * FROM " + Postagem.TABLE_NAME)
    List<Postagem> selectAll();

    @Query("SELECT * FROM " + Postagem.TABLE_NAME + " WHERE titulo LIKE :query")
    List<Postagem> selectPostagens(String query);

    /**
     * Insere uma única postagem no banco de dados.
     *
     * @param postagem A postagem a ser inserida
     * @return O id da postagem que acabou de ser inserida.
     */
    @Insert
    void inserirPostagem(Postagem postagem);
}
