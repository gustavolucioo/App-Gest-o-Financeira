package br.unifor.projetoav3.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room
import br.unifor.projetoav3.R
import br.unifor.projetoav3.adapter.MoneyAdapter
import br.unifor.projetoav3.database.GestaoFinanceiraDatabase
import br.unifor.projetoav3.database.MoneyDAO
import br.unifor.projetoav3.database.UserDAO
import br.unifor.projetoav3.model.Money
import br.unifor.projetoav3.model.User
import br.unifor.projetoav3.model.UserWithMoneys
import br.unifor.projetoav3.util.DatabaseUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var userDAO: UserDAO
    private lateinit var mReceitaAdd:FloatingActionButton
    private lateinit var mDespesaAdd:FloatingActionButton

    private lateinit var mDespesasText: TextView
    private var stringDespesas = "";
    private var mUserId = -1
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var moneyDAO: MoneyDAO;

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mUserWithMoneys: UserWithMoneys

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.main_recyclerview_moneys)

        mReceitaAdd = findViewById(R.id.main_floatinactionbutton_addReceita)
        mReceitaAdd.setOnClickListener(this)

        mDespesaAdd = findViewById(R.id.main_floatinactionbutton_addDespesas)
        mDespesaAdd.setOnClickListener(this)

        mDespesasText = findViewById(R.id.despesas_textview)


        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()

        if (intent != null) {
             mUserId = intent.getIntExtra("userId", -1)
        }

//        GlobalScope.launch {
//            userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()
//            moneyDAO = DatabaseUtil.getInstance(applicationContext).getMoneyDAO()
//            if (intent != null) {
//                val mUserId = intent.getIntExtra("userId", -1)
//                if(mUserId != -1){
//                    val money1 = Money(name = "ifood", isReceita = true, valor = 500.0 ,  userID = mUserId)
//                    val money2 = Money(name = "rappi", isReceita = false, valor = 200.0 ,  userID = mUserId)
//                    moneyDAO.insert(money1)
//                    moneyDAO.insert(money2)
//                    mUserWithMoneys = userDAO.findUserWithMoneys(mUserId)
//                }
//            }
//            val moneyAdapter = MoneyAdapter(mUserWithMoneys.moneys)
//            val llm = LinearLayoutManager(applicationContext)
//            mRecyclerView = findViewById(R.id.main_recyclerview_moneys)
//            mRecyclerView.apply {
//                adapter = moneyAdapter
//                layoutManager = llm
//            }
//        }





    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            if (mUserId != -1) {
                mUserWithMoneys = userDAO.findUserWithMoneys(mUserId)
                handler.post {
                    val moneyAdapter = MoneyAdapter(mUserWithMoneys.moneys)
                    val llm = LinearLayoutManager(applicationContext)

                    mRecyclerView.apply {
                        adapter = moneyAdapter
                        layoutManager = llm
                    }
                }


            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.main_floatinactionbutton_addReceita -> {
                val it = Intent(applicationContext, CadastrarReceitaActivity::class.java)
                it.putExtra("userId", mUserId)
                startActivity(it)
            }
            R.id.main_floatinactionbutton_addDespesas -> {
                val it = Intent(applicationContext,CadastrarDespesaActivity::class.java)
                it.putExtra("userId", mUserId)
                startActivity(it)
            }
        }
    }
}