package br.com.djektech.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //implementação do adaptador
        val produtosAdapter = ProdutoAdapter(this)

        //definindo o adaptador da lista
        list_view_produtos.adapter = produtosAdapter

        btn_adicionar.setOnClickListener {
            //Criando a Intent explícita
            val intent = Intent(this, CadastroActivity::class.java)
            //iniciando a atividade
            startActivity(intent)
        }

        //excluindo um itens da lista
        list_view_produtos.setOnItemLongClickListener{
            adapterView:AdapterView<*>, view:View, position:Int, id:Long ->
            //buscando o item clicado
            val item = produtosAdapter.getItem(position)
            //removendo o item clicado
            produtosAdapter.remove(item)
            //retorno indicando o sucesso do clique
            true
        }
    }

    override fun onResume() {
        super.onResume()

        val adapter = list_view_produtos.adapter as ProdutoAdapter
        adapter.clear()
        adapter.addAll(produtosGlobal)

        val soma = produtosGlobal.sumByDouble {it.valor * it.quantidade}
        val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        txt_total.text = "TOTAL: ${f.format(soma)}"
    }
}