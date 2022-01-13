package br.unifor.projetoav3.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.unifor.projetoav3.R
import br.unifor.projetoav3.database.MoneyDAO
import br.unifor.projetoav3.model.Money
import br.unifor.projetoav3.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CadastrarDespesaActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mDespesaName:EditText
    private lateinit var mDespesaValor:EditText
    private lateinit var mDespesaAdicionar:Button

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var despesaDAO:MoneyDAO

    private var mUserId = - 1
    private var mDespesaId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_despesa)

        despesaDAO = DatabaseUtil.getInstance(applicationContext).getMoneyDAO()

        mDespesaName = findViewById(R.id.despesa_add_edittext_name)
        mDespesaValor = findViewById(R.id.despesa_add_edittext_valor)
        mDespesaAdicionar = findViewById(R.id.despesa_add_button)

        mDespesaAdicionar.setOnClickListener(this)

        mUserId = intent.getIntExtra("userId", -1)
        mDespesaId = intent.getIntExtra("despesaId", -1)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.despesa_add_button -> {
                if(mDespesaId == - 1){
                    val name = mDespesaName.text.toString()
                    val valor = mDespesaValor.text.toString().toDouble()

                    if(name.isEmpty()){
                        mDespesaName.error = "Canto vázio"
                        return
                    }
                    val despesa = Money(
                            name = name,
                            valor = valor,
                            isReceita = false,
                            userID = mUserId
                    )
                    Log.i("App", despesa.valor.toString())
                    GlobalScope.launch {
                        despesaDAO.insert(despesa)
                        handler.post{
                            Toast.makeText(
                                applicationContext,
                                "Despesa adicionada com sucesso",
                                Toast.LENGTH_SHORT).show()

                            finish()
                        }
                    }
                }

            }
        }
    }
}