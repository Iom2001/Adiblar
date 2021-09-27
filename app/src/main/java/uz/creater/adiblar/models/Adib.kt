package uz.creater.adiblar.models

import java.io.Serializable

class Adib: Serializable {
    var name: String? = null
    var years: String? = null
    var imageUri: String? = null
    var desc: String? = null
    var type: String? = null

    constructor()

    constructor(name: String?, years: String?, imageUri: String?, desc: String?, type: String?) {
        this.name = name
        this.years = years
        this.imageUri = imageUri
        this.desc = desc
        this.type = type
    }
}