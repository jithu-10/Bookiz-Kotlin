package hotelbooking.db

import hotelbooking.booking.BookingInterface
import hotelbooking.hotel.Amenity
import hotelbooking.hotel.Hotel
import hotelbooking.modifyString
import hotelbooking.users.AppAdmin
import hotelbooking.users.User
import hotelbooking.users.UserData

internal object AuthenticationDB{

    private val userAuthentication = HashMap<UserData,String>().apply {
        val appAdmin = AppAdmin()
        UserDB.addUser(appAdmin)
        put(appAdmin.userData,"pass")
        ContactInfoDB.addEmail(appAdmin.userData.email)
        ContactInfoDB.addPhoneNumber(appAdmin.userData.phoneNumber)
    }




    fun addUserAuthentication(userData : UserData, password : String){
        if(ContactInfoDB.isEmailExist(userData.email) || ContactInfoDB.isPhoneNumberExist(userData.phoneNumber)){
            throw Exception("Phone number or Mail id already exist");
        }
        userAuthentication[userData] = password;

        ContactInfoDB.addEmail(userData.email)
        ContactInfoDB.addPhoneNumber(userData.phoneNumber)
    }

    fun authenticateUser(email_or_phNo : String, password_: String) : UserData?{
        for(user in userAuthentication){
            val userData : UserData = user.key;
            val password: String = user.value;
            if(email_or_phNo == userData.email || email_or_phNo == userData.phoneNumber.toString()){
                if(password_ == password){
                    return userData;
                }
            }
        }
        return null;
    }
}

internal object UserDB{

    private val users = ArrayList<User>()



    fun addUser(user : User){
        users.add(user)

    }

    fun getUser(userData: UserData, userType : String?) : User?{
        for(user in users){
            if(user.userData==userData){
                if(userType == user::class.simpleName){
                    return user;
                }
            }
        }
        return null;
    }

}

internal object ContactInfoDB{

    private val phoneNumbers = ArrayList<Long>();
    private val emails = ArrayList<String>()

    fun addPhoneNumber(phoneNumber : Long){
        if(phoneNumbers.contains(phoneNumber)){
            return
        }
        phoneNumbers.add(phoneNumber)
    }

    fun addEmail(email : String){
        if(emails.contains(email)){
            return
        }
        emails.add(email);
    }

    fun isPhoneNumberExist(phoneNumber: Long) : Boolean{

        return phoneNumbers.contains(phoneNumber);
    }

    fun isEmailExist(email : String) : Boolean{
        return emails.contains(email);
    }
}


internal object HotelDB{

    private val hotels = ArrayList<Hotel>()


    fun getHotels() : List<Hotel>{
        return hotels;
    }

    fun addHotel(hotel : Hotel){
        hotels.add(hotel);
    }



}

internal object BookingDB{

    private val bookings : ArrayList<BookingInterface> = ArrayList();

    fun addBooking(booking : BookingInterface){
        bookings.add(booking)
    }

    fun removeBooking(booking : BookingInterface){
        bookings.remove(booking)
    }

    fun getBookings() : List<BookingInterface>{
        return bookings;
    }


}

internal object LocationDB{
    private val locations = ArrayList<String>();

    fun addLocation(location : String){
        locations.add(modifyString(location));
    }

    fun removeLocation(location : String){
        locations.remove(location);
    }

    fun isLocationAvailable(location: String) : Boolean{
        return locations.contains(location);
    }
}

internal object AmenityDB{

    private val amenities = ArrayList<Amenity>()

    fun addAmenity(amenity: Amenity) {
        amenities.add(amenity)
    }

    fun removeAmenity(amenity: Amenity) {
        amenities.remove(amenity)
    }

    fun getAmenities() : List<Amenity>{
        return amenities;
    }


}





