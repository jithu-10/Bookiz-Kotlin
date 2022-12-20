package application

import application.InputHelper.getDoubleInput
import application.InputHelper.getInputWithinRange

import application.InputHelper.getSimpleDateWithoutYear
import application.InputHelper.getStringInput
import hotelbooking.booking.HotelBookingInterface
import hotelbooking.hotel.*
import hotelbooking.hotel.room.Price
import hotelbooking.hotel.room.RoomHotelView
import hotelbooking.users.HotelAdmin
import hotelbooking.users.UserData
import java.util.*

object HotelAdminView {

    fun menu(hotelAdmin : HotelAdmin){
        do{
            println("Welcome "+hotelAdmin.userData.userName)
            println("1. List Hotels")
            println("2. Register Hotel")
            println("3. Exit")

            when(getInputWithinRange(1,3,null)){
                1 -> listHotelsOwned(hotelAdmin)
                2 -> registerHotel(hotelAdmin)
                3 -> break
            }

        }while(true)

    }


    private fun listHotelsOwned(hotelAdmin: HotelAdmin){
        val hotelsOwned = hotelAdmin.getHotelsOwned()
        if(hotelsOwned.isEmpty()){
            println("No Hotels Registered")
            return
        }
        var sno=1
        for(hotel in hotelsOwned){
            with(hotel){
                println("$sno. "+getName()+" "+getHotelType()+" "+getApprovalStatus()+" "+getTotalNumberOfRooms()); 
            }
            
            sno++;
        }

        val choice = getInputWithinRange(1,hotelsOwned.size,null)
        val hotel : HotelAdminInterface = hotelsOwned[choice-1]
        hotelMenu(hotel,hotelAdmin)

    }

    private fun registerHotel(hotelAdmin: HotelAdmin) {
        val hotelName = getHotelName();
        val hotelAddress = getHotelAddress()
        val hotel = hotelAdmin.registerHotel(hotelAdmin.userData,hotelName,hotelAddress);
        addRooms(hotel)
        addHotelAmenities(hotel)
        hotelTypeSpecification(hotel)
        println("Hotel Successfully Registered")
    }

    private fun getHotelName(): String {
        println("Enter Hotel Name : ")
        return getStringInput()
    }

    private fun getHotelAddress(): Address {
        println("Enter Building No . ")
        val buildingNo = getStringInput()
        println("Enter Street Name : ")
        val street = getStringInput()
        println("Enter Locality : ")
        val locality = getStringInput()
        println("Enter City : ")
        val city = getStringInput()
        println("Enter State : ")
        val state = getStringInput()
        println("Enter PostalCode")
        val postalCode: Int = InputHelper.getPostalCode()
        return Address(buildingNo, street, locality, city, state, postalCode)
    }

    private fun hotelMenu(hotel : HotelAdminInterface, hotelAdmin: HotelAdmin){
       
        when(hotel.getApprovalStatus()){
            HotelApprovalStatus.APPROVED -> approvedHotelMenu(hotel)
            HotelApprovalStatus.ON_PROCESS -> unApprovedHotelMenu(hotel)
            HotelApprovalStatus.REJECTED -> rejectedHotelMenu(hotel, hotelAdmin)
            HotelApprovalStatus.REMOVED,
            HotelApprovalStatus.REMOVED_RE_PROCESS-> removedHotelMenu(hotel,hotelAdmin)
        }

    }


    private fun approvedHotelMenu(hotel : HotelAdminInterface){
        println("Hotel : "+hotel.getName())

        do {
            println("1.Add Rooms\n2.Remove Rooms\n3.Add Amenities\n4.Remove Amenities\n5.Show Rooms which are booked and non booked by Date\n6.Change Room Prices\n7.List of bookings in their hotel\n8.Verify Customer\n9.Go Back")
            println("Enter Input")

            when (getInputWithinRange(1,9, null)) {
                1 -> addRooms(hotel)
                2 -> removeRooms(hotel)
                3 -> addHotelAmenities(hotel)
                4 -> removeHotelAmenities(hotel)
                5 -> showRoomsBookedNonBooked(hotel)
                6 -> changeRoomPrices(hotel)
                7 -> bookedCustomersList(hotel)
                8 -> verifyCustomer(hotel)
                9 -> break
            }
        } while (true)
    }

    private fun unApprovedHotelMenu(hotel : HotelAdminInterface){
        println("Hotel : "+hotel.getName())
        println()
        println("Your Hotel Yet to be approved")
        println()
        do {
            println("1.Add Rooms\n2.Remove Rooms\n3.Add Amenities\n4.Remove Amenities\n5.Change Room Prices\n6.Change Hotel Type\n7.Go Back")
            println("Enter Input : ")
            when (getInputWithinRange(1,7, null)) {
                1 -> addRooms(hotel)
                2 -> removeRooms(hotel)
                3 -> addHotelAmenities(hotel)
                4 -> removeHotelAmenities(hotel)
                5 -> changeRoomPrices(hotel)
                6 -> changeHotelType(hotel)
                7 -> break;
            }
        } while (true)
    }

    private fun rejectedHotelMenu(hotel : HotelAdminInterface, hotelAdmin: HotelAdmin){
        println("Hotel : "+hotel.getName())
        println()
        println("Your Hotel has been rejected. ")
        println()
        do {
            println("1.Add Rooms\n2.Remove Rooms\n3.Add Amenities\n4.Remove Amenities\n5.Change Room Prices\n6.Change Hotel Type\n7.Re-Register\n8.Go Back")
            println("Enter Input : ")
            when (getInputWithinRange(1,8, null)) {
                1 -> addRooms(hotel)
                2 -> removeRooms(hotel)
                3 -> addHotelAmenities(hotel)
                4 -> removeHotelAmenities(hotel)
                5 -> changeRoomPrices(hotel)
                6 -> changeHotelType(hotel)
                7 -> registerAgain(hotel,hotelAdmin)
                8 -> break
            }
        } while (true)
    }

    private fun removedHotelMenu(hotel : HotelAdminInterface, hotelAdmin : HotelAdmin){
        println("Hotel : "+hotel.getName())
        println()
        println("Your Hotel been removed from the app . Your Hotel Will not shown to customers until further update")
        println()

        do {
            println("1.Add Rooms\n2.Remove Rooms\n3.Add Amenities\n4.Remove Amenities\n5.Show Rooms which are booked and non booked by Date\n6.Change Room Prices\n7.List of Customers who booked rooms in their hotel\n8.Verify Customer\n9.Re-Register\n10.Go Back")
            println("Enter Input : ")
            when (getInputWithinRange(1,10, null)) {
                1 -> addRooms(hotel)
                2 -> removeRooms(hotel)
                3 -> addHotelAmenities(hotel)
                4 -> removeHotelAmenities(hotel)
                5 -> showRoomsBookedNonBooked(hotel)
                6 -> changeRoomPrices(hotel)
                7 -> bookedCustomersList(hotel)
                8 -> verifyCustomer(hotel)
                9 -> registerAgain(hotel, hotelAdmin)
                10 -> break

            }
        } while (true)
    }


    private fun addRooms(hotel : HotelAdminInterface){
        println("Enter No of Types of Rooms : ")
        val types: Int = getInputWithinRange(1,5,"only up to 5 types of room add at a time")
        for (i in 0 until types) {
            println("Type : " + (i + 1))
            println("Enter Number of Rooms for Type " + (i + 1))
            val noOfRooms: Int = getInputWithinRange(1,10,"Only up to 10 rooms add at a time")
            println("Enter Max Guest can accommodate in this Room : ")
            val maxGuest = getInputWithinRange(1,12, "Only up to 12 guest can occupy a room")
            val roomPrice: Price = getRoomPrice()
            val bedPrice: Price = getBedPrice()
            for (j in 0 until noOfRooms) {
                hotel.addRoom(maxGuest,roomPrice,bedPrice)
            }
        }
    }

    private fun removeRooms(hotel : HotelAdminInterface){
        println("Remove Rooms ")
        val rooms = hotel.getRooms()
        if (rooms.isEmpty()) {
            println("No rooms available")
            return
        }
        displayRooms(rooms)
        println("Enter S.No to Remove Room : ")
        val choice = getInputWithinRange(1, rooms.size, null)
        hotel.removeRoom(rooms[choice - 1])
    }

    private fun addHotelAmenities(hotel : HotelAdminInterface){
        val totalAmenities: List<Amenity> = hotel.getAllAmenities()
        val hotelAmenities = hotel.getAmenities()
        if (totalAmenities.isEmpty()) {
            println("No Amenity to Display")
            return
        }
        if (totalAmenities.size == hotelAmenities.size) {
            println("All Amenity already added")
            return
        }
        println("Add Hotel Amenities ")
        for (amenity in totalAmenities) {
            if (!hotelAmenities.contains(amenity)) {
                println("Does your hotel have" + amenity.name + " ?")
                println("1.YES\n.2.NO")
                println("Enter Input : ")
                val choice = getInputWithinRange(1,2, null)
                if (choice == 1) {
                    hotel.addAmenity(amenity)
                }
            }
        }
    }

    private fun removeHotelAmenities(hotel : HotelAdminInterface){
        val hotelAmenities = hotel.getAmenities()
        if (hotelAmenities.isEmpty()) {
            println("No Amenity Available")
            return
        }
        println("Remove Hotel Amenities ")

        var sno = 0
        for (amenity in hotelAmenities) {
            println((sno + 1).toString() + " " + amenity)
            sno++
        }
        println("Enter S.No to Remove Amenity")
        val value = getInputWithinRange(1,hotelAmenities.size, null)
        hotel.removeAmenity(hotelAmenities[value - 1])
    }

    private fun showRoomsBookedNonBooked(hotel : HotelAdminInterface){
        println("Enter Date : ")
        val date: Date = InputHelper.getDate()
        val currentDate: Date = InputHelper.getCurrentDate()
        if (currentDate.compareTo(date) == 1) {
            println("Cant Show Booked rooms before current date")
            return
        }

        val noOfRoomsBookedByDate: Int = hotel.getNoOfRoomsBookedByDate(date)

        println("BOOKED  |  UNBOOKED" + "\n")
        println(noOfRoomsBookedByDate.toString() + "        " + (hotel.getTotalNumberOfRooms() - noOfRoomsBookedByDate))
    }

    private fun changeRoomPrices(hotel : HotelAdminInterface){
        val rooms: List<RoomHotelView> = hotel.getRooms()
        displayRooms(rooms)
        println("Change Room Price")
        println("Enter S.No Change Room Price : ")
        val choice = getInputWithinRange(1, rooms.size, null)
        val room = rooms[choice - 1]
        val roomPrice = getRoomPrice()
        val bedPrice = getBedPrice()
        room.setRoomPrice(roomPrice)
        room.setBedPrice(bedPrice)
    }


    private fun bookedCustomersList(hotel: HotelAdminInterface) {
        val bookings: List<HotelBookingInterface> = hotel.getBookings()
        if (bookings.isEmpty()) {
            println("No Customers Booked rooms")
            return
        }
        println()
        var i = 0
        for (booking in bookings) {
            val customer: UserData = booking.getCustomerData()
            customerDetails(i, booking, customer)
            i++
        }

    }

    private fun verifyCustomer(hotel : HotelAdminInterface){
        val bookings: List<HotelBookingInterface> = hotel.getBookings()
        if (bookings.isEmpty()) {
            println("No Customer Booked rooms")
            return
        }
        println("Enter Booking ID : ")
        val bookingID = getInputWithinRange(1,Integer.MAX_VALUE,"Booking Id can't be negative")
        for (booking in bookings) {
            if (booking.getBookingID()==bookingID) {
                val customer: UserData = booking.getCustomerData()
                customerDetails(0, booking, customer)
                return
            }
        }

        println("No Customer booked in your hotel with following ID")
    }

    private fun customerDetails(sno: Int, booking : HotelBookingInterface, customer: UserData) {
        with(booking){
            println((sno+1).toString() + " Customer Name : " + customer.userName)
            println("Booking ID : " + getBookingID())
            println("Check In Date : " + getSimpleDateWithoutYear(getCheckInDate()))
            println("Check Out Date : " + getSimpleDateWithoutYear(getCheckOutDate()))
            println("No of Rooms Booked : " + getNoOfRoomsBooked())
            println("Paid : " + if (getPaymentStatus()) "YES" else "NO")
            println("\n")
        }
        
        
    }

    private fun changeHotelType(hotel : HotelAdminInterface){
        println("Hotel Type : " + hotel.getHotelType())
        println("1." + "Change Hotel Type")
        println("2." + "Back")
        if (getInputWithinRange(1,2, null) == 1) {
            hotelTypeSpecification(hotel)
            println("Hotel Type change requested successfully")
        }
    }

    private fun registerAgain(hotel : HotelAdminInterface, hotelAdmin: HotelAdmin){
        hotelAdmin.reRegisterHotel(hotel)
        println("Hotel Registered Again")
    }


    private fun getRoomPrice(): Price {
        var roomPrice: Price
        do {
            println("Enter Base Room Rent (Without Bed Price) : ")
            val baseRoomPrice = getDoubleInput()
            println("Enter Max Room Rent (Without Bed Price) : ")
            val maxRoomPrice = getDoubleInput()
            try {
                roomPrice = Price(baseRoomPrice, maxRoomPrice)
                break
            } catch (e: Exception) {
                println(e.message)
            }
        } while (true)
        return roomPrice
    }

    private fun getBedPrice(): Price {
        var bedPrice: Price
        do {
            println("Enter Bed Price : ")
            val bedPriceMax: Double = getDoubleInput()
            try {
                bedPrice = Price(bedPriceMax, bedPriceMax + 1)
                break
            } catch (e: Exception) {
                println(e.message)
            }
        } while (true)
        return bedPrice
    }


    private fun displayRooms(rooms: List<RoomHotelView>) {
        var sno = 0
        for (room in rooms) {
            println((sno + 1).toString() + ".Room Max Capacity : " + room.getRoomCapacity())
            println("Room Base Price : " + room.getRoomPrice().basePrice + "  Room Max Price : " + room.getRoomPrice().maxPrice)
            println()
            sno++
        }
    }

    private fun hotelTypeSpecification(hotel: HotelAdminInterface) {
        println("Select Hotel Type : ")
        println("1." + HotelType.ELITE + " HOTEL ")
        println("2." + HotelType.PREMIUM + " HOTEL ")
        println("3." + HotelType.STANDARD + " HOTEL ")
        println("4." + "Skip")
        with(hotel){
            when (getInputWithinRange(1,4, null)) {
                1 -> setHotelType(HotelType.ELITE)
                2 -> setHotelType(HotelType.PREMIUM)
                3 -> setHotelType(HotelType.STANDARD)
                4 -> setHotelType()
            }
        }
        

        
    }



}