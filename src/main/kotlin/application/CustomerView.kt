package application

import application.InputHelper.getDate
import application.InputHelper.getInputWithinRange
import application.InputHelper.getSimpleDateWithoutYear
import application.InputHelper.getStringInput
import hotelbooking.booking.Book
import hotelbooking.booking.CancelBooking
import hotelbooking.booking.CustomerBookingInterface
import hotelbooking.hotel.HotelCustomerInterface
import hotelbooking.hotel.HotelInterface
import hotelbooking.hotel.room.RoomCustomerView
import hotelbooking.users.Customer
import java.lang.Exception
import java.util.*

object CustomerView {

    fun menu(customer : Customer){
        do{
            println("Welcome "+customer.userData.userName)
            println("1.Book Hotel\n2.List Bookings\n3.Cancel Booking\n4.List Cancelled Bookings\n5.Favorite List\n6.Sign Out\n")

            when(getInputWithinRange(1,7,null)){
                1 -> bookHotel(customer)
                2 -> listBookings(customer)
                3 -> cancelBooking(customer)
                4 -> listCancelledBookings(customer)
                5 -> listFavoriteHotels(customer)
                6 -> break
            }
        }while (true)
    }


    private fun bookHotel(customer: Customer){
        try{
            println("Enter Locality : ");
            val locality: String = InputHelper.modifyString(getStringInput())
            println("Check In Date : ")
            val checkInDate: Date = getDate();
            println("Check Out Date : ")
            val checkOutDate: Date = getDate();
            println("Enter No of Rooms Needed : ")
            val noOfRoomsNeeded = getInputWithinRange(
                1,
                10,
                "Only you can book up to 10 rooms at a time and no of rooms should be greater than 1"
            )
            val noOfGuestsInEachRoom = ArrayList<Int>()
            for (i in 0 until noOfRoomsNeeded) {
                println("No of Guest in Room " + (i + 1))
                val noOfGuest = getInputWithinRange(1, 12, "Only up to 12 members can stay on single room")
                noOfGuestsInEachRoom.add(noOfGuest)
            }

            val book = Book(checkInDate,checkOutDate,locality, noOfGuestsInEachRoom)
            val availableHotelsWithRooms = book.filterHotels();
            val availableHotels = availableHotelsWithRooms.keys.toList()
            if (availableHotels.isEmpty()) {
                println("Sold Out")
                return
            }
            do {
                var i =0
                for (hotel in availableHotels) {
                    printHotelDetailsWithBooking(
                        i,
                        hotel,
                        availableHotelsWithRooms[hotel]!!,
                        noOfGuestsInEachRoom
                    )
                    i++
                }
                println("1.Select Hotel");
                println("2.Go Back")
                val selectChoice = getInputWithinRange(1,2, null)
                if (selectChoice == 1) {
                    println("Enter S.No to Select Hotel : ")
                    val choice = getInputWithinRange(1,availableHotels.size, null)
                    println("\n\n")
                    val hotel: HotelCustomerInterface = availableHotels[choice-1]
                    val rooms: List<RoomCustomerView> = availableHotelsWithRooms[hotel]!!
                    val booked: Boolean = expandedHotelDetails(
                        hotel,
                        customer,
                        rooms,
                        book
                    )
                    if (booked) {
                        return
                    }
                } else {
                    break
                }
            } while (true)

        }catch (e  : Exception){
            println(e.message)
        }

    }

    private fun printHotelDetailsWithBooking(
        sno: Int,
        hotel: HotelCustomerInterface,
        rooms: List<RoomCustomerView>,
        noOfGuestsInEachRoom: List<Int>
    ) {
        printHotelDetails(sno, hotel)
        var listPrice: Double = getListPrice(rooms)
        var maxPrice: Double = getMaxPrice(rooms)
        val bedPrice: Double = getBedPrice(rooms, noOfGuestsInEachRoom)
        listPrice += bedPrice
        maxPrice += bedPrice
        val discount: Int = getDiscountPercent(listPrice, maxPrice).toInt()
        println("Price(per night) : ₹$listPrice Actual Price(per night) : ₹$maxPrice  $discount%off\n\n")
    }

    private fun printHotelDetails(sno: Int, hotel: HotelCustomerInterface) {
        with(hotel){
            println((sno + 1).toString() + " . " + "BOOKIZ " + getHotelType() + " Hotel ID : " + getID())
            println("\t" + getName())
            println("\tPh.No : " + getPhoneNumber())
            println("\tAddress : No." + getAddress().buildingNo + "," + getAddress().street)
            println("\t" + getAddress().locality + "," + getAddress().city)
            println("\t" + getAddress().state + "-" + getAddress().postalCode)
        }
        
    }

    private fun expandedHotelDetails(
        hotel: HotelCustomerInterface,
        customer: Customer,
        rooms: List<RoomCustomerView>,
        book: Book
    ): Boolean {
        println(hotel.getHotelType().toString() + " " + hotel.getID() + " " + hotel.getName())
        println("\tNo." + hotel.getAddress().buildingNo + "," + hotel.getAddress().street)
        println("\t" + hotel.getAddress().locality + "," + hotel.getAddress().city)
        println("\t" + hotel.getAddress().state + hotel.getAddress().postalCode)
        println()
        println("Your Booking Details")
        println("Dates : " + getSimpleDateWithoutYear(book.checkInDate) + " - " +getSimpleDateWithoutYear(book.checkOutDate))
        println("Rooms : ${book.getNoOfRoomsNeeded()}")
        println("Booking for : " + customer.userData.userName)
        println()
        println("Amenities : ")
        val amenitiesList = hotel.getAmenities()
        var rowChange = 0
        for (i in amenitiesList.indices) {
            print((i + 1).toString() + "." + amenitiesList[i].name + "    ")
            rowChange++
            if (rowChange == 4) {
                rowChange = 0
                println()
            }
        }
        println("\nPricing breakup : ")

        val noOfGuestsInEachRoom = book.noOfGuestsInEachRoom
        var totalPrice = 0.0
        var i =0
        for (room in rooms) {
            val roomPrice_oneDay: Double =
                room.getRoomPrice().listPrice + noOfGuestsInEachRoom[i] * room.getBedPrice().basePrice
            val roomPrice_total = roomPrice_oneDay * book.getNoOfDays()
            println("Room " + (i + 1) + " : ₹" + room.getRoomPrice().maxPrice + "(org price) : ₹" + room.getRoomPrice().listPrice + "(discounted price) + (" + noOfGuestsInEachRoom[i] + "*" + room.getBedPrice() + ") = ₹" + roomPrice_oneDay + " * " + book.getNoOfDays() + "(days) : ₹" + roomPrice_total)
            totalPrice += roomPrice_total
            i++
        }
        println("\t\tTotal : ₹$totalPrice")
        println("\n\n1.Book  2.Add To Favorite List 3.Back")
        val choice = getInputWithinRange(1,3, null)
        loop@ do {
            when (choice) {
                1 -> {
                    val booked: Int = paySlip(totalPrice)
                    if (booked == 1) {
                        book.bookHotel(customer,hotel,true)
                        return true
                    }
                    else if(booked ==2){
                        book.bookHotel(customer,hotel,false)
                        return true
                    }
                    break@loop
                }

                2 -> {
                    addToFavoriteList(customer, hotel)
                    println("Added to Favorite List")
                    break@loop
                }

                3 -> return false
            }
        } while (true)
        return false
    }

    private fun addToFavoriteList(customer: Customer, hotel: HotelCustomerInterface) {
        customer.addFavoriteHotels(hotel)
    }

    private fun paySlip(totalPrice: Double): Int {
        println("1. Pay Now ₹$totalPrice")
        println("2. Pay Later")
        println("3. Go Back")
        when (getInputWithinRange(1,3, null)) {
            1 -> {
                println("Paid Using UPI")
                return 1
            }

            2 -> {
                println("Pay at Hotel ₹$totalPrice")
                return 2
            }

            3 -> return 3
        }
        return 3
    }

    private fun getListPrice( rooms: List<RoomCustomerView>): Double {
        var listPrice  =0.0
        for (room in rooms) {
            listPrice += room.getRoomPrice().listPrice
        }

        return listPrice
    }

    private fun getMaxPrice(rooms: List<RoomCustomerView>): Double {
        var maxPrice = 0.0
        for (room in rooms) {

            maxPrice += room.getRoomPrice().maxPrice
        }
        return maxPrice
    }

    private fun getBedPrice(rooms: List<RoomCustomerView>, noOfGuestsInEachRoom: List<Int>): Double {
        var bedPrice = 0.0
        var i =0
        for (room in rooms) {
            bedPrice += room.getBedPrice().listPrice * noOfGuestsInEachRoom[i]
            i++
        }
        return bedPrice
    }

    private fun getDiscountPercent(listPrice: Double, maxPrice: Double): Double {
        val discount = maxPrice - listPrice
        return discount / maxPrice * 100
    }



    private fun listBookings(customer: Customer): Boolean {
        val bookings: List<CustomerBookingInterface> = customer.getBookings()
        if (bookings.isEmpty()) {
            println("No Bookings Avail")
            return false
        }
        bookingsWithDetail(bookings)
        return true
    }

    private fun bookingsWithDetail(bookings: List<CustomerBookingInterface>) {
        println("\nBooking List\n")
        for (i in bookings.indices) {
            val booking: CustomerBookingInterface = bookings[i]
            val hotel: HotelInterface = booking.getHotel()
            println((i + 1).toString() + ".Booking ID : " + booking.getBookingID())
            println("  Check-in Date : " + getSimpleDateWithoutYear(booking.getCheckInDate()) + "   Check-out Date : " + getSimpleDateWithoutYear(booking.getCheckOutDate()))
            println("  " + hotel.getHotelType() + " " + hotel.getName())
            println("  No." + hotel.getAddress().buildingNo + "," + hotel.getAddress().street)
            println("  " + hotel.getAddress().locality + "," + hotel.getAddress().city)
            println("  " + hotel.getAddress().state + "-" + hotel.getAddress().postalCode)
            println()

        }
    }


    private fun cancelBooking(customer: Customer) {
        if (!listBookings(customer)) {
            return
        }
        println("1. Cancel Hotel")
        println("2. Go Back")
        println("Enter Input : ")
        val choice = getInputWithinRange(1,2, null)
        if (choice == 1) {
            println("Enter S.NO to cancel booking")
            val bookings: List<CustomerBookingInterface> = customer.getBookings()
            val bookingIndex = getInputWithinRange(1,bookings.size, null)
            val booking: CustomerBookingInterface = bookings[bookingIndex - 1]
            CancelBooking(booking)
            println("Booking Cancelled Successfully")
        }
    }


    private fun listCancelledBookings(customer: Customer) {
        val bookings: List<CustomerBookingInterface> = customer.getCancelledBookings()
        if (bookings.isEmpty()) {
            println("No Booking Available ")
            return
        }
        bookingsWithDetail(bookings)
    }


    private fun listFavoriteHotels(customer: Customer) {
        val favoriteHotels: List<HotelCustomerInterface> = customer.getFavoriteHotels()
        if (favoriteHotels.isEmpty()) {
            println("No Favorite Hotels")
            return
        }
        println("Favorite Hotels")
        var sno = 0
        for (hotel in favoriteHotels) {
            printHotelDetails(sno, hotel)
            sno++;
        }
    }


}