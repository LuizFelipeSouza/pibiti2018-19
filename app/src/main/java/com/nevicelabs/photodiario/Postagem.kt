package com.nevicelabs.photodiario

import android.net.Uri
import java.io.Serializable
import java.util.Date

class Postagem(uriDaImagem: String, legendaDaImagem: String, dataDePublicacao: Date): Serializable {
    // lateinit var titulo: String
    var titulo: String = "Primeira postagem"
}
