package application

import hotelbooking.Authentication
import hotelbooking.users.UserData

import java.lang.Exception


object Application {

    fun startApp(){
        welcomeMessage()
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