package com.nevicelabs.photodiario

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity
class Postagem(
        @ColumnInfo(name="uri") val uriDaImagem: String,
        @ColumnInfo(name="legenda") val legendaDaImagem: String,
        @ColumnInfo(name="data") val dataDePublicacao: Date
): Serializable {
    // lateinit var titulo: String
    var titulo: String = "Primeira postagem"
}
