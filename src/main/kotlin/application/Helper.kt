package application

import java.text.SimpleDateFormat
import java.util.*

object InputHelper{
    private val input : Scanner = Scanner(System.`in`)
    private var lastNumberValue = false

    internal fun getIntegerInput(): Int {
        lastNumberValue = true
        val value: Int = try {
            input.nextInt()
        } catch (e: InputMismatchException) {
            input.nextLine()
            println("Enter Value in Integer Format")
            getIntegerInput()
        }
        return value
    }

    internal fun getDoubleInput(): Double {
        lastNumberValue = true
        val value: Double = try {
            input.nextDouble()
        } catch (e: InputMismatchException) {
            input.nextLine()
            println("Enter Value in Double Format")
            getDoubleInput()
        }
        return value
    }


    internal fun getInputWithinRange(start: Int, end: Int, str: String?): Int {
        var str = str
        if (str == null) {
            str ="Enter Input From Given Option"
        }
        val value: Int = getIntegerInput()
        if (value < start || value > end) {
            println(str)
            return getInputWithinRange(start, end, str)
        }
        return value
    }

    internal fun getStringInput(): String {
        var value: String
        if (lastNumberValue) {
            input.nextLine()
        }
        lastNumberValue = false
        try {
            value = input.nextLine().trim { it <= ' ' }
            if (value.isEmpty()) {
                throw RuntimeException("String Can't be empty")
            }
        } catch (e: InputMismatchException) {
            println("Enter value in String format")
            value = getStringInput()
        } catch (e: Exception) {
            value = getStringInput()
        }
        return value
    }

    internal fun getPhoneNumber(): Long {
        lastNumberValue = true
        var phoneNumber: Long
        try {
            phoneNumber = input.nextLong()
            if (phoneNumber.toString().length != 10) {
                throw RuntimeException("Enter Valid Phone Number ")
            }
        } catch (e: InputMismatchException) {
            input.nextLine()
            println("Enter Valid Phone Number ")
            phoneNumber = getPhoneNumber()
        } catch (e: Exception) {
            println("Enter Valid Phone Number ")
            phoneNumber = getPhoneNumber()
        }
        return phoneNumber
    }

    internal fun getEmailInput(): String {
        val email = getStringInput()
        return if (!emailValidator(email)) {
            println("Enter Valid Mail")
            getEmailInput()
        } else {
            email
        }

    }

    internal fun getPhoneNumberOrEmail(): String{
        val mail_phone: String

        return try {
            mail_phone = getStringInput()
            if (emailValidator(mail_phone)) {
                mail_phone
            } else if (phoneNumberValidator(mail_phone)) {
                mail_phone
            } else {
                throw RuntimeException()
            }
        } catch (e: Exception) {
            println("Please Enter Valid Phone Number or Mail Id..")
            getPhoneNumberOrEmail()
        }

    }

    internal fun getPostalCode(): Int {
        lastNumberValue = true
        var postalCode: Int
        try {
            postalCode = input.nextInt()
            if (postalCode.toString().length != 6) {
                throw RuntimeException("Enter Valid Postal Code")
            }
        } catch (e: InputMismatchException) {
            input.nextLine()
            println("Enter Valid Postal Code")
            postalCode = getPostalCode()
        } catch (e: Exception) {
            postalCode = getPostalCode()
            println(e.message)
        }
        return postalCode
    }

    internal fun getDate(): Date {
        val dateStr = getStringInput()
        return if (!dateValidator(dateStr)) {
            println("Enter Valid Date ")
            getDate()
        } else {
            convertStringToDate(dateStr)
        }
    }

    private fun convertStringToDate(dateStr: String): Date {
        var day = 0
        var month = 0
        val year: Int
        var dateSet = false
        var monthSet = false
        var value = 0
        for (i in dateStr.indices) {
            if (dateStr[i] == '-') {
                if (!dateSet) {
                    dateSet = true
                    day = value
                    value = 0
                } else if (!monthSet) {
                    monthSet = true
                    month = value
                    value = 0
                }
            } else {
                value *= 10
                value += (dateStr[i]).code - 48
            }
        }
        year = value
        var date: Date? = null
        try {
            val sdFormat = SimpleDateFormat("yyyy-MM-dd")
            date = sdFormat.parse("$year-$month-$day")
        } catch (e: Exception) {
        }
        return date as Date
    }



    private fun dateValidator(date: String): Boolean {
        return regexValidator("^(29-02-(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$"
                + "|^((0[1-9]|1[0-9]|2[0-8])-02-((19|2[0-9])[0-9]{2}))$"
                + "|^((0[1-9]|[12][0-9]|3[01])-(0[13578]|10|12)-((19|2[0-9])[0-9]{2}))$"
                + "|^((0[1-9]|[12][0-9]|30)-(0[469]|11)-((19|2[0-9])[0-9]{2}))$",date);
    }

    internal fun getCurrentDate(): Date {
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.time
    }

    internal fun modifyString(str: String): String {
        var newStr = StringBuilder()
        for (i in str.indices) {
            if (str[i] == ' ') {
                continue
            }
            newStr.append(str[i])
        }
        newStr = StringBuilder(newStr.toString().uppercase(Locale.getDefault()))
        return newStr.toString()
    }

    internal fun getSimpleDateWithoutYear(date: Date): String {
        val dateFormat = SimpleDateFormat("EEE,dd MMM")
        return dateFormat.format(date)
    }

    private fun emailValidator(email: String): Boolean {
        return regexValidator("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", email)
    }

    private fun phoneNumberValidator(phoneNumber: String): Boolean {
        return regexValidator("([1-9]\\d{9})", phoneNumber)
    }



    private fun regexValidator(regex : String, value : String): Boolean{
        val pattern = Regex(regex)
        return pattern.matches(value)
    }
}