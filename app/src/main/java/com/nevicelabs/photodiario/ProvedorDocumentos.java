package com.nevicelabs.photodiario;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.provider.DocumentsContract.Root;
import android.provider.DocumentsContract.Document;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProvedorDocumentos extends DocumentsProvider {

    private final static String[] DEFAULT_ROOT_PROJECTION = {
            Root.COLUMN_ROOT_ID,
            Root.COLUMN_MIME_TYPES,
            Root.COLUMN_FLAGS,
            Root.COLUMN_ICON,
            Root.COLUMN_TITLE,
            Root.COLUMN_SUMMARY,
            Root.COLUMN_DOCUMENT_ID,
            Root.COLUMN_AVAILABLE_BYTES
    };

    private static final String[] DEFAULT_DOCUMENT_PROJECTION = new String[]{
            Document.COLUMN_DOCUMENT_ID,
            Document.COLUMN_MIME_TYPE,
            Document.COLUMN_DISPLAY_NAME,
            Document.COLUMN_LAST_MODIFIED,
            Document.COLUMN_FLAGS,
            Document.COLUMN_SIZE
    };

    private final static String ROOT = "42";
    private File mBaseDir;

    @Override
    public boolean onCreate() {
        mBaseDir = getContext().getFilesDir();

        return false;
    }

    /**
     * Este método retorna um cursor que aponta para as raízes dos provedores de documentos.
     *
     * @param projection Contém as colunas que oestão sendo requisitadas. Será nula,
     *                   caso eu queira escolher quais colunas quero retornar
     * @return um Cursor que aponta para as raízes dos provedores de cocmunetos
     * @throws FileNotFoundException Uma Exception para quando o documento não está disponível
     */
    @Override
    public Cursor queryRoots(String[] projection) throws FileNotFoundException {
        // Cria um cursor, tanto com os campos requisitados, quanto com a projeção padrão
        final MatrixCursor resultado = new MatrixCursor(projection != null ? projection : DEFAULT_ROOT_PROJECTION);
        // Criamos uma linha que irá conter informações sobre a raiz
        final MatrixCursor.RowBuilder row = resultado.newRow();

        // Adcionamos informações sobre a raíz nesta linha. Aqui, estamos informando o ID
        row.add(DocumentsContract.Root.COLUMN_ROOT_ID, ROOT);
        // O título da raíz (e.g. Galeria, Drive). No caso. colocamos o nome do aplicativo
        // row.add(DocumentsContract.Root.COLUMN_TITLE, getContext().getString(R.string.app_name));
        // row.add(DocumentsContract.Root.COLUMN_ICON, R.drawable.ic_launcher_foreground);
        // row.add(DocumentsContract.Root.COLUMN_DOCUMENT_ID, getIdPorArquivo(mBaseDir));

        // Não entendi o que esta linha faz. Deve ser em caso de ter múltiplas raízes, aí tem que sumarizar
        // row.add(DocumentsContract.Root.COLUMN_SUMMARY, getContext().getString(R.string.root_summary));

        /* Adicionamos mais informações, neste caso, as flags. Aqui estamos dizendo que os diretórios
         que estão sob a raiz suportam criação de arquivos (esses arquivos podem ser novos diretórios),
         os arquivos desse diretório irão aparecer entre os documentos recentes e
         a busca de arquivos */
        row.add(DocumentsContract.Root.COLUMN_FLAGS, DocumentsContract.Root.FLAG_SUPPORTS_CREATE |
                DocumentsContract.Root.FLAG_SUPPORTS_SEARCH);

        // row.add(DocumentsContract.Root.COLUMN_MIME_TYPES, getMimeChildTypes(mBaseDir));
        row.add(DocumentsContract.Root.COLUMN_AVAILABLE_BYTES, mBaseDir.getFreeSpace());

        return resultado;
    }

    /**
     * Método executado quando o usuário seleciona uma raíz atrvés da IU do seletor. Aponta para
     * os arquivos do diretório solicitado. Os filhos podem ser arquivos ou outros diretórios.
     *
     * @param parentDocumentId O ID do documento pai. Pode ser uma raíz ou um diretório acima.
     * @param projection
     * @param sortOrder
     * @return Um objeto Cursor que aponta para o arquivo ou diretório filho daquele que foi selecionado
     * @throws FileNotFoundException Exceção lançada quando o arquivo estiver indisponível
     */
    @Override
    public Cursor queryChildDocuments(
            String parentDocumentId,
            String[] projection, String sortOrder) throws FileNotFoundException {

        // final MatrixCursor resultado = new MatrixCursor(resolveDocumentProjection(projection));
        final MatrixCursor resultado = new MatrixCursor(projection != null ? projection : DEFAULT_ROOT_PROJECTION);
        final File parent = getArquivoPorId(parentDocumentId);

        for (File arquivo : parent.listFiles()) {
            // Adiciona o nome do documento, tipo MIME, tamanho, etc.
            incluirArquivo(resultado, null, arquivo);
        }

        return resultado;
    }

    /**
     * Faz uma busca pelos documentos e retorna um Cursor que aponta para o documento indicado.
     * Retorna as mesmas informações que queryChildDocuments, mas para um documento específico.
     *
     * @param documentId O ID do documento requisitado
     * @param projection ainda não entendi o que é esse projection
     * @return Um objeto Cursor que aponta para um documento específico
     * @throws FileNotFoundException Exceção lançada quando o arquivo não está disponível
     */
    @Override
    public Cursor queryDocument(String documentId, String[] projection)
            throws FileNotFoundException {
        final MatrixCursor resultado = new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);
        incluirArquivo(resultado, documentId, null);

        return resultado;
    }

    /**
     * Este método retorna um ParcelFileDescriptor que representa o arquivo especificado.
     * este método é chamado quando o usuário seleciona um documento e o aplicativo cliente chama
     * openFileDescriptor().
     *
     * @param documentId
     * @param mode
     * @param signal
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public ParcelFileDescriptor openDocument(
            final String documentId, String mode, CancellationSignal signal) throws FileNotFoundException {

        final File arquivo = getArquivoPorId(documentId);
        final boolean isWrite = (mode.indexOf('w') != -1);
        int modoDeAcesso = Integer.getInteger(mode);

        if (isWrite) {
            try {
                Handler handler = new Handler(getContext().getMainLooper());

                return ParcelFileDescriptor.open(arquivo, modoDeAcesso, handler,
                        new ParcelFileDescriptor.OnCloseListener() {
                            @Override
                            public void onClose(IOException e) {
                                Log.i("Log", "Um arquivo com o id " + documentId + "foi fechado."
                                        + "Atualize o servidor");
                            }
                        });
            } catch (IOException e) {
                throw new FileNotFoundException("Falaha ao abrir o documento de id " + documentId);
            }
        } else {
            return ParcelFileDescriptor.open(arquivo, modoDeAcesso);
        }
    }

    private File getArquivoPorId(String idDoDocumento) throws FileNotFoundException {
        File target = mBaseDir;

        if (idDoDocumento.equals(ROOT)) {
            return target;
        }

        final int splitIndex = idDoDocumento.indexOf(':', 1);;

        if (splitIndex < 0) {
            throw new FileNotFoundException("Não existe raíz para o documento " + idDoDocumento);
        } else {
            final String path = idDoDocumento.substring(splitIndex + 1);
            target = new File(target, path);

            if (!target.exists()) {
                throw new FileNotFoundException("Não existe arquivo para " + idDoDocumento +
                        "no índice " + target);
            }

            return target;
        }
    }

    private String getIdPorArquivo(File arquivo) {
        String path = arquivo.getAbsolutePath();

        final String rootPath = mBaseDir.getPath();

        if (rootPath.equals(path)) {
            path = "";
        } else if (rootPath.endsWith("/")) {
            path = path.substring(rootPath.length());
        } else {
            path = path.substring(rootPath.length() + 1);
        }
        return "root" + ':' + path;
    }

    private static String getTypeForFile(File file) {
        if (file.isDirectory()) {
            return Document.MIME_TYPE_DIR;
        } else {
            return getTypeForName(file.getName());
        }
    }

    private static String getTypeForName(String name) {
        final int lastDot = name.lastIndexOf('.');
        if (lastDot >= 0) {
            final String extension = name.substring(lastDot + 1);
            final String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            if (mime != null) {
                return mime;
            }
        }
        return "application/octet-stream";
    }

    private void incluirArquivo(MatrixCursor resultado, String docId, File arquivo)
            throws FileNotFoundException {
        if (docId == null) {
            docId = getIdPorArquivo(arquivo);
        } else {
            arquivo = getArquivoPorId(docId);
        }

        int flags = 0;

        if (arquivo.isDirectory()) {
            if (arquivo.canWrite()) {
                flags |= Document.FLAG_DIR_SUPPORTS_CREATE;
            }
        } else if (arquivo.canWrite()) {
            flags |= Document.FLAG_SUPPORTS_WRITE;
            flags |= Document.FLAG_SUPPORTS_DELETE;
        }

        final String nomeNoDisplay = arquivo.getName();
        final String mimeType = getTypeForFile(arquivo);

        if (mimeType.startsWith("image/*")) {
            flags |= Document.FLAG_SUPPORTS_THUMBNAIL;
        }

        final MatrixCursor.RowBuilder row = resultado.newRow();
        row.add(Document.COLUMN_DOCUMENT_ID, docId);
        row.add(Document.COLUMN_DISPLAY_NAME, nomeNoDisplay);
        row.add(Document.COLUMN_SIZE, arquivo.length());
        row.add(Document.COLUMN_MIME_TYPE, mimeType);
        row.add(Document.COLUMN_LAST_MODIFIED, arquivo.lastModified());
        row.add(Document.COLUMN_FLAGS, flags);
        // row.add(Document.COLUMN_ICON, R.drawable.ic_launcher_foreground);
    }
}
