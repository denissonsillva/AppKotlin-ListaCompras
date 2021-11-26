package br.com.djektech.listadecompras

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.nio.file.Files.delete
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

        //excluindo um itens da lista
        list_view_produtos.setOnItemLongClickListener{

            adapterView:AdapterView<*>, view:View, position:Int, id:Long ->

            //buscando o item clicado
            val item = produtosAdapter.getItem(position)

            //removendo o item clicado
            produtosAdapter.remove(item)

            //deletando do banco de dados
            item?.id?.let { deletarProduto(it) }
            toast("Item deletado com sucesso!")

            //retorno indicando o sucesso do clique
            true
        }

        btn_adicionar.setOnClickListener {

            //Criando a Intent explícita
            //val intent = Intent(this, CadastroActivity::class.java)

            //iniciando a atividade
            startActivity<CadastroActivity>()

            //val intent = Intent(this, CadastroActivity::class.java)
            //startActivity(intent)
        }
    }

    fun deletarProduto(idProduto:Int) {

        database.use {
            delete("produtos", "id = {id}", "id" to idProduto)
        }
    }

    override fun onResume() {
        super.onResume()

        val adapter = list_view_produtos.adapter as ProdutoAdapter

        database.use{
            //efetuando uma consulta no banco de dados
            select("produtos").exec {

                //Criando o parser que montará o objeto produto
                val parser = rowParser { id: Int, nome: String,
                                         quantidade: Int,
                                         valor: Double,
                                         foto: ByteArray? ->
                    //colunas do banco de dados

                    //montagem do objeto Produto com dados do banco
                    Produto(id, nome, quantidade, valor, foto?.toBitmap())
                }

                var listaProdutos = parseList(parser)

                //limpando os dados da lista e carregando as novas informações
                adapter.clear()
                adapter.addAll(listaProdutos)

                //efetuando a multiplicação e soma da quantidade e valor
                val soma = listaProdutos.sumByDouble {it.valor * it.quantidade}
                //formatando em formato moeda
                val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                txt_total.text = "TOTAL: ${f.format(soma)}"

            }
        }
    }
}