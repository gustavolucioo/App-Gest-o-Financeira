package br.unifor.projetoav3.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import br.unifor.projetoav3.R
import br.unifor.projetoav3.database.UserDAO
import br.unifor.projetoav3.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mRegister:TextView

    private lateinit var mLoginPassword:EditText
    private lateinit var mLoginEmail: EditText
    private lateinit var mLoginSingIn:Button

    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()

        mLoginEmail = findViewById(R.id.login_edittext_email)
        mLoginPassword = findViewById(R.id.login_edittext_password)
        mLoginSingIn = findViewById(R.id.login_button_signin)

        mLoginSingIn.setOnClickListener(this)

        mRegister = findViewById(R.id.login_textview_register)
        mRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.login_textview_register -> {
                val it = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(it)
            }
            R.id.login_button_signin ->{
                val email = mLoginEmail.text.toString()
                val password = mLoginPassword.text.toString()

                var isLoginForeFilled = true

                if(email.isEmpty()) {
                    mLoginEmail.error = "Este campo deve ser preenchido"
                    isLoginForeFilled = false
                }
                if(password.isEmpty()) {
                    mLoginPassword.error = "Este campo deve ser preenchido"
                    isLoginForeFilled = false
                }
                if(isLoginForeFilled){

                    GlobalScope.launch {

                        val user = userDAO.findByEmail(email)

                        if(user != null){
                            // Verificando se a senha está armazenada no banco de dados
                            if(BCrypt.verifyer().verify(password.toCharArray(), user.password).verified){
                                val it = Intent(applicationContext, MainActivity::class.java)
                                it.putExtra("userId", user.id)
                                startActivity(it)
                                finish()
                            }
                            else{
                            showToastMesseage()
                            }
                        }
                        else{
                            showToastMesseage()
                        }
                        Log.i("App", "TESTE")
                    }

                }
            }
        }
    }
    private fun showToastMesseage(){
        val handler = Handler(Looper.getMainLooper())

        handler.post {
            Toast.makeText(
                applicationContext,
                "Usuário ou senha inválidos",
                Toast.LENGTH_SHORT).show()

        }

    }
}