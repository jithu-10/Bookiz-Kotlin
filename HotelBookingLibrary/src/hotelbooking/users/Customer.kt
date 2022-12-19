package hotelbooking.users

import hotelbooking.booking.BookingStatus
import hotelbooking.booking.CustomerBookingInterface
import hotelbooking.hotel.HotelCustomerInterface

class Customer internal constructor(override val userData: UserData) : User {

    private val favoriteHotels = LinkedHashSet<HotelCustomerInterface>()
    private val bookings: ArrayList<CustomerBookingInterface> = ArrayList()

    internal fun addBooking(booking : CustomerBookingInterface){
        bookings.add(booking);
    }
    

    fun getBookings() : List<CustomerBookingInterface>{
        return bookings.filter { it.getBookingStatus()== BookingStatus.BOOKED }
    }

    fun getCancelledBookings() : List<CustomerBookingInterface>{
        return bookings.filter { it.getBookingStatus()== BookingStatus.CANCELLED }
    }

    fun addFavoriteHotels(hotel : HotelCustomerInterface){
        favoriteHotels.add(hotel);
    }

    fun removeFavoriteHotels(hotel : HotelCustomerInterface){
        favoriteHotels.remove(hotel)
    }

    fun getFavoriteHotels() : List<HotelCustomerInterface>{
        return ArrayList(favoriteHotels);
    }





}