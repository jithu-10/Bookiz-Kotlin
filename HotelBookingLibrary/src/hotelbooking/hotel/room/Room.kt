package hotelbooking.hotel.room

internal class Room(
    private val roomCapacity : Int,
    private var roomPrice : Price,
    private var bedPrice : Price,
) {
    
    private val roomCustomerPanel : RoomCustomerPanel = RoomCustomerPanel(this)
    private val roomHotelPanel : RoomHotelPanel = RoomHotelPanel(this)
    private val roomAdminPanel : RoomAdminPanel = RoomAdminPanel(this)

    fun getRoomCapacity(): Int {
        return roomCapacity
    }

    fun getRoomPrice() : Price {
        return roomPrice
    }

    fun setRoomPrice(roomPrice: Price) {
        this.roomPrice=roomPrice
    }

    fun setRoomListPrice(listPrice : Double){
        roomPrice = roomPrice.copy( listPrice = listPrice)
    }

    fun setBedListPrice(listPrice: Double){
        bedPrice = bedPrice.copy( listPrice = listPrice)
    }




    fun getBedPrice() : Price{
        return bedPrice
    }

    fun setBedPrice(bedPrice: Price) {
        this.bedPrice=bedPrice
    }

    fun getRoomCustomerPanel() : RoomCustomerPanel{
        return roomCustomerPanel
    }

    fun getRoomAdminPanel() : RoomAdminPanel{
        return roomAdminPanel
    }

    fun getRoomHotelPanel() : RoomHotelPanel{
        return roomHotelPanel
    }

}
