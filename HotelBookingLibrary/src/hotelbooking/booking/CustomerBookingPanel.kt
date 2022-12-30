package hotelbooking.booking


class CustomerBookingPanel internal constructor(booking : BookingDetails) : BookingPanel(booking){
    fun getNoOfRoomsBooked() : Int{
        return booking.getNoOfRoomsBooked()
    }
    fun getPaymentStatus() : Boolean{
        return booking.getPaymentStatus()
    }
    fun getBookingStatus() : BookingStatus{
        return booking.getBookingStatus()
    }
    fun getTotalPrice() : Double{
        return booking.getTotalPrice()
    }

}


