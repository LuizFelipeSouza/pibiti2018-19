package com.nevicelabs.photodiario;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "postagens")
public class Postagem implements Serializable {

        public Postagem() {}

        public Postagem(String uri, String legenda, String dataDePublicacao) {
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
        public String titulo = "Primeira Postagem";

        @ColumnInfo(name="legenda")
        private String legenda;

        @ColumnInfo(name="data_publicacao")
        private String dataDePublicacao;

        public long getId() {
                return id;
        }

        public String getDataDePublicacao() {
                return dataDePublicacao;
        }

        public String getLegenda() {
                return legenda;
        }

        public String getTitulo() {
                return titulo;
        }

        public void setId(long id) {
                this.id = id;
        }

        public void setUriImagem(String uriImagem) {
                this.uriImagem = uriImagem;
        }

        public void setTitulo(String titulo) {
                this.titulo = titulo;
        }

        public void setLegenda(String legenda) {
                this.legenda = legenda;
        }

        public void setDataDePublicacao(String dataDePublicacao) {
                this.dataDePublicacao = dataDePublicacao;
        }

        public String getUriImagem() {
                return uriImagem;
        }
}
