package hotelbooking.booking

import hotelbooking.hotel.room.Room
import hotelbooking.hotel.HotelInterface
import hotelbooking.hotel.room.RoomCustomerView
import hotelbooking.users.UserData
import java.util.*

internal class BookingDetails(
    private val checkInDate : Date,
    private val checkOutDate : Date,
    private val customerData : UserData,
    private val hotel : HotelInterface,
    private val noOfGuestsInEachRoom : List<Int>,
    private val bookedRooms : List<RoomCustomerView>,
    private val paid : Boolean,
    private var bookingStatus : BookingStatus
) : CustomerBookingInterface, HotelBookingInterface {

    private val id = generateId();
    private var totalPrice :Double = 0.0

    companion object{
        private var id_helper=9999;

        fun generateId() : Int{
            return ++id_helper;
        }
    }


    override fun getBookingID(): Int {
        return id;
    }

    override fun getCheckInDate(): Date {
        return checkInDate;
    }

    override fun getCheckOutDate(): Date {
        return checkOutDate;
    }

    override fun getCustomerData(): UserData {
        return customerData;
    }

    override fun getHotel(): HotelInterface {
        return hotel;
    }

    override fun getBookingStatus(): BookingStatus {
        return bookingStatus;
    }

    fun setBookingStatus(bookingStatus: BookingStatus) {
        this.bookingStatus=bookingStatus
    }

    override fun getNoOfRoomsBooked(): Int {
        return noOfGuestsInEachRoom.size
    }

    override fun getBookedRooms(): List<RoomCustomerView> {
        return bookedRooms
    }

    override fun getPaymentStatus(): Boolean {
        return paid
    }


    override fun getNoOfGuestsInEachRoom(): List<Int> {
        return noOfGuestsInEachRoom
    }

    override fun getTotalPrice(): Double {
        return totalPrice
    }

    fun setTotalPrice(totalPrice : Double){
        this.totalPrice=totalPrice
    }

}