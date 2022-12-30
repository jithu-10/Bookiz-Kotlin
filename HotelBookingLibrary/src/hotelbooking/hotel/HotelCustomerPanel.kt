package hotelbooking.hotel



class HotelCustomerPanel internal constructor(hotel : Hotel) : HotelPanel(hotel){
    fun getAmenities(): List<Amenity> {
        return hotel.getAmenities()
    }
}