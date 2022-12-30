package hotelbooking.booking

import hotelbooking.db.BookingDB
import hotelbooking.db.HotelDB
import hotelbooking.db.LocationDB
import hotelbooking.helper.getDatesBetweenTwoDates
import hotelbooking.hotel.*
import hotelbooking.hotel.Hotel
import hotelbooking.hotel.room.Room
import hotelbooking.hotel.room.RoomCustomerPanel
import hotelbooking.hotel.room.RoomHotelPanel
import hotelbooking.helper.modifyString
import hotelbooking.users.Customer
import java.util.*

class Book(
    val checkInDate: Date,
    val checkOutDate: Date,
    val locality : String,
    val noOfGuestsInEachRoom: List<Int>){



    private lateinit var availHotelAndRoomsMap : LinkedHashMap<HotelCustomerPanel,List<RoomCustomerPanel>>


    init{
        validateCheckInDate()
        validateCheckOutDate()
        if(!LocationDB.isLocationAvailable(locality)) throw Exception("No hotels available in your locality")
    }



    fun bookHotel(customer : Customer, hotel : HotelCustomerPanel, paymentStatus: Boolean){
        if(!availHotelAndRoomsMap.contains(hotel)){
            throw Exception("Unavailable Hotel")
        }
        val booking : BookingDetails = BookingDetails(
            checkInDate,
            checkOutDate,
            customer.userData,
            hotel,
            noOfGuestsInEachRoom,
            availHotelAndRoomsMap[hotel]?:throw Exception("Room Unavailable"),
            paymentStatus,
            BookingStatus.BOOKED
        )
        setTotalPrice(availHotelAndRoomsMap[hotel]!!,booking)
        availHotelAndRoomsMap.clear();
        BookingDB.addBooking(booking.getCustomerBookingPanel())
        customer.addBooking(booking.getCustomerBookingPanel())
        hotel.hotel.addBooking(booking.getHotelBookingPanel())


    }


    private fun setTotalPrice(rooms: List<RoomCustomerPanel>, booking : BookingDetails){
        var totalPrice = 0.0
        var i =0
        for (room in rooms) {
            val roomPrice_oneDay: Double =
                room.getRoomPrice().listPrice + noOfGuestsInEachRoom[i] * room.getBedPrice().basePrice
            val roomPrice_total = roomPrice_oneDay * getNoOfDays()
            totalPrice += roomPrice_total
            i++
        }
        booking.setTotalPrice(totalPrice)
    }









    fun filterHotels() :Map<HotelCustomerPanel, List<RoomCustomerPanel>>{
        val totalNoOfRoomsNeeded = noOfGuestsInEachRoom.size;
        val datesInRange: ArrayList<Date> = getDatesBetweenTwoDates(checkInDate, checkOutDate)
        datesInRange.add(checkOutDate)
        val hotels : List<Hotel> = HotelDB.getHotels()
            .filter { hotel: Hotel -> hotel.getApprovalStatus() == HotelApprovalStatus.APPROVED }
        val hotelAndRoomsMap = LinkedHashMap<HotelCustomerPanel, List<RoomCustomerPanel>>()
        for(hotel in hotels){

            if (modifyString(hotel.getAddress().locality) != modifyString(locality) && modifyString(hotel.getAddress().city) != modifyString(locality)
            ) {
                continue
            }

            if (hotel.getTotalNumberOfRooms() < totalNoOfRoomsNeeded) {

                continue
            }

            val filteredRooms: List<RoomCustomerPanel> = filterRooms(hotel, datesInRange, totalNoOfRoomsNeeded, noOfGuestsInEachRoom)?:continue

            hotelAndRoomsMap[hotel.getHotelCustomerInterface()] = filteredRooms

        }
        availHotelAndRoomsMap = hotelAndRoomsMap
        return  hotelAndRoomsMap
    }






    private fun filterRooms(hotel: Hotel, datesInRange: ArrayList<Date>, noOfRoomsNeeded: Int, noOfGuestsInEachRoom: List<Int>): List<RoomCustomerPanel>? {
        Collections.sort(noOfGuestsInEachRoom, Collections.reverseOrder())
        val rooms = hotel.getRooms().map { it.room }
        val selectedRooms = ArrayList<Room>()

        for (i in noOfGuestsInEachRoom.indices) {
            val noOfGuests = noOfGuestsInEachRoom[i]

            val availRooms = ArrayList<Room>()
            for (j in rooms.indices) {
                if (selectedRooms.contains(rooms[j])) {
                    continue
                }
                if (noOfGuests > rooms[j].getRoomCapacity()) {

                    continue
                }
                if (!dateAvailabilityCheck(datesInRange, hotel, rooms[j])) {

                    continue
                }
                availRooms.add(rooms[j])

            }
            if (availRooms.isEmpty()) {
                return null
            }
            var selectedRoom = availRooms[0]
            for (k in 1 until availRooms.size) {
                val previous = selectedRoom.getRoomCapacity() - noOfGuests
                val current = availRooms[k].getRoomCapacity() - noOfGuests
                if (current < previous) {
                    selectedRoom = availRooms[k]
                }
            }
            selectedRooms.add(selectedRoom)
        }

        return if (selectedRooms.size == noOfRoomsNeeded) {
            selectedRooms.map { it.getRoomCustomerPanel() }
        } else null
    }












    private fun dateAvailabilityCheck(datesInRange: ArrayList<Date>, hotel: Hotel, room: Room): Boolean {
        for (i in datesInRange.indices) {
            if (hotel.checkRoomBookedByDate(room, datesInRange[i])) {
                return false
            }
        }
        return true
    }



    private fun validateCheckInDate() {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        val currentDate = cal.time
        val value = checkInDate.compareTo(currentDate)
        if (value == -1) {
            throw Exception("You can't book hotel for previous dates")
        } else {
            val noOfInBetweenDays: Int = getDatesBetweenTwoDates(currentDate, checkInDate).size
            if (noOfInBetweenDays > 60) {
                throw Exception("You can book hotel room only up to 60 days")
            }
        }
    }

    private fun validateCheckOutDate() {
        val value = checkOutDate.compareTo(checkInDate)
        if (value == -1 || value == 0) {
            throw Exception("Check out Date will be greater than Check In Date")
        } else {
            val noOfInBetweenDays: Int = getDatesBetweenTwoDates(checkInDate, checkOutDate).size
            if (noOfInBetweenDays > 150) {
                throw Exception("You can book only for next 150 days")
            }
        }
    }

    fun getNoOfDays() :  Int{
        return getDatesBetweenTwoDates(checkInDate,checkOutDate).size
    }



    fun getNoOfRoomsNeeded() : Int{
        return noOfGuestsInEachRoom.size
    }


}

