package hotelbooking.booking


import hotelbooking.hotel.room.RoomCustomerPanel

class HotelBookingPanel internal constructor(booking : BookingDetails) : BookingPanel(booking){
    fun getNoOfRoomsBooked() : Int{
        return booking.getNoOfRoomsBooked()
    }
    fun getNoOfGuestsInEachRoom() : List<Int>{
        return booking.getNoOfGuestsInEachRoom()
    }
    fun getBookedRooms() : List<RoomCustomerPanel>{
        return booking.getBookedRooms()
    }
    fun getPaymentStatus() : Boolean{
        return booking.getPaymentStatus()
    }
    fun getTotalPrice() : Double{
        return booking.getTotalPrice()
    }
}