package hotelbooking.hotel

data class Amenity(
    val name : String,
    val points : Int) {

    val id : Int = generateId();


    companion object{
        private var id_helper = 0;

        fun generateId() : Int{
            return ++id_helper;
        }
    }



}
