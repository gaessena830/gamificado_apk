package com.gaessena.softwaregamificado

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.gaessena.softwaregamificado.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    val urlBase = "http://softgamificado.mooo.com/gamificado_back/"
    lateinit var binding: ActivityMainBinding
    private lateinit var service: PostApiLoginService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        service = retrofit.create(PostApiLoginService::class.java)

        binding.buttonIngresar.setOnClickListener {

            fetchDataFromServer()
        }

    }


    // Función para realizar la solicitud al servidor
    private fun fetchDataFromServer() {
        val usuario = binding.inputUsername.text.toString()
        val contraseña = binding.inputPass.text.toString()

        val json = JSONObject().apply {
            put("user", usuario)
            put("pass", contraseña)
        }
        // Convertir el objeto JSON en un RequestBody
        val requestBody = json.toString().toRequestBody("application/json".toMediaTypeOrNull())



        lifecycleScope.launch {
            try {
                val response = service.getUserLogin(requestBody)
                println(response)
                println("Status: ${response.status}")
                println("Message: ${response.message}")
                println("Page: ${response.page}")
                val message = response.message
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                if (response.page== "./perfil.html"){
                    //pagina de perfil docente
                    val intent = Intent(this@MainActivity,DocenteActivity::class.java).apply{
                        putExtra("usuario",usuario)

                    }
                    startActivity(intent)
                }else if (response.page== "./perfilestudiante.html"){
                    //perfil estudiante
                    val intent = Intent(this@MainActivity,EstudianteActivity::class.java).apply{
                        putExtra("usuario",usuario)

                    }
                    startActivity(intent)
                }
                } catch (e: Exception) {
                    // Manejar el error si es necesario
                    println("Error al obtener datos del servidor: ${e.message}")
                }
        }
    }


}