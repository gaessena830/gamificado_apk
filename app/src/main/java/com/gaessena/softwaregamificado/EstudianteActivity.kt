package com.gaessena.softwaregamificado

import GetApiReadUserService
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gaessena.softwaregamificado.databinding.ActivityEstudianteBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EstudianteActivity : AppCompatActivity() {
    val urlBase = "http://softgamificado.mooo.com/gamificado_back/"
    lateinit var binding: ActivityEstudianteBinding
    private lateinit var service: GetApiReadUserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEstudianteBinding.inflate(layoutInflater)
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
                    val textGrado= findViewById<TextView>(R.id.textGrado)
                    val textFechaIngreso= findViewById<TextView>(R.id.textFechaIngreso)
                    val textNivelIngles= findViewById<TextView>(R.id.textNivelIngles)
                    val textNivelMatem= findViewById<TextView>(R.id.textNivelMatem)
                    if (perfil != null) {
                        textDocente.text= perfil.nombre + " " + perfil.apellidos
                        textIdentif.text=perfil.identificacion
                        texttelef.text= perfil.telefono
                        textCorreo.text= perfil.correo
                        textUsername.text="Bienvenido " + perfil.username
                        textGrado.text= perfil.grado
                        textFechaIngreso.text= perfil.fechaIngreso
                        textNivelIngles.text= perfil.nivelIngles
                        textNivelMatem.text= perfil.nivelMatematicas
                    }
                }
            }
        }
    }
}