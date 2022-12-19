package hotelbooking.users

import hotelbooking.db.AmenityDB
import hotelbooking.db.BookingDB
import hotelbooking.db.HotelDB
import hotelbooking.db.LocationDB
import hotelbooking.booking.BookingInterface
import hotelbooking.hotel.Amenity
import hotelbooking.hotel.Hotel
import hotelbooking.hotel.HotelAppAdminInterface
import hotelbooking.hotel.HotelApprovalStatus

class AppAdmin internal constructor(): User {
    override val userData: UserData = UserData("admin",1234567890,"admin@bookiz.com")

    fun approveHotel(hotel : HotelAppAdminInterface){
        hotel.setApprovalStatus(HotelApprovalStatus.APPROVED)
        LocationDB.addLocation(hotel.getAddress().city)
        LocationDB.addLocation(hotel.getAddress().locality);
    }

    fun rejectHotel(hotel : HotelAppAdminInterface){
        if(hotel.getApprovalStatus()== HotelApprovalStatus.ON_PROCESS) {
            hotel.setApprovalStatus(HotelApprovalStatus.REJECTED);
        }
        else{
            hotel.setApprovalStatus(HotelApprovalStatus.REMOVED);
            LocationDB.removeLocation(hotel.getAddress().city)
            LocationDB.removeLocation(hotel.getAddress().locality);
        }
    }

    fun removeHotel(hotel : HotelAppAdminInterface){
        LocationDB.removeLocation(hotel.getAddress().locality)
        LocationDB.removeLocation(hotel.getAddress().city)
        hotel.setApprovalStatus(HotelApprovalStatus.REMOVED)
    }

    fun getRegisteredHotels() : List<HotelAppAdminInterface>{
        return HotelDB.getHotels()
            .filter { hotel : Hotel -> hotel.getApprovalStatus()== HotelApprovalStatus.APPROVED };
    }

    fun getApprovalRequestedHotels(): List<HotelAppAdminInterface> {
        return HotelDB.getHotels()
            .filter { hotel: Hotel -> hotel.getApprovalStatus() == HotelApprovalStatus.ON_PROCESS || hotel.getApprovalStatus() == HotelApprovalStatus.REMOVED_RE_PROCESS }
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

    fun getAllBookings() : List<BookingInterface>{
        return BookingDB.getBookings()
    }
}
