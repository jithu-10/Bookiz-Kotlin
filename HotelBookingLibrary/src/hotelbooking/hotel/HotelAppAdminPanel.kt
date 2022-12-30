package hotelbooking.hotel

import hotelbooking.hotel.room.RoomAdminPanel



class HotelAppAdminPanel internal constructor(hotel : Hotel) : HotelPanel(hotel){
    fun setHotelType() {
        hotel.setHotelType()
    }

    fun setHotelType(hotelType: HotelType) {
        hotel.setHotelType(hotelType)
    }

    fun getApprovalStatus(): HotelApprovalStatus {
        return hotel.getApprovalStatus()
    }

    fun setApprovalStatus(hotelApprovalStatus: HotelApprovalStatus) {
        hotel.setApprovalStatus(hotelApprovalStatus)
    }

    fun getRoomsForAdmin(): List<RoomAdminPanel> {
        return hotel.getRoomsForAdmin()
    }

    fun getTotalNumberOfRooms(): Int {
        return hotel.getTotalNumberOfRooms()
    }

    fun getAmenities(): List<Amenity> {
        return hotel.getAmenities()
    }
    fun getTotalAmenityPercent(): Int {
        return hotel.getTotalAmenityPercent()
    }


}