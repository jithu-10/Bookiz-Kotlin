package hotelbooking.booking


import hotelbooking.hotel.room.RoomCustomerView

interface HotelBookingInterface : BookingInterface {
    fun getNoOfRoomsBooked() : Int
    fun getNoOfGuestsInEachRoom() : List<Int>
    fun getBookedRooms() : List<RoomCustomerView>
    fun getPaymentStatus() : Boolean
    fun getTotalPrice() : Double
}