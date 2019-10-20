package com.nevicelabs.photodiario;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = Postagem.TABLE_NAME)
public class Postagem implements Serializable {

        public Postagem(String uri, String legenda, Date dataDePublicacao) {
                this.uriImagem = uri;
                this.legenda = legenda;
                this.dataDePublicacao = dataDePublicacao;
        }

        public static final String TABLE_NAME = "postagens";

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true, name = "id")
        public long id;

        @ColumnInfo(name="uri")
        private String uriImagem;

        @ColumnInfo(name="titulo")
        private String titulo = "Primeira Postagem";

        @ColumnInfo(name="legenda")
        private String legenda;

        @ColumnInfo(name="data_publicacao")
        private Date dataDePublicacao;
}
