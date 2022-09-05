package br.edu.infnet.google_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), RecyclerViewItemListener {

    private val userDao = UserDao()  //Carregou a classe na memória e ele já está instanciado

    private lateinit var listaDeIds: ArrayList<String>
    private lateinit var idEmEducao: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listar()

        val btSave = findViewById<Button>(R.id.btSave)

        btSave.setOnClickListener {
            val etName = findViewById<EditText>(R.id.etName)
            val etEmail = findViewById<EditText>(R.id.etEmail)
            val etPhone = findViewById<EditText>(R.id.etPhone)

            val listFone = ArrayList<String>()
            listFone.add(etPhone.text.toString())

            val user = User(null,etName.text.toString(),etEmail.text.toString(),listFone)

            userDao.inserir(user)?.addOnSuccessListener {
                etName.text=null
                etEmail.text=null
                etPhone.text=null

                listar()
            }?.addOnFailureListener{ exception ->
                Toast.makeText(this,exception.message,Toast.LENGTH_LONG).show()
            }
        }



    }
    private fun listar(){
        userDao.listar().addOnSuccessListener { listaDeDocumentos -> //Callback

            val users = ArrayList<User>()
            for(documento in listaDeDocumentos ){
                var user = documento.toObject(User::class.java) //conversão
                users.add(user) //Nome pode ser nulo, logo é interessante colocar .toString para afirmar que é uma string

            }

            val rvContatos = findViewById<RecyclerView>(R.id.rvContatos)
            rvContatos.layoutManager = LinearLayoutManager(this)
            val adapter = ListaContatoAdapter()
            adapter.listaContatos = users
            adapter.setRecyclerViewItemListener(this)
            rvContatos.adapter = adapter


        }.addOnFailureListener{ exception ->
            Toast.makeText(this,exception.message,Toast.LENGTH_LONG).show()
        }
    }

    override fun recyclerViewItemClicked(view: View, id: String) {


        userDao.obter(id).addOnSuccessListener{

            val user = it.toObject(User::class.java)

            //IMPLEMENTAR ATUALIZAR

            Log.i("DR3","O usuário ${user!!.id} - ${user.nome} foi clicado")

        }
    }

    //Click Longo
    override fun recyclerViewItemClickedLong(view: View, id: String) {
        userDao.del(id).addOnSuccessListener {
            Log.i("DR3","deletado")
            listar()
        }
    }
}