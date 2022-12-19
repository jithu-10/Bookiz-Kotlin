package hotelbooking

import hotelbooking.db.AuthenticationDB
import hotelbooking.db.UserDB
import hotelbooking.users.*

object Authentication{
    fun signUp(userName : String,email : String,phoneNumber : Long,password: String){
        val userData = UserData(userName,phoneNumber,email);
        AuthenticationDB.addUserAuthentication(userData,password);
    }



    private fun createCustomerAccount(userData : UserData) : User {
        val customer : Customer = Customer(userData);
        UserDB.addUser(customer);
        return customer
    }

    private fun createHotelAdminAccount(userData : UserData) : User {
        val hotelAdmin : HotelAdmin = HotelAdmin(userData);
        UserDB.addUser(hotelAdmin)
        return hotelAdmin
    }


    fun signIn(email_or_phone : String, password: String) : UserData {
        return AuthenticationDB.authenticateUser(email_or_phone,password)?: throw Exception("Account Not Found");
    }

    fun signInAsAdmin(userData: UserData): AppAdmin {
        val user = UserDB.getUser(userData, AppAdmin::class.simpleName)?: throw Exception("Authentication Failed")
        return user as AppAdmin
    }

    fun signInAsHotelAdmin(userData: UserData): HotelAdmin {
        val user = UserDB.getUser(userData, HotelAdmin::class.simpleName)?: createHotelAdminAccount(userData);
        return user as HotelAdmin
    }

    fun signInAsCustomer(userData: UserData): Customer {
        val user = UserDB.getUser(userData, Customer::class.simpleName)?: createCustomerAccount(userData);
        return user as Customer
    }
}
