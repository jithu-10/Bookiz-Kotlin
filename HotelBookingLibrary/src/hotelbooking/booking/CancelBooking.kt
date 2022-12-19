package hotelbooking.booking

import hotelbooking.db.BookingDB
import hotelbooking.hotel.Hotel
import hotelbooking.hotel.HotelInterface

class CancelBooking(private val booking : CustomerBookingInterface) {
    init{
        cancelBooking()
    }

    private fun cancelBooking(){

        (booking as BookingDetails).setBookingStatus(BookingStatus.CANCELLED)
        removeBookingFromHotel(booking.getHotel())
        removeBookingFromDB();
    }


    private fun removeBookingFromHotel(hotel : HotelInterface){
        (hotel as Hotel).removeBooking(hotel.getBookingByID(booking.getBookingID()))
    }

    private fun removeBookingFromDB(){
        BookingDB.removeBooking(booking)
    }


}