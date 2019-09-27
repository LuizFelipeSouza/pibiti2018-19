package com.nevicelabs.photodiario;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.DoubleSummaryStatistics;

/**
 * Implementação da calsse abstrata ContentProvider e de seus métodos obrigatórios.
 * A string de autoridade do provedor, os URIs de conteúdo e seus nomes de coluna estão
 * definidos na classe PostagemContract.
 */
public class ProvedorPostagens extends ContentProvider {
    // Exemplo de implementação deste método. Esta variável representa o banco de dados que ainda vamos implementar.
    // private AppDatabase appDatabase;

    private PostagemDAO postagemDAO;
    private static final String DBNAME = "postagensdb";

    @Override
    public boolean onCreate() {
        PostagensDatabase appDatabase = Room.databaseBuilder(getContext(), PostagensDatabase.class, DBNAME).build();
        postagemDAO = appDatabase.getPostagemDao();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        /*
            O método ContentProvider.query() precisa retornar um objeto
            Cursor ou, caso haja falha, lançar uma Exception. Se você estiver
            usando um banco de dados SQLite como armazenamento de
            dados, pode simplesmente retornar o Cursor retornado por um dos
            métodos query() da classe SQLiteDatabase. Se a consulta não
            corresponder a nenhuma linha, será necessário retornar uma
            instância de Cursor cujo método getCount() retorne 0. Retorne null
            somente se ocorrer um erro interno durante o processo de consulta.
         */

        /*
        // A "projection" defines the columns that will be returned for each row
        String[] mProjection = {
                PostagensContract.URI_IMAGEM,
                PostagensContract.TITULO,
                PostagensContract.LEGENDA,
                PostagensContract.URI_POSTAGEM
        };

        // Gets a word from the UI
        String searchString = searchWord.getText().toString();

        // Remember to insert code here to check for invalid or malicious input.

        // If the word is the empty string, gets everything
        if (TextUtils.isEmpty(searchString)) {
            // Setting the selection clause to null will return all words
            selection = null;
            selectionArgs[0] = "";

        } else {
            // Constructs a selection clause that matches the word that the user entered.
            selection = PostagensContract.TITULO + " = ?";

            // Moves the user's input string to the selection arguments.
            selectionArgs[0] = searchString;

        }

        // Does a query against the table and returns a Cursor object
        Cursor mCursor = getContentResolver().query(
                PostagensContract.URI_POSTAGEM,  // The content URI of the words table
                projection,                       // The columns to return for each row
                selection,                  // Either null, or the word the user entered
                selectionArgs,                    // Either empty, or the string the user entered
                sortOrder);                       // The sort order for the returned rows

        // Some providers return null if an error occurs, others throw an exception
        if (null == mCursor) {
            // Insert code here to handle the error. Be sure not to use the cursor! You may want to
            // call android.util.Log.e() to log this error.

            // If the Cursor is empty, the provider found no matches
        } else if (mCursor.getCount() < 1) {
            // Insert code here to notify the user that the search was unsuccessful. This isn't necessarily
            // an error. You may want to offer the user the option to insert a new row, or re-type the
            // search term.
        } else {
            // Insert code here to do something with the results

        }
        // Defines a list of columns to retrieve from the Cursor and load into an output row
        String[] wordListColumns =
                {
                        PostagensContract.LEGENDA,   // Contract class constant containing the word column name
                        PostagensContract.URI_IMAGEM,
                        PostagensContract.TITULO
                };

        // Defines a list of View IDs that will receive the Cursor columns for each row
        int[] wordListItems = { R.id.dictWord, R.id.locale};

        // Creates a new SimpleCursorAdapter
        cursorAdapter = new ListAdapter(
                getApplicationContext(),               // The application's Context object
                R.layout.wordlistrow,                  // A layout in XML for one row in the ListView
                mCursor,                               // The result from the query
                wordListColumns,                      // A string array of column names in the cursor
                wordListItems,                        // An integer array of view IDs in the row layout
                0);                                    // Flags (usually none are needed)

        // Sets the adapter for the ListView
        wordList.setAdapter(cursorAdapter);
        */

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        /* O método insert() adiciona uma nova linha à tabela apropriada
        usando os valores no argumento ContentValues. Se um nome de
        coluna não estiver no argumento ContentValues, talvez seja
        necessário fornecer um valor padrão para ele tanto no código do
        provedor quanto no esquema do banco de dados.

        Esse método retornará o URI de conteúdo da nova linha. Para
        construir isso, anexe o valor _ID da nova linha (ou outra chave
        principal) ao URI de conteúdo da tabela usando withAppendedId(). */
        // Defines a new Uri object that receives the result of the insertion
        Uri newUri;

        // Defines an object to contain the new values to insert
        ContentValues newValues = new ContentValues();

        /*
         * Sets the values of each column and inserts the word. The arguments to the "put"
         * method are "column name" and "value"
         */
        /*
        newValues.put(UserDictionary.Words.APP_ID, "example.user");
        newValues.put(UserDictionary.Words.LOCALE, "en_US");
        newValues.put(UserDictionary.Words.WORD, "insert");
        newValues.put(UserDictionary.Words.FREQUENCY, "100");

        newUri = getContentResolver().insert(
                PostagensContract.URI_POSTAGEM,   // the user dictionary content URI
                newValues                          // the values to insert
        );
        */

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
