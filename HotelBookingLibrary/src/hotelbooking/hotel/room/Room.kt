package hotelbooking.hotel.room

data class Room(
    val roomCapacity : Int,
    var roomPrice : Price,
    var bedPrice : Price
){

    val id : Int = generateId();

    companion object{
        private var id_helper=0;

        fun generateId() : Int{
            return ++id_helper;
        }
    }

}
