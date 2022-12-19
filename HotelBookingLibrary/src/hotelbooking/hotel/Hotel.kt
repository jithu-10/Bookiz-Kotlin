package hotelbooking.hotel

import hotelbooking.db.AmenityDB
import hotelbooking.booking.HotelBookingInterface
import hotelbooking.getDatesBetweenTwoDates
import hotelbooking.hotel.room.Room
import hotelbooking.users.UserData
import java.util.*

internal class Hotel(
    private val hotelOwner : UserData,
    private val name : String,
    private val address: Address,
): HotelAdminInterface, HotelCustomerInterface, HotelAppAdminInterface
{
    private var hotelType : HotelType = HotelType.STANDARD
    private var approvalStatus: HotelApprovalStatus = HotelApprovalStatus.ON_PROCESS
    private var rooms : ArrayList<Room> = ArrayList()
    private var amenities : ArrayList<Amenity> = ArrayList()
    private var bookings : ArrayList<HotelBookingInterface> = ArrayList()
    private var roomBookedStatus : HashMap<Room,ArrayList<Date>> = HashMap();
    private val id : Int = generateId();

    companion object{
        private var id_helper=0;

        fun generateId() : Int{
            return ++id_helper;
        }
    }

    override fun getID() : Int{
        return id;
    }
    override fun getName() : String{
        return name;
    }

    override fun getAddress() : Address {
        return address;
    }

    override fun getPhoneNumber() : Long{
        return hotelOwner.phoneNumber
    }

    override fun getHotelType() : HotelType {
        return hotelType;
    }

    override fun setHotelType(hotelType: HotelType){
        this.hotelType=hotelType
    }

    override fun setHotelType() {
        val amenityPercent = getTotalAmenityPercent()
        hotelType = if (amenityPercent >= 90) {
            HotelType.ELITE
        } else if (amenityPercent >= 50) {
            HotelType.PREMIUM
        } else {
            HotelType.STANDARD
        }
    }

    override fun getApprovalStatus() : HotelApprovalStatus {
        return approvalStatus;
    }

    override fun setApprovalStatus(approvalStatus: HotelApprovalStatus){
        this.approvalStatus=approvalStatus
    }

    override fun getRooms() : List<Room>{
        return rooms;
    }

    override fun getTotalNumberOfRooms(): Int {
        return rooms.size
    }

    override fun addRoom(room : Room) {
        rooms.add(room)
    }

    override fun removeRoom(room : Room){
        rooms.remove(room)
    }

    override fun getAmenities() : List<Amenity>{
        return amenities;
    }

    override fun getAllAmenities() : List<Amenity>{
        return AmenityDB.getAmenities()

    }

    override fun getTotalAmenityPercent() : Int{
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

    override fun addAmenity(amenity: Amenity){
        amenities.add(amenity)
    }

    override fun removeAmenity(amenity: Amenity){
        amenities.remove(amenity)
    }

    internal fun addBooking(booking : HotelBookingInterface){
        bookings.add(booking);
        addRoomBooking(booking.getCheckInDate(),booking.getCheckOutDate(),booking.getBookedRooms())
    }

    private fun addRoomBooking(checkInDate : Date, checkOutDate : Date, rooms : List<Room>){
        for(room in rooms){
            updateRoomBookedStatus(room,checkInDate,checkOutDate,true)
        }
    }

    internal fun removeBooking(booking : HotelBookingInterface){
        bookings.remove(booking)
        cancelRoomBooking(booking.getCheckInDate(),booking.getCheckOutDate(),booking.getBookedRooms())
    }

    private fun cancelRoomBooking(checkInDate: Date,checkOutDate: Date,rooms : List<Room>){
        for(room in rooms){
            updateRoomBookedStatus(room,checkInDate,checkOutDate,false)
        }
    }

    internal fun getBookingByID(id : Int): HotelBookingInterface {
        for(booking in bookings){
            if(booking in bookings) {
                if(booking.getBookingID()==id)return booking

            }
        }
        throw Exception("Not Found")
    }

    override fun getBookings(): List<HotelBookingInterface>{
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

    override fun getNoOfRoomsBookedByDate(date: Date): Int {
        var value = 0
        for (room in rooms) {
            if (checkRoomBookedByDate(room, date)) {
                value++
            }
        }
        return value
    }








}
