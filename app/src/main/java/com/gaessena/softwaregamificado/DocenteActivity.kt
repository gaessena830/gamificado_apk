package com.gaessena.softwaregamificado

import GetApiReadUserService
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gaessena.softwaregamificado.databinding.ActivityDocenteBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DocenteActivity : AppCompatActivity() {
    val urlBase = "http://softgamificado.mooo.com/gamificado_back/"
    lateinit var binding: ActivityDocenteBinding
    private lateinit var service: GetApiReadUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDocenteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el usuario de los extras del intent
        var usuario = intent.getStringExtra("usuario") ?: ""
        if (usuario.isNotEmpty()) {
            fetchDataFromServer(usuario)
            //Toast.makeText(this, usuario, Toast.LENGTH_SHORT).show()
            // Manejar el caso donde no se proporciona un usuario

        } else {
            Toast.makeText(this, "No se proporcionó un usuario", Toast.LENGTH_SHORT).show()
            // Manejar el caso donde no se proporciona un usuario
        }
    }
        private fun fetchDataFromServer(user:String){
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // Configura el cliente OkHttpClient
                .build()

            service= retrofit.create(GetApiReadUserService::class.java)
            lifecycleScope.launch {
                val response=service.getReadUser(user)
                if (response.isSuccessful){
                    val perfil = response.body()
                    Toast.makeText(
                        applicationContext,
                        "Nombre: ${perfil?.nombre}, Apellidos: ${perfil?.apellidos}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Aquí puedes utilizar los datos del perfil como desees
                    runOnUiThread {
                        val textDocente= findViewById<TextView>(R.id.textDocente)
                        val textIdentif= findViewById<TextView>(R.id.textIdentif)
                        val texttelef= findViewById<TextView>(R.id.textTelef)
                        val textCorreo= findViewById<TextView>(R.id.textCorreo)
                        val textUsername= findViewById<TextView>(R.id.txtBienvenido)
                        if (perfil != null) {
                            textDocente.text= perfil.nombre + " " + perfil.apellidos
                        }
                        if (perfil != null) {
                            textIdentif.text=perfil.identificacion
                        }
                        if (perfil != null) {
                            texttelef.text= perfil.telefono
                        }
                        if (perfil != null) {
                            textCorreo.text= perfil.correo
                        }
                        if (perfil != null) {
                            textUsername.text="Bienvenido " + perfil.username
                        }
                    }
                }
            }
        }
}