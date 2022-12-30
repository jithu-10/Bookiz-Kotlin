package hotelbooking.users

import hotelbooking.booking.BookingStatus
import hotelbooking.booking.CustomerBookingPanel
import hotelbooking.hotel.HotelCustomerPanel

class Customer internal constructor(override val userData: UserData) : User {

    private val favoriteHotels = LinkedHashSet<HotelCustomerPanel>()
    private val bookings: ArrayList<CustomerBookingPanel> = ArrayList()

    internal fun addBooking(booking : CustomerBookingPanel){
        bookings.add(booking);
    }
    

    fun getBookings() : List<CustomerBookingPanel>{
        return bookings.filter { it.getBookingStatus()== BookingStatus.BOOKED }
    }

    fun getCancelledBookings() : List<CustomerBookingPanel>{
        return bookings.filter { it.getBookingStatus()== BookingStatus.CANCELLED }
    }

    fun addFavoriteHotels(hotel : HotelCustomerPanel){
        favoriteHotels.add(hotel);
    }

    fun removeFavoriteHotels(hotel : HotelCustomerPanel){
        favoriteHotels.remove(hotel)
    }

    fun getFavoriteHotels() : List<HotelCustomerPanel>{
        return ArrayList(favoriteHotels);
    }





}