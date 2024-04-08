import com.gaessena.softwaregamificado.GetPerfilModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetApiReadUserService {
    @GET("readUser.php")
    suspend fun getReadUser(@Query("username") usuario: String): Response<GetPerfilModel>
}
