package br.unifor.projetoav3.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import br.unifor.projetoav3.R
import br.unifor.projetoav3.database.UserDAO
import br.unifor.projetoav3.model.User
import br.unifor.projetoav3.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var userDAO: UserDAO

    private lateinit var mUsername:EditText
    private lateinit var mFullname:EditText
    private lateinit var mPhonenumber:EditText
    private lateinit var mEmail:EditText
    private lateinit var mPassword:EditText
    private lateinit var mPasswordconfirmation:EditText
    private lateinit var mSignup: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()

        mUsername = findViewById(R.id.register_editttext_username)
        mFullname = findViewById(R.id.register_editttext_fullname)
        mPhonenumber = findViewById(R.id.register_editttext_phonenumber)
        mEmail = findViewById(R.id.register_editttext_email)
        mPassword = findViewById(R.id.register_editttext_password)
        mPasswordconfirmation = findViewById(R.id.register_editttext_passwordconfirmation)

        mSignup = findViewById(R.id.register_button_signup)
        mSignup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register_button_signup -> {
                val username = mUsername.text.toString()
                val fullname = mFullname.text.toString()
                val phonenumber = mPhonenumber.text.toString()
                val email = mEmail.text.toString()
                val password = mPassword.text.toString()
                val passwordConfirmation = mPasswordconfirmation.text.toString()

                var isFormFilled = true

                if(username.isEmpty()){
                    mUsername.error = "Preencha este canto"
                    isFormFilled = false
                }
                if(fullname.isEmpty()){
                    mFullname.error = "Preencha este canto"
                    isFormFilled = false
                }
                if(phonenumber.isEmpty()){
                    mPhonenumber.error = "Preencha este canto"
                    isFormFilled = false
                }
                if(email.isEmpty()){
                    mEmail.error = "Preencha este canto"
                    isFormFilled = false
                }
                if(password.isEmpty()){
                    mPassword.error = "Preencha este canto"
                    isFormFilled = false
                }
                if(passwordConfirmation.isEmpty()){
                    mPasswordconfirmation.error = "Preencha este canto"
                    isFormFilled = false
                }

                if(isFormFilled){

                    if(password != passwordConfirmation){
                        mPasswordconfirmation.error = "As senhas são diferentes"
                        return
                    }
                    GlobalScope.launch {

                        val handler = Handler(Looper.getMainLooper())

                        val user = userDAO.findByEmail(email)

                        if(user == null){
                            val user  = User(
                                username = username,
                                fullName = fullname,
                                phonenumber = phonenumber,
                                email = email,
                                password = BCrypt.withDefaults().hashToString(12, password.toCharArray())
                            )
                            userDAO.insert(user)

                            handler.post{
                                Toast.makeText(
                                    applicationContext,
                                    "Usuário adicionado com sucesso",
                                    Toast.LENGTH_SHORT).show()

                                finish()
                            }
                        }
                        else{
                            handler.post{
                                mEmail.error =  "Esse email já foi cadastrado"
                            }

                        }


                    }
                }
            }
        }
    }
}