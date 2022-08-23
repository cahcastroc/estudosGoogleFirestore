package br.edu.infnet.google_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val userDao = UserDao()  //Carregou a classe na memória e ele já está instanciado

//    private lateinit var listaDeIds: ArrayList<String>
//    private lateinit var idEmEducao: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fones = arrayListOf("21222888","5552226")
        val user = User (null,"Camila", "camila@",fones)
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

            val nomes = ArrayList<String>()
            for(documento in listaDeDocumentos ){
                var user = documento.toObject(User::class.java) //conversão
                nomes.add(user.nome.toString()) //Nome pode ser nulo, logo é interessante colocar .toString para afirmar que é uma string

            }

            val listViewContatos = findViewById<ListView>(R.id.listViewContatos)
            val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nomes)
            listViewContatos.adapter = adapter

        }.addOnFailureListener{ exception ->
            Toast.makeText(this,exception.message,Toast.LENGTH_LONG).show()
        }
    }
}