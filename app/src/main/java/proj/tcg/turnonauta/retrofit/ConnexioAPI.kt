package proj.tcg.turnonauta.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import proj.tcg.turnonauta.models.Torneig
import proj.tcg.turnonauta.models.Usuaris
import proj.tcg.turnonauta.models.UsuarisAmbPunts
import proj.tcg.turnonauta.models.UsuarisStatistics
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface ApiService {
    @GET("/login")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Int

    @GET("/user_by_id")
    suspend fun getUserById(
        @Query("user_id") user_id: Int
    ): Usuaris

    @GET("/user_statistics")
    suspend fun getStatistic(
        @Query("user_id") user_id: Int
    ): UsuarisStatistics

    @GET("/tournaments_played")
    suspend fun getTournamentsPlayed(
        @Query("user_id") user_id: Int
    ): List<Torneig>

    @GET("/users_in_tournament")
    suspend fun get_users_in_tournament(
        @Query("torneig_id") torneig_id: Int
    ): List<UsuarisAmbPunts>

    @GET("/tournament_by_id")
    suspend fun getTournamentById(
        @Query("torneig_id")torneigId: Int
    ): Torneig

    @GET("users/check_username")
    suspend fun checkUsernameExists(
        @Query("username") username: String
    ): Response<Boolean>

    @PUT("users/{id}/update_name")
    suspend fun updateUserName(@Path("id") userId: Int, @Body newName: String): Response<Void>

    @POST("register")
    suspend fun registerUser(
        @Query("username") username: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
        @Query("password") password: String
    ): Response<Void>

}

class ConnexioAPI {
    companion object {
        private var mAPI: ApiService? = null

        @Synchronized
        fun API(): ApiService {
            if (mAPI == null) {
                val unsafeClient = getUnsafeOkHttpClient()

                mAPI = Retrofit.Builder()
                    .client(unsafeClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://52.20.160.197:8443")
                    .build()
                    .create(ApiService::class.java)
                Log.d("Retrofit", "Connected")
            }
            return mAPI!!
        }
    }
}


private fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }
        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}