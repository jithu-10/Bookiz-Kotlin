package hotelbooking.booking

import hotelbooking.hotel.HotelInterface
import hotelbooking.users.UserData
import java.util.*

interface BookingInterface {

    fun getBookingID() : Int
    fun getCheckInDate() : Date
    fun getCheckOutDate() : Date
    fun getCustomerData() : UserData
    fun getHotel() : HotelInterface

}