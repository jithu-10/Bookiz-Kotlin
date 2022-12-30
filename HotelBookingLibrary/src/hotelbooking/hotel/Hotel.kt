package hotelbooking.hotel

import hotelbooking.db.AmenityDB
import hotelbooking.booking.HotelBookingPanel
import hotelbooking.helper.getDatesBetweenTwoDates
import hotelbooking.hotel.room.*
import hotelbooking.users.UserData
import java.util.*
import kotlin.collections.ArrayList

internal class Hotel internal constructor(
    private val hotelOwner : UserData,
    private val name : String,
    private val address: Address,
)
{

    private var hotelAdminInterface : HotelAdminPanel = HotelAdminPanel(this)
    private var hotelCustomerInterface : HotelCustomerPanel = HotelCustomerPanel(this)
    private var hotelAppAdminInterface : HotelAppAdminPanel = HotelAppAdminPanel(this)

    private var hotelType : HotelType = HotelType.STANDARD
    private var approvalStatus: HotelApprovalStatus = HotelApprovalStatus.ON_PROCESS
    private var rooms : ArrayList<Room> = ArrayList()
    private var amenities : ArrayList<Amenity> = ArrayList()
    private var bookings : ArrayList<HotelBookingPanel> = ArrayList()
    private var roomBookedStatus : HashMap<Room,ArrayList<Date>> = HashMap();
    private val id : Int = generateId();

    companion object{
        private var id_helper=0;

        fun generateId() : Int{
            return ++id_helper;
        }
    }

    fun getID() : Int{
        return id;
    }
    fun getName() : String{
        return name;
    }

    fun getAddress() : Address {
        return address;
    }

    fun getPhoneNumber() : Long{
        return hotelOwner.phoneNumber
    }

    fun getHotelType() : HotelType {
        return hotelType;
    }

    fun setHotelType(hotelType: HotelType){
        this.hotelType=hotelType
    }

    fun setHotelType() {
        val amenityPercent = getTotalAmenityPercent()
        hotelType = if (amenityPercent >= 90) {
            HotelType.ELITE
        } else if (amenityPercent >= 50) {
            HotelType.PREMIUM
        } else {
            HotelType.STANDARD
        }
    }

    fun getApprovalStatus() : HotelApprovalStatus {
        return approvalStatus;
    }

    fun getRooms(): List<RoomHotelPanel> {
        return rooms.map{
            it.getRoomHotelPanel()
        }
    }

    fun getRoomsForAdmin(): List<RoomAdminPanel>{
        return rooms.map{
            it.getRoomAdminPanel()
        }

    }



    fun setApprovalStatus(approvalStatus: HotelApprovalStatus){
        this.approvalStatus=approvalStatus
    }



    fun getTotalNumberOfRooms(): Int {
        return rooms.size
    }



    fun addRoom(maxGuest: Int, roomPrice: Price, bedPrice: Price) {
        rooms.add(Room(maxGuest,roomPrice,bedPrice))
    }

    fun removeRoom(room: RoomHotelPanel) {
        rooms.remove(room.room)
    }



    fun getAmenities() : List<Amenity>{
        return amenities;
    }

    fun getAllAmenities() : List<Amenity>{
        return AmenityDB.getAmenities()

    }

    fun getTotalAmenityPercent() : Int{
        val totalAmenityPoints: Double = getAllAmenities().let{ list ->
            var points = 0.0
            list.forEach{
                points += it.points
            }
            points
        }
        val totalHotelAmenityPoints: Double = getAmenities().let{list ->
            var points = 0.0
            list.forEach {
                points += it.points
            }
            points
        }
        return ((totalHotelAmenityPoints / totalAmenityPoints) * 100).toInt()
    }

    fun addAmenity(amenity: Amenity){
        amenities.add(amenity)
    }

    fun removeAmenity(amenity: Amenity){
        amenities.remove(amenity)
    }

    internal fun addBooking(booking : HotelBookingPanel){
        bookings.add(booking);
        addRoomBooking(booking.getCheckInDate(),booking.getCheckOutDate(),booking.getBookedRooms())
    }

    private fun addRoomBooking(checkInDate : Date, checkOutDate : Date, rooms : List<RoomCustomerPanel>){
        for(room in rooms){
            updateRoomBookedStatus(room.room,checkInDate,checkOutDate,true)
        }
    }

    internal fun removeBooking(booking : HotelBookingPanel){
        bookings.remove(booking)
        cancelRoomBooking(booking.getCheckInDate(),booking.getCheckOutDate(),booking.getBookedRooms())
    }

    private fun cancelRoomBooking(checkInDate: Date,checkOutDate: Date,rooms : List<RoomCustomerPanel>){
        for(room in rooms){
            updateRoomBookedStatus(room.room,checkInDate,checkOutDate,false)
        }
    }

    internal fun getBookingByID(id : Int): HotelBookingPanel {
        for(booking in bookings){
            if(booking in bookings) {
                if(booking.getBookingID()==id)return booking

            }
        }
        throw Exception("Not Found")
    }

    fun getBookings(): List<HotelBookingPanel>{
        return bookings
    }

    private fun updateRoomBookedStatus(room: Room, checkInDate: Date, checkOutDate: Date, book : Boolean){
        if(book){
            val bookedDates : ArrayList<Date> = getDatesBetweenTwoDates(checkInDate,checkOutDate)
            bookedDates.add(checkOutDate)
            for (date in bookedDates) {
                if (checkRoomBookedByDate(room, date)) {
                    throw Exception("Already selected rooms are booked on this date for this hotel")
                }
            }
            val previouslyBookedDates = roomBookedStatus[room]
            if (previouslyBookedDates != null) {
                bookedDates.addAll(previouslyBookedDates)
            }

            roomBookedStatus[room] = bookedDates
        } else {

            val unBookedDates: ArrayList<Date> = getDatesBetweenTwoDates(checkInDate, checkOutDate)
            unBookedDates.add(checkOutDate)
            roomBookedStatus[room]!!.removeAll(unBookedDates)
        }
    }

    internal fun checkRoomBookedByDate(room : Room, date: Date): Boolean {
        val bookedDates = roomBookedStatus[room]
        return bookedDates != null && bookedDates.contains(date)
    }

    fun getNoOfRoomsBookedByDate(date: Date): Int {
        var value = 0
        for (room in rooms) {
            if (checkRoomBookedByDate(room, date)) {
                value++
            }
        }
        return value
    }
    
    fun getHotelAdminInterface() : HotelAdminPanel{
        return hotelAdminInterface
    }
    
    fun getHotelAppAdminInterface() : HotelAppAdminPanel{
        return hotelAppAdminInterface
    }
    
    fun getHotelCustomerInterface() : HotelCustomerPanel{
        return hotelCustomerInterface
    }






}
