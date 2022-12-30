package hotelbooking.hotel.room


class RoomHotelPanel internal constructor(internal val room : Room){

    fun getRoomCapacity() : Int{
        return room.getRoomCapacity()
    }

    fun setRoomPrice(roomPrice : Price){
        room.setRoomPrice(roomPrice)
    }
    fun setBedPrice(bedPrice : Price){
        room.setBedPrice(bedPrice)
    }
    fun getRoomPrice() : Price{
        return room.getRoomPrice()
    }

    fun getBedPrice() : Price{
        return room.getBedPrice()
    }
}