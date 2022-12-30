package hotelbooking.booking

import hotelbooking.hotel.HotelPanel
import hotelbooking.users.UserData
import java.util.*

open class BookingPanel internal constructor(internal val booking : BookingDetails){

    fun getBookingID() : Int{
        return booking.getBookingID()
    }

    fun getCheckInDate() : Date{
        return booking.getCheckInDate()
    }
    fun getCheckOutDate() : Date{
        return booking.getCheckOutDate()
    }
    fun getCustomerData() : UserData{
        return booking.getCustomerData()
    }
    fun getHotel() : HotelPanel{
        return booking.getHotel()
    }

}