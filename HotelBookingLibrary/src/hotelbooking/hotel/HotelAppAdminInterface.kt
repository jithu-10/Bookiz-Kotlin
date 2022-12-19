package hotelbooking.hotel

import hotelbooking.hotel.room.Room

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
    fun getRooms() : List<Room>
    fun getTotalNumberOfRooms(): Int
    fun getAmenities() : List<Amenity>
    fun getTotalAmenityPercent() : Int


}