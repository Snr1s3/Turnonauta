package proj.tcg.turnonauta.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date


data class Usuaris(
    val id_usuaris: Int? = null,
    val rol: Int? = null,
    val username: String,
    val email: String,
    val bio: String? = null,
    val telefon: String? = null,
    val contrasenya: String,
    val rang: Int? = null,
    val data_de_registre: String? = null
)

data class UsuarisAmbPunts(
    val username: String,
    val punts: Int
)

data class NewUser(
    val username: String,
    val email: String,
    val phone: String,
    val password: String
)

data class Torneig(
    @SerializedName("id_torneig") val idTorneig: Int?,
    @SerializedName("nom") val nom: String,
    @SerializedName("joc") val joc: String,
    @SerializedName("usuari_organitzador") val usuariOrganitzador: Int,
    @SerializedName("competitiu") val competitiu: Boolean,
    @SerializedName("virtual") val virtual: Boolean,
    @SerializedName("format") val format: String?,
    @SerializedName("premi") val premi: String?,
    @SerializedName("num_jugadors") val numJugadors: Int?,
    @SerializedName("data_d_inici") val dataDInici: Date,
    @SerializedName("data_final") val dataFinal: Date?
)

data class UsuarisStatistics(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("rounds_played") val roundsPlayed: Int,
    @SerializedName("rounds_won") val roundsWon: Int,
    @SerializedName("tournaments_played") val tournamentsPlayed: Int,
    @SerializedName("tournaments_won") val tournamentsWon: Int
)

data class UpdateNameRequest(
    val username: String
)

interface OnNameUpdatedListener {
    fun onNameUpdated(newName: String)
}

