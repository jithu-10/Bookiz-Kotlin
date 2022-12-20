package hotelbooking.hotel.room

internal data class Room(
    private val roomCapacity : Int,
    private var roomPrice : Price,
    private var bedPrice : Price
) : RoomCustomerView,RoomHotelView,RoomAdminView{

    val id : Int = generateId();

    companion object{
        private var id_helper=0;

        fun generateId() : Int{
            return ++id_helper;
        }
    }

    override fun getRoomCapacity(): Int {
        return roomCapacity
    }

    override fun getRoomPrice() : Price {
        return roomPrice
    }

    override fun setRoomPrice(roomPrice: Price) {
        this.roomPrice=roomPrice
    }

    override fun setRoomListPrice(listPrice : Double){
        roomPrice = roomPrice.copy( listPrice = listPrice)
    }

    override fun setBedListPrice(listPrice: Double){
        bedPrice = bedPrice.copy( listPrice = listPrice)
    }




    override fun getBedPrice() : Price{
        return bedPrice
    }

    override fun setBedPrice(bedPrice: Price) {
        this.bedPrice=bedPrice
    }

}
