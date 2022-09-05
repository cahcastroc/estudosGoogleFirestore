package br.edu.infnet.google_firestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListaContatoAdapter() : RecyclerView.Adapter<ListaContatoAdapter.ViewHolder>() {


    var listaContatos = ArrayList<User>()

    set(value){
        field = value
        notifyDataSetChanged()
    }

    lateinit var itemListener: RecyclerViewItemListener

    fun setRecyclerViewItemListener(listener: RecyclerViewItemListener){ //Função de inicialização
        //Define o listener do RecylcerView
        itemListener = listener
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       //Renderizador da tela

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.contato_listrow,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Associação de todos
        holder.bindItem(listaContatos.get(position), itemListener, position)
    }

    override fun getItemCount(): Int {
        return listaContatos.size
    }

    //Classe interna - relação Tdo -> Parte
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(user: User, itemListener: RecyclerViewItemListener, position: Int) {
            val tvRowNome = itemView.findViewById<TextView>(R.id.tvRowNome)
            tvRowNome.text = user.nome
            val tvRowEmail = itemView.findViewById<TextView>(R.id.tvRowEmail)
            tvRowEmail.text = user.email
            val tvRowTel = itemView.findViewById<TextView>(R.id.tvRowTel)
            tvRowTel.text = user.fones.toString() //Como é uma lista de tels, vamos usar o To string para que apareçam separados por ,.

            //ItemView é o item clicado --Ao clicar temos que notificar o recyclerViewItemClicked(que criamos na outra classe)

            itemView.setOnClickListener {
                itemListener.recyclerViewItemClicked(it,user.id!!)
            }

        }




    }
}