package hotelbooking.hotel.room

interface RoomHotelView {
    fun getRoomCapacity() : Int
    fun getRoomPrice() : Price
    fun setRoomPrice(roomPrice : Price)
    fun getBedPrice() : Price
    fun setBedPrice(bedPrice : Price)
}