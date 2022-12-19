package hotelbooking.hotel

import hotelbooking.booking.HotelBookingInterface
import hotelbooking.hotel.room.Room
import java.util.*

interface HotelAdminInterface : HotelInterface {
    override fun getName() : String
    override fun getID() : Int
    override fun getAddress() : Address
    override fun getPhoneNumber() : Long
    override fun getHotelType() : HotelType
    fun setHotelType()
    fun setHotelType(hotelType: HotelType)
    fun getApprovalStatus() : HotelApprovalStatus
    fun getRooms() : List<Room>
    fun getTotalNumberOfRooms(): Int
    fun addRoom(room : Room)
    fun removeRoom(room : Room)
    fun getAmenities() : List<Amenity>
    fun getAllAmenities() : List<Amenity>
    fun addAmenity(amenity: Amenity)
    fun removeAmenity(amenity: Amenity)
    fun getBookings(): List<HotelBookingInterface>
    fun getNoOfRoomsBookedByDate(date: Date): Int
}