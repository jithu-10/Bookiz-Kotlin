package application

import application.InputHelper.getInputWithinRange
import application.InputHelper.getIntegerInput
import application.InputHelper.getSimpleDateWithoutYear
import application.InputHelper.getStringInput
import hotelbooking.booking.BookingInterface
import hotelbooking.hotel.Amenity
import hotelbooking.hotel.HotelAppAdminInterface
import hotelbooking.hotel.HotelInterface
import hotelbooking.hotel.HotelType
import hotelbooking.hotel.room.Room
import hotelbooking.users.AppAdmin
import hotelbooking.users.UserData


object AdminView {

    fun menu(appAdmin : AppAdmin){
        do{
            println("Welcome Admin")
            println("1. List of Hotels which have requested for approval")
            println("2. List of Hotel Registered")
            println("3. Remove Hotels ")
            println("4. Set Price for Hotel Rooms")
            println("5. List all bookings")
            println("6. Add Amenity")
            println("7. Remove Amenity")
            println("8. Sign Out ")

            when(getInputWithinRange(1,10,null)){
                1-> approveHotels(appAdmin)
                2-> listRegisteredHotels(appAdmin)
                3-> removeRegisteredHotels(appAdmin)
                4-> setPriceForRooms(appAdmin)
                5-> listAllBookings(appAdmin)
                6-> addAmenity(appAdmin)
                7-> removeAmenity(appAdmin)
                8-> break
            }
        }while(true)
    }


    private fun approveHotels(appAdmin : AppAdmin){
        listHotelsRequestedForApproval(appAdmin)
    }

    private fun listHotelsRequestedForApproval(appAdmin : AppAdmin){
        val hotelsRequested = appAdmin.getApprovalRequestedHotels().toMutableList();
        if(hotelsRequested.isEmpty()){
            println("No such request Available now")
        }
        var i =0
        for (hotel in hotelsRequested) {
            with(hotel){
                println((i + 1).toString() + " . " + getName())
                println("\t" + "No . " + getAddress().buildingNo + "," + getAddress().street)
                println("\t" + getAddress().locality + "," + getAddress().city)
                println("\t" + getAddress().state + "," + getAddress().postalCode)
                println("Ph No" + getPhoneNumber())
                println("Total No of Rooms" + getTotalNumberOfRooms())
                println("Total Amenity Percent" + getTotalAmenityPercent()+ "%")
                println("Requested Hotel Type : " + getHotelType())
                println("\n\n")
            }
            
            
            i++
        }

        val options: Int = selectOrExitOptions()
        if (options == 2) {
            return
        }

        println("Enter S.no to Select Hotel")
        val choice = getInputWithinRange(1,hotelsRequested.size, null)
        val approval: Int = approvalOptions()

        val hotel = hotelsRequested[choice-1]
        if (approval == 1) {
            if (changeHotelTypeOption() == 1) {
                changeHotelTypeSpecification(hotel)
            }
            appAdmin.approveHotel(hotel)
            setPriceForHotelRooms(hotel)
            hotelsRequested.removeAt(choice-1)
        } else {
            appAdmin.rejectHotel(hotel)
            hotelsRequested.removeAt(choice - 1)
        }
    }

    private fun selectOrExitOptions(): Int {
        println("1.Select From List \n 2.Back")
        println("Enter Input")
        return getInputWithinRange(1,2, null)
    }

    private fun approvalOptions(): Int {
        println("1.Accept \n2.Reject")
        println("Enter Input")
        return getInputWithinRange(1,2, null)
    }

    private fun changeHotelTypeOption(): Int {
        println("1. Change Hotel Type")
        println("2. Skip")
        return getInputWithinRange(1,2, null)
    }

    private fun changeHotelTypeSpecification(hotel: HotelAppAdminInterface) {
        println("Select Hotel Type : ")
        println("1." + HotelType.ELITE + " HOTEL ")
        println("2." + HotelType.PREMIUM + " HOTEL ")
        println("3." + HotelType.STANDARD + " HOTEL ")
        println("4." + "Skip")
        when (getInputWithinRange(1,4, null)) {
            1 -> hotel.setHotelType(HotelType.ELITE)
            2 -> hotel.setHotelType(HotelType.PREMIUM)
            3 -> hotel.setHotelType(HotelType.STANDARD)
            4 -> hotel.setHotelType()
        }
    }

    private fun displayRooms(rooms: List<Room>) {
        var i = 0
        for (room in rooms) {
            with(room){
                println( (i + 1).toString() + ". Max Guest : " + roomCapacity + " Base Price : " + roomPrice.basePrice + " Max Price : " + roomPrice.maxPrice + " List Price : " + roomPrice.getListPrice()) 
            }
            i++
            
        }
    }

    private fun setPriceForHotelRooms(hotel: HotelAppAdminInterface) {
        do {
            println("Set Price for Hotel Rooms")
            val rooms = hotel.getRooms()
            displayRooms(rooms)
            println("1.Change Room List Price")
            println("2.Back")
            val options = getInputWithinRange(1,2, null)
            if (options == 2) {
                return
            }
            println("Enter Room No. to change list price : ")
            val choice = getInputWithinRange(1,rooms.size, null)
            val room = rooms[choice - 1]
            println("Base Price : " + room.roomPrice.basePrice + " Max Price : " + room.roomPrice.maxPrice + " List Price : " + room.roomPrice.getListPrice())
            println("Enter New List Price : ")
            do {
                try {
                    val listPrice: Double = InputHelper.getDoubleInput()
                    room.roomPrice.setListPrice(listPrice)
                    break
                } catch (e: Exception) {
                    println(e.message)
                }
            } while (true)
        } while (true)
    }


    private fun listRegisteredHotels(appAdmin: AppAdmin) {
        val hotels: List<HotelAppAdminInterface> = appAdmin.getRegisteredHotels()
        if (hotels.isEmpty()) {
            println("No Hotel Avail")
            return
        }
        println("ID\t Hotel Name\t\t\t\tLocality \t\t\t\tRooms\tTypeofRoom\n")
        for (hotel in hotels) {
            with(hotel){
                System.out.printf(
                    "%-4s %-20s %-25s %-7s %-7s",
                    getID(),
                    getName(),
                    getAddress().locality + "," + getAddress().city,
                    getTotalNumberOfRooms(),
                )
                println()
            }
            
            
        }
    }



    private fun removeRegisteredHotels(appAdmin: AppAdmin) {
        listRegisteredHotels(appAdmin)
        println("Enter Hotel ID : ")
        val hotelId = getIntegerInput()
        for(hotel in appAdmin.getRegisteredHotels()){
            if(hotel.getID()==hotelId){
                appAdmin.removeHotel(hotel)
                println("Hotel Removed Successfully")
                return
            }
        }
        println("Hotel With given ID Not Found");
    }



    private fun setPriceForRooms(appAdmin: AppAdmin) {
        println("1.Enter Hotel ID and change price for rooms\n2.Exit")
        println("Enter Input : ")
        val choice = getInputWithinRange(1,2, null)
        if (choice == 1) {
            changeHotelPriceByID(appAdmin)
        }

    }

    private fun changeHotelPriceByID(appAdmin: AppAdmin) {
        println("\nEnter Hotel ID : ")
        val hotelID = getInputWithinRange(0,Int.MAX_VALUE, "Please enter valid Hotel ID")
        for(hotel in appAdmin.getRegisteredHotels()){
            if(hotel.getID()==hotelID){
                setPrice(hotel)
                return
            }
        }
        println("No Hotel With the ID Found")
    }



    private fun setPrice(hotel: HotelAppAdminInterface) {
        with(hotel){
            println("Hotel ID : " + getID())
            println("Hotel Name : " + getName())
            println("Hotel Address : No." + getAddress().buildingNo + "," + getAddress().street + "," + getAddress().locality)
            println("\t" + getAddress().city + "," + getAddress().state)
            println("Postal Code : " + getAddress().postalCode)
            println("No of Rooms : " + getTotalNumberOfRooms())
        }
        setPriceForHotelRooms(hotel)
        
        
    }



    private fun addAmenity(appAdmin: AppAdmin) {
        println("Enter Amenity Name : ")
        val amenityName = getStringInput().uppercase()
        println("Enter Amenity Points : ")
        val amenityPoints = getInputWithinRange(1,100,"Maximum 100 points can be given")
        val amenity = Amenity(amenityName, amenityPoints)
        appAdmin.addAmenity(amenity)
    }



    private fun removeAmenity(appAdmin: AppAdmin) {
        println("Amenity List : \n")
        var sno = 1
        val amenities: List<Amenity> = appAdmin.getAmenities()
        if(amenities.isEmpty()){
            println("No Amenity Found")
            return
        }
        for (amenity in amenities) {
            println("$sno. ${amenity.name}")
            sno++
        }
        println("Enter S.No to Remove Amenity")
        val choice = getInputWithinRange(1,amenities.size,null)
        val amenity = amenities[choice - 1]
        appAdmin.removeAmenity(amenity)
    }


    private fun listAllBookings(appAdmin: AppAdmin) {
        val bookings: List<BookingInterface> = appAdmin.getAllBookings()
        if (bookings.isEmpty()) {
            println("No Bookings Avail")
            return
        }
        println("Booking List ")
        var i = 0
        for (booking in bookings) {
            val customer: UserData = booking.getCustomerData()
            val hotel: HotelInterface = booking.getHotel()
            println((i + 1).toString() + ". " + "Booking ID" + booking.getBookingID())
            println("Check In Date : " + getSimpleDateWithoutYear(booking.getCheckInDate()))
            println("Check Out Date : " + getSimpleDateWithoutYear(booking.getCheckOutDate()))
            println("Customer Name : " + customer.userName)
            println("Contact : " + customer.phoneNumber)
            println(("Hotel Name : " + hotel.getName()) + " Hotel ID : "+ hotel.getID())
            println()
            i++
        }
    }

}

