package hotelbooking.users

import hotelbooking.db.AmenityDB
import hotelbooking.db.BookingDB
import hotelbooking.db.HotelDB
import hotelbooking.db.LocationDB
import hotelbooking.booking.BookingPanel
import hotelbooking.hotel.Amenity
import hotelbooking.hotel.Hotel
import hotelbooking.hotel.HotelAppAdminPanel
import hotelbooking.hotel.HotelApprovalStatus

class AppAdmin internal constructor(): User {
    override val userData: UserData = UserData("admin",1234567890,"admin@bookiz.com")

    fun approveHotel(hotel : HotelAppAdminPanel){
        hotel.setApprovalStatus(HotelApprovalStatus.APPROVED)
        LocationDB.addLocation(hotel.getAddress().city)
        LocationDB.addLocation(hotel.getAddress().locality);
    }

    fun rejectHotel(hotel : HotelAppAdminPanel){
        if(hotel.getApprovalStatus()== HotelApprovalStatus.ON_PROCESS) {
            hotel.setApprovalStatus(HotelApprovalStatus.REJECTED);
        }
        else{
            hotel.setApprovalStatus(HotelApprovalStatus.REMOVED);
            LocationDB.removeLocation(hotel.getAddress().city)
            LocationDB.removeLocation(hotel.getAddress().locality);
        }
    }

    fun removeHotel(hotel : HotelAppAdminPanel){
        LocationDB.removeLocation(hotel.getAddress().locality)
        LocationDB.removeLocation(hotel.getAddress().city)
        hotel.setApprovalStatus(HotelApprovalStatus.REMOVED)
    }

    fun getRegisteredHotels() : List<HotelAppAdminPanel>{
        val hotels : MutableList<HotelAppAdminPanel> = mutableListOf()
        HotelDB.getHotels()
            .filter { hotel : Hotel -> hotel.getApprovalStatus()== HotelApprovalStatus.APPROVED }.forEach{
                hotels.add(it.getHotelAppAdminInterface())
            }
        return hotels
    }

    fun getApprovalRequestedHotels(): List<HotelAppAdminPanel> {
        val hotels : MutableList<HotelAppAdminPanel> = mutableListOf()
        HotelDB.getHotels()
            .filter { hotel: Hotel -> hotel.getApprovalStatus() == HotelApprovalStatus.ON_PROCESS || hotel.getApprovalStatus() == HotelApprovalStatus.REMOVED_RE_PROCESS }.forEach{
                hotels.add(it.getHotelAppAdminInterface())
            }
        return hotels
    }


    fun addAmenity(amenity : Amenity){
        AmenityDB.addAmenity(amenity)
    }

    fun removeAmenity(amenity: Amenity){
        AmenityDB.removeAmenity(amenity)
    }

    fun getAmenities() : List<Amenity>{
        return AmenityDB.getAmenities()
    }

    fun getAllBookings() : List<BookingPanel>{
        return BookingDB.getBookings()
    }
}
