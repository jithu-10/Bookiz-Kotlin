package hotelbooking.booking

import hotelbooking.hotel.room.Room

interface HotelBookingInterface : BookingInterface {
    fun getNoOfRoomsBooked() : Int
    fun getNoOfGuestsInEachRoom() : List<Int>
    fun getBookedRooms() : List<Room>
    fun getPaymentStatus() : Boolean
    fun getTotalPrice() : Double
}