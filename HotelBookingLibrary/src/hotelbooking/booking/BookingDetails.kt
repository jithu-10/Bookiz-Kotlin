package hotelbooking.booking

import hotelbooking.hotel.HotelPanel
import hotelbooking.hotel.room.RoomCustomerPanel
import hotelbooking.users.UserData
import java.util.*

internal class BookingDetails(
    private val checkInDate : Date,
    private val checkOutDate : Date,
    private val customerData : UserData,
    private val hotel : HotelPanel,
    private val noOfGuestsInEachRoom : List<Int>,
    private val bookedRooms : List<RoomCustomerPanel>,
    private val paid : Boolean,
    private var bookingStatus : BookingStatus
) {

    private val customerBookingPanel : CustomerBookingPanel = CustomerBookingPanel(this)
    private val hotelBookingPanel : HotelBookingPanel = HotelBookingPanel(this)

    private val id = generateId();
    private var totalPrice :Double = 0.0

    companion object{
        private var id_helper=9999;

        fun generateId() : Int{
            return ++id_helper;
        }
    }


    fun getBookingID(): Int {
        return id;
    }

    fun getCheckInDate(): Date {
        return checkInDate;
    }

    fun getCheckOutDate(): Date {
        return checkOutDate;
    }

    fun getCustomerData(): UserData {
        return customerData;
    }

    fun getHotel(): HotelPanel {
        return hotel;
    }

    fun getBookingStatus(): BookingStatus {
        return bookingStatus;
    }

    fun setBookingStatus(bookingStatus: BookingStatus) {
        this.bookingStatus=bookingStatus
    }

    fun getNoOfRoomsBooked(): Int {
        return noOfGuestsInEachRoom.size
    }

    fun getBookedRooms(): List<RoomCustomerPanel> {
        return bookedRooms
    }

    fun getPaymentStatus(): Boolean {
        return paid
    }


    fun getNoOfGuestsInEachRoom(): List<Int> {
        return noOfGuestsInEachRoom
    }

    fun getTotalPrice(): Double {
        return totalPrice
    }

    fun setTotalPrice(totalPrice : Double){
        this.totalPrice=totalPrice
    }

    fun getCustomerBookingPanel() : CustomerBookingPanel{
        return customerBookingPanel
    }

    fun getHotelBookingPanel() : HotelBookingPanel{
        return hotelBookingPanel
    }

}