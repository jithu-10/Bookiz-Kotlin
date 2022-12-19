package hotelbooking.users

data class UserData internal constructor(
    val userName : String,
    val phoneNumber : Long,
    val email : String
){
    val id : Int = generateId();

    companion object{
        private var id_helper=0;

        fun generateId() : Int{
            return ++id_helper;
        }
    }
}
