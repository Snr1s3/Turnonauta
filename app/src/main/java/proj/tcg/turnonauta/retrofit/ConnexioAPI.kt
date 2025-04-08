package proj.tcg.turnonauta.retrofit

import android.annotation.SuppressLint
import android.util.Log
import okhttp3.OkHttpClient
import proj.tcg.turnonauta.models.NewUser
import proj.tcg.turnonauta.models.Torneig
import proj.tcg.turnonauta.models.UpdateNameRequest
import proj.tcg.turnonauta.models.Usuaris
import proj.tcg.turnonauta.models.UsuarisAmbPunts
import proj.tcg.turnonauta.models.UsuarisStatistics
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    @GET("/users/login")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Int

    @GET("/users/get_by_id")
    suspend fun getUserById(
        @Query("user_id") userId: Int
    ): Usuaris

    @GET("/users/user_statistics")
    suspend fun getStatistic(
        @Query("user_id") userId: Int
    ): UsuarisStatistics

    @GET("/tournaments/tournaments_played")
    suspend fun getTournamentsPlayed(
        @Query("user_id") userId: Int
    ): List<Torneig>

    @GET("/users/users_in_tournament")
    suspend fun getUsersTournament(
        @Query("torneig_id") torneigId: Int
    ): List<UsuarisAmbPunts>

    @GET("/tournaments/tournament_by_id")
    suspend fun getTournamentById(
        @Query("torneig_id") torneigId: Int
    ): Torneig

    @DELETE("/users/delete_by_id")
    suspend fun deleteUsers(
        @Query("user_id")userId: Int
    ): Boolean

    @GET("/users/check_username")
    suspend fun checkUsernameExists(
        @Query("username") username: String
    ): Boolean

    @PUT("/users/update_name/{id}")
    suspend fun updateUserName(
        @Path("id") userId: Int,
        @Body updateNameRequest: UpdateNameRequest
    ): Usuaris


    @POST("/users/add_user")
    suspend fun registerUser(
        @Body user: NewUser
    ): Usuaris
}

class ConnexioAPI {
    companion object {
        private var mAPI: ApiService? = null

        @Synchronized
        fun api(): ApiService {
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
        val trustAllCerts = arrayOf<TrustManager>(@SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
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
        builder.hostnameVerifier { _, _ -> true }
        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}