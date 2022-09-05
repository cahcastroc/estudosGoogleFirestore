package br.edu.infnet.google_firestore

import android.view.View

interface RecyclerViewItemListener {

    fun recyclerViewItemClicked(view: View, id: String) //aqui vamos saber qual a view foi clicada e qual id (posição dele)

    fun recyclerViewItemClickedLong(view: View, id: String)
}