package hotelbooking.hotel

interface HotelInterface {
    fun getName() : String
    fun getID() : Int
    fun getHotelType() : HotelType
    fun getPhoneNumber() : Long
    fun getAddress() : Address
}