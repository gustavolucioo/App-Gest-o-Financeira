package br.unifor.projetoav3.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.unifor.projetoav3.R
import br.unifor.projetoav3.database.MoneyDAO
import br.unifor.projetoav3.model.Money
import br.unifor.projetoav3.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CadastrarReceitaActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mReceitaName: EditText
    private lateinit var mReceitaValor: EditText
    private lateinit var mReceitaAdicionar: Button

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var receitaDAO: MoneyDAO

    private var mUserId = - 1
    private var mReceitaId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_receita)

        receitaDAO = DatabaseUtil.getInstance(applicationContext).getMoneyDAO()

        mReceitaName = findViewById(R.id.receita_editText_name)
        mReceitaValor = findViewById(R.id.receita_editText_valor)
        mReceitaAdicionar = findViewById(R.id.ReceitaAdd_button)

        mReceitaAdicionar.setOnClickListener(this)

        mUserId = intent.getIntExtra("userId", -1)
        mReceitaId = intent.getIntExtra("receitaId", -1)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ReceitaAdd_button -> {
                if(mReceitaId == - 1){
                    val name = mReceitaName.text.toString()
                    val valor = mReceitaValor.text.toString().toDouble()

                    if(name.isEmpty()){
                        mReceitaName.error = "Canto vázio"
                        return
                    }
                    val receita = Money(
                            name = name,
                            valor = valor,
                            isReceita = true,
                            userID = mUserId
                    )
                    Log.i("SALARIO", receita.valor.toString())
                    GlobalScope.launch {
                        receitaDAO.insert(receita)
                        handler.post{
                            Toast.makeText(
                                    applicationContext,
                                    "Receita adicionada com sucesso",
                                    Toast.LENGTH_SHORT).show()

                            finish()
                        }
                    }
                }

            }
        }
    }
}