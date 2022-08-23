package br.edu.infnet.google_firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserDao {

    private val collection = "users"
    val db = Firebase.firestore

    fun inserir(user: User) : Task<Void>? {
        var task: Task<Void>? = null
        if (user.id == null){
            val ref : DocumentReference = db.collection(collection).document()
            user.id = ref.id
            task = ref.set(user)
        }
        return task
    }

    fun listar(): Task<QuerySnapshot>{
        return db.collection(collection).get()
    }
}