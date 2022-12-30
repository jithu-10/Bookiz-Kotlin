package application

import hotelbooking.Authentication
import hotelbooking.booking.Book
import hotelbooking.hotel.Address
import hotelbooking.hotel.Amenity
import hotelbooking.hotel.HotelType
import hotelbooking.hotel.room.Price
import hotelbooking.users.UserData

import java.lang.Exception
import java.util.*


object Application {

    fun startApp(){
       init()
        welcomeMessage()
    }

    private fun init(){

        val admin = Authentication.signInAsAdmin(Authentication.signIn("1234567890","pass"))
        val amenity1 = Amenity("AC",20)
        val amenity2 = Amenity("Fridge",40)
        admin.addAmenity(amenity1)
        admin.addAmenity(amenity2)

        Authentication.signUp("hotel","j@gm.c",9876543211,"pass")
        val hotelAdmin = Authentication.signInAsHotelAdmin(Authentication.signIn("9876543211","pass"))
        hotelAdmin.registerHotel(hotelAdmin.userData,"Grand Chola", Address("2A","Old Trafford Street","Tambaram","Chennai","TamilNadu",123456)).apply {

            addRoom(3, Price(1000.0,2000.0), Price(100.0,200.0))
            addRoom(3, Price(1000.0,2000.0), Price(100.0,200.0))
            addRoom(3, Price(1000.0,2000.0), Price(100.0,200.0))
            setHotelType(HotelType.ELITE)
            addAmenity(amenity2)
        }

        admin.approveHotel(admin.getApprovalRequestedHotels()[0])

        Authentication.signUp("customer","j@gm3.c",1234512345,"pass")
        val customer = Authentication.signInAsCustomer(Authentication.signIn("1234512345","pass"))

        val cal = Calendar.getInstance()
        cal[2023, Calendar.JANUARY, 1, 0, 0] = 0

        val checkInDate = cal.time

        cal[2023, Calendar.JANUARY, 3, 0, 0] = 0
        val checkOutDate = cal.time

        val book = Book(checkInDate,checkOutDate,"Chennai", listOf(1,2,3))
        book.bookHotel(customer,book.filterHotels().keys.toList()[0],true)


    }

    private fun welcomeMessage(){
        do{
            println("Welcome to Bookiz ");
            println("1.Sign In ")
            println("2.Sign Up ")
            println("3.Exit")

            when(InputHelper.getInputWithinRange(1,3,null)){
                1 -> signIn()
                2 -> signUp()
                3 -> break
            }
        }while (true)


    }

    private fun signIn(){
        println("Enter Phone Number or Email : ")
        val phone_or_mail : String = InputHelper.getPhoneNumberOrEmail()
        println("Enter Password : ")
        val password : String = InputHelper.getStringInput()
        try{
            val userData : UserData = Authentication.signIn(phone_or_mail,password)
            signingInOptions(userData)
        }catch (e : Exception){
            println(e.message)
        }

    }

    private fun signingInOptions(userData: UserData){
        println("1.Sign In as Customer")
        println("2.Sign In as Hotel Admin")
        println("3.Sign In as App Admin")
        when(InputHelper.getInputWithinRange(1,3,null)){
            1 -> CustomerView.menu(Authentication.signInAsCustomer(userData))
            2 -> HotelAdminView.menu(Authentication.signInAsHotelAdmin(userData))
            3 ->{
                try{
                    AdminView.menu(Authentication.signInAsAdmin(userData))
                }catch (e : Exception){
                    println(e.message)
                }

            }
        }
    }

    private fun signUp(){
        println("Enter User name : ")
        val userName : String = InputHelper.getStringInput();

        do{
            try{
                println("Enter Email : ")
                val email : String = InputHelper.getEmailInput();
                println("Enter Phone Number : ")
                val phoneNumber : Long = InputHelper.getPhoneNumber()
                println("Enter Password : ")
                val password : String = InputHelper.getStringInput()
                Authentication.signUp(userName,email,phoneNumber,password)
                println("Signed Up Successfully. You can sign in now ");
                break
            }
            catch (e : Exception){
                println(e.message);
            }
        }while(true)

    }
}