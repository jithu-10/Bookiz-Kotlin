package hotelbooking.hotel

import hotelbooking.hotel.room.Room
import hotelbooking.hotel.room.RoomAdminView

interface HotelAppAdminInterface : HotelInterface {
    override fun getName() : String
    override fun getID() : Int
    override fun getAddress() : Address
    override fun getPhoneNumber() : Long
    override fun getHotelType() : HotelType
    fun setHotelType()
    fun setHotelType(hotelType: HotelType)
    fun getApprovalStatus() : HotelApprovalStatus
    fun setApprovalStatus(hotelApprovalStatus: HotelApprovalStatus)
    fun getRoomsForAdmin() : List<RoomAdminView>
    fun getTotalNumberOfRooms(): Int
    fun getAmenities() : List<Amenity>
    fun getTotalAmenityPercent() : Int


}