package hotelbooking.hotel

interface HotelCustomerInterface : HotelInterface {
    override fun getName() : String
    override fun getID() : Int
    override fun getHotelType() : HotelType
    override fun getPhoneNumber() : Long
    override fun getAddress() : Address
    fun getAmenities() : List<Amenity>

}