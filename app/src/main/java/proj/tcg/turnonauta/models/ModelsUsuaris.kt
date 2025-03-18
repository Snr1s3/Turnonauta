package proj.tcg.turnonauta.models

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