package hotelbooking.booking

import hotelbooking.db.BookingDB
import hotelbooking.hotel.*

class CancelBooking(private val booking : CustomerBookingPanel) {
    init{
        cancelBooking()
    }

    private fun cancelBooking(){

        booking.booking.setBookingStatus(BookingStatus.CANCELLED)
        removeBookingFromHotel(booking.getHotel())
        removeBookingFromDB();
    }


    private fun removeBookingFromHotel(hotel : HotelPanel){

        hotel.hotel.removeBooking(hotel.hotel.getBookingByID(booking.getBookingID()))
    }

    private fun removeBookingFromDB(){
        BookingDB.removeBooking(booking)
    }


}