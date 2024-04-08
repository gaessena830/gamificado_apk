package com.gaessena.softwaregamificado

import com.google.gson.annotations.SerializedName

data class GetPerfilModel (
    @SerializedName("nombre")
        var nombre:String,
    @SerializedName("apellidos")
        var apellidos : String,
    @SerializedName("identificacion")
        var identificacion : String,
    @SerializedName("correo")
        var correo : String,
    @SerializedName("telefono")
        var telefono: String,
    @SerializedName("username")
        var username :String,
    @SerializedName("rol")
        var rol : String,
    @SerializedName("grado")
        var grado : String,
    @SerializedName("fechaIngreso")
        var fechaIngreso : String,
    @SerializedName("nivelIngles")
        var nivelIngles : String,
    @SerializedName("nivelMatematicas")
        var nivelMatematicas : String
)