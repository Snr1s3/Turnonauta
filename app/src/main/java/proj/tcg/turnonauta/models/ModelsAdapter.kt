package proj.tcg.turnonauta.models

data class TornejosJugats(
    val nom: String,
    val codi: Int,
    val joc: String,
    val format: String,
    val jugadors: Int
)

data class UsuariTorneig(
    val image: Int,
    val text: String
)

data class DetallTorneig(
    val nom: String
)