package hotelbooking.users

import hotelbooking.hotel.Address
import hotelbooking.hotel.HotelApprovalStatus
import hotelbooking.db.HotelDB
import hotelbooking.hotel.Hotel
import hotelbooking.hotel.HotelAdminInterface

class HotelAdmin internal constructor(override val userData: UserData) : User {
    private val hotelsOwned : ArrayList<HotelAdminInterface> = ArrayList();
    fun getHotelsOwned(): List<HotelAdminInterface>{
        return hotelsOwned
    }



    fun registerHotel(userData: UserData, name : String, address : Address) : HotelAdminInterface {
        val hotel : Hotel = Hotel(userData,name, address)
        hotelsOwned.add(hotel)
        HotelDB.addHotel(hotel)
        return hotel
    }




    fun reRegisterHotel(hotel : HotelAdminInterface){
        if (hotel.getApprovalStatus() === HotelApprovalStatus.REJECTED) {
            (hotel as Hotel).setApprovalStatus(HotelApprovalStatus.ON_PROCESS)
        } else if (hotel.getApprovalStatus() === HotelApprovalStatus.REMOVED) {
            (hotel as Hotel).setApprovalStatus(HotelApprovalStatus.REMOVED_RE_PROCESS)
        }
    }


}