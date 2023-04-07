package models

class pDetails(private var name: String? = null, private var email: String? = null,
               private var photo: String? = null
){

    fun getName():String? {
        return name
    }
    fun getEmail():String? {
        return email
    }
    fun getPhoto():String? {
        return photo
    }
}