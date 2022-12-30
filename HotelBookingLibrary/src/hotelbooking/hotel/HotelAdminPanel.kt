package hotelbooking.hotel

import hotelbooking.booking.HotelBookingPanel
import hotelbooking.hotel.room.Price
import hotelbooking.hotel.room.RoomHotelPanel
import java.util.*


class HotelAdminPanel internal constructor(hotel : Hotel) : HotelPanel(hotel){
    fun setHotelType(){
        hotel.setHotelType()
    }
    fun setHotelType(hotelType: HotelType){
        hotel.setHotelType(hotelType)
    }
    fun getApprovalStatus() : HotelApprovalStatus{
        return hotel.getApprovalStatus()
    }
    fun getRooms() : List<RoomHotelPanel>{
        return hotel.getRooms()
    }
    fun getTotalNumberOfRooms(): Int{
        return hotel.getTotalNumberOfRooms()
    }
    fun addRoom(maxGuest : Int, roomPrice : Price, bedPrice : Price){
        hotel.addRoom(maxGuest,roomPrice,bedPrice)
    }
    fun removeRoom(room : RoomHotelPanel){
        hotel.removeRoom(room)
    }
    fun getAmenities() : List<Amenity>{
        return hotel.getAmenities()
    }
    fun getAllAmenities() : List<Amenity>{
        return hotel.getAllAmenities()
    }
    fun addAmenity(amenity: Amenity){
        hotel.addAmenity(amenity)
    }
    fun removeAmenity(amenity: Amenity){
        hotel.removeAmenity(amenity)
    }
    fun getBookings(): List<HotelBookingPanel>{
        return hotel.getBookings()
    }
    fun getNoOfRoomsBookedByDate(date: Date): Int{
        return hotel.getNoOfRoomsBookedByDate(date)
    }
}