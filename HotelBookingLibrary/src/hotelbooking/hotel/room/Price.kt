package hotelbooking.hotel.room

data class Price(
    val basePrice : Double,
    val maxPrice : Double){

    init{
        validateBasePrice()
        validateMaxPrice()
    }

    private var listPrice : Double = basePrice;

    fun setListPrice(listPrice: Double){
        if (listPrice < basePrice || listPrice > maxPrice) {
            throw Exception("List Price should be greater than base Price and should be lesser than max Price")
        }
    }

    fun getListPrice() : Double{
        return listPrice
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


}
