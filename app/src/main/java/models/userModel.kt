package models

class UserModel(pDetails: pDetails ?=null) {
    private val personalDetails = pDetails("","","")
    fun getPDetails() : pDetails{
        return personalDetails
    }
}