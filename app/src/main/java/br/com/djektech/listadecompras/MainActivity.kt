package br.com.djektech.listadecompras

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.selector
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val produtosAdapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        //implementação do adaptador
        val produtosAdapter = ProdutoAdapter(this)

        //definindo o adaptador da lista
        list_view_produtos.adapter = produtosAdapter

        //excluindo um itens da lista
        list_view_produtos.setOnItemLongClickListener{ adapterView: AdapterView<*>, view: View, position: Int, l: Long ->

            //atualizando registro
            val opcoes = listOf("editar", "excluir")

            val opc_editar = 0
            val opc_excluir = 1

            selector("O que deseja fazer", opcoes) { dialogInterface, position ->
                when (position) {
                    opc_editar -> {
                        alert("Editar").show()
                        //toast("Editar")
                    }

                    opc_excluir -> {
                        //buscando o item clicado
                        val item = produtosAdapter.getItem(position)

                        //removendo o item da lista
                        produtosAdapter.remove(item)

                        //deletando do banco de dados
                        item?.let { deletarProduto(it.id) }

                        toast("Idem deletado com sucesso!")
                    }
                }
            }

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
        //atualizando a soma na Main Activity após remoção de produto
        onResume()
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