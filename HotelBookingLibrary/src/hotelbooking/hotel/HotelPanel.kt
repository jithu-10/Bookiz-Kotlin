package hotelbooking.hotel


open class HotelPanel  internal constructor(internal val hotel : Hotel){
    fun getName() : String{
        return hotel.getName()
    }
    fun getID() : Int{
        return hotel.getID()
    }
    fun getHotelType() : HotelType{
        return hotel.getHotelType()
    }
    fun getPhoneNumber() : Long{
        return  hotel.getPhoneNumber()
    }
    fun getAddress() : Address{
        return hotel.getAddress()
    }
}