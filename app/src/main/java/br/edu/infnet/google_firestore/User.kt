package br.edu.infnet.google_firestore

data class User (
    var id: String? = null,
    var nome: String? = null,
    var email: String? = null,
    var fones: List<String>? = null
)