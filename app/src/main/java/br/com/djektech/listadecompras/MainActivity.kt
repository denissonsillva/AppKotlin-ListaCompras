package br.com.djektech.listadecompras

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //implementação do adaptador
        val produtosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        //definindo o adaptador da lista
        list_view_produtos.adapter = produtosAdapter

        btn_adicionar.setOnClickListener {
            //Criando a Intent explícita
            val intent = Intent(this, CadastroActivity::class.java)
            //iniciando a atividade
            startActivity(intent)

        }

        //excluindo um itens da lista
        list_view_produtos.setOnItemClickListener{
            adapterView:AdapterView<*>, view:View, position:Int, id:Long ->
            //buscando o item clicado
            val item = produtosAdapter.getItem(position)
            //removendo o item clicado
            produtosAdapter.remove(item)
            //retorno indicando o sucesso do clique
            true
        }
    }
}