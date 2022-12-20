package hotelbooking.hotel.room

interface RoomAdminView {
    fun getRoomCapacity() : Int
    fun getRoomPrice() : Price
    fun getBedPrice() : Price
    fun setRoomListPrice(listPrice : Double)
    fun setBedListPrice(listPrice: Double)

}