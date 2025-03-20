package proj.tcg.turnonauta.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate


data class Usuaris(
    val id_usuaris: Int? = null,
    val rol: Int? = null,
    val username: String,
    val email: String,
    val bio: String? = null,
    val telefon: String? = null,
    val contrasenya: String,
    val rang: Int? = null,
    val data_de_registre: LocalDate? = null
)

data class UsuarisStatistics(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("rounds_played") val roundsPlayed: Int,
    @SerializedName("rounds_won") val roundsWon: Int,
    @SerializedName("tournaments_played") val tournamentsPlayed: Int,
    @SerializedName("tournaments_won") val tournamentsWon: Int
)