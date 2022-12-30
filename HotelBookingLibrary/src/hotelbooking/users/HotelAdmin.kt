package hotelbooking.users

import hotelbooking.db.HotelDB
import hotelbooking.hotel.*
import hotelbooking.hotel.Hotel

class HotelAdmin internal constructor(override val userData: UserData) : User {
    private val hotelsOwned : ArrayList<HotelAdminPanel> = ArrayList();
    fun getHotelsOwned(): List<HotelAdminPanel>{
        return hotelsOwned
    }



    fun registerHotel(userData: UserData, name : String, address : Address) : HotelAdminPanel {
        val hotel : Hotel = Hotel(userData,name, address)
        hotelsOwned.add(hotel.getHotelAdminInterface())
        HotelDB.addHotel(hotel)
        return hotel.getHotelAdminInterface()
    }




    fun reRegisterHotel(hotel : HotelAdminPanel){
        if (hotel.getApprovalStatus() === HotelApprovalStatus.REJECTED) {
            hotel.hotel.getHotelAppAdminInterface().setApprovalStatus(HotelApprovalStatus.ON_PROCESS)
        } else if (hotel.getApprovalStatus() === HotelApprovalStatus.REMOVED) {
            hotel.hotel.getHotelAppAdminInterface().setApprovalStatus(HotelApprovalStatus.REMOVED_RE_PROCESS)
        }
    }


}