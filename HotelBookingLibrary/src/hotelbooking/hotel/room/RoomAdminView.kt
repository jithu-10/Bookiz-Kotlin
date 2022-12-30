package hotelbooking.hotel.room



class RoomAdminPanel internal constructor(internal val room : Room){
    fun getRoomCapacity() : Int{
        return room.getRoomCapacity()
    }
    fun getRoomPrice() : Price{
        return room.getRoomPrice()
    }
    fun getBedPrice() : Price{
        return room.getBedPrice()
    }
    fun setRoomListPrice(listPrice : Double){
        room.setRoomListPrice(listPrice)
    }
    fun setBedListPrice(listPrice: Double){
        room.setBedListPrice(listPrice)
    }
}