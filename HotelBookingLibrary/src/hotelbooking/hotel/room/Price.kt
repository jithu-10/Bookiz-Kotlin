package hotelbooking.hotel.room

data class Price(
    val basePrice : Double,
    val maxPrice : Double,
    val listPrice : Double = basePrice){


    init{
        validateBasePrice()
        validateMaxPrice()
        validateListPrice()
    }



    private fun validateBasePrice(){
        if (basePrice < 100) {
            throw Exception("Base Price Should be Greater than " + 100)
        }
    }

    private fun validateMaxPrice(){
        if (maxPrice <= basePrice) {
            throw Exception("Max Price should be greater than Base Price : $basePrice")
        }
    }

    private fun validateListPrice(){
        if (listPrice < basePrice || listPrice > maxPrice) {
            throw Exception("List Price should be greater than base Price and should be lesser than max Price")
        }
    }



}
