package hotelbooking.booking

interface CustomerBookingInterface : BookingInterface {

    fun getNoOfRoomsBooked() : Int
    fun getPaymentStatus() : Boolean
    fun getBookingStatus() : BookingStatus
    fun getTotalPrice() : Double
}


