package com.nevicelabs.photodiario;


import android.provider.BaseColumns;

/**
 * De acordo com uma resposta no StackOverflow, o Design Pattern Contract
 * "defines constants that help applications work with the content URIs,
 *  column names, intent actions, and other features of a content provider.
 *  Contract classes are not included automatically with a provider; the provider's developer has
 *  to define them and then make them available to other developers".
 *
 * A interface BaseColumns tem apenas duas constantes: _ID, que cria um valor autoincrementando, o
 * que é muito útil na utilização de bancos de dados e provedores de conteúdo. A outra constante é
 *  _count, que conta o número de linhas presentes no banco de dados / provedor de conteúdo.
 */
public class PostagensContract implements BaseColumns {

    private static final String URI = "com.nevicelabs.photodiario.provider";
    private static final String PATH = URI + "/postagens";
    public static final String URI_PATTERN = "content://" + PATH + "*";

    String[] projection = {
            PostagensContract._ID,
            PostagensContract._COUNT
    };
}
