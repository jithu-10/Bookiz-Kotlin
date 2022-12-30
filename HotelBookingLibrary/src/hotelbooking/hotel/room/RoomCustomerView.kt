package hotelbooking.hotel.room


class RoomCustomerPanel internal constructor(internal val room : Room){
    fun getRoomPrice() : Price{
        return room.getRoomPrice()
    }

    fun getBedPrice() : Price{
        return room.getBedPrice()
    }
}