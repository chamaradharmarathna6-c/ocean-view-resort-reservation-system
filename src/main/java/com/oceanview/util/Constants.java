package com.oceanview.util;


public class Constants {


    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";


    public static final String ROOM_TYPE_SINGLE = "SINGLE";
    public static final String ROOM_TYPE_DOUBLE = "DOUBLE";
    public static final String ROOM_TYPE_SUITE = "SUITE";
    public static final String ROOM_TYPE_DELUXE = "DELUXE";


    public static final String ROOM_AVAILABLE = "AVAILABLE";
    public static final String ROOM_OCCUPIED = "OCCUPIED";
    public static final String ROOM_MAINTENANCE = "MAINTENANCE";
    public static final int MAX_ROOMS_PER_TYPE = 5;


    public static final String RESERVATION_PENDING = "PENDING";
    public static final String RESERVATION_CONFIRMED = "CONFIRMED";
    public static final String RESERVATION_CHECKED_IN = "CHECKED_IN";
    public static final String RESERVATION_CHECKED_OUT = "CHECKED_OUT";
    public static final String RESERVATION_CANCELLED = "CANCELLED";


    public static final String BILL_PENDING = "PENDING";
    public static final String BILL_PAID = "PAID";
    public static final String BILL_PARTIALLY_PAID = "PARTIALLY_PAID";


    public static final double RATE_SINGLE = 5000.0;
    public static final double RATE_DOUBLE = 8000.0;
    public static final double RATE_SUITE = 12000.0;
    public static final double RATE_DELUXE = 15000.0;


    public static final String COLOR_PRIMARY = "Dodger Blue";
    public static final String COLOR_DARK = "Navy";
    public static final String COLOR_LIGHT = "#White";
    public static final String COLOR_ACCENT = "#Yellow";


    public static final String SESSION_USER = "currentUser";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final String SESSION_USERNAME = "username";


    public static final String PARAM_ACTION = "action";
    public static final String PARAM_RESERVATION_NUMBER = "reservationNumber";
    public static final String PARAM_GUEST_NAME = "guestName";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_CONTACT = "contactNumber";
    public static final String PARAM_ROOM_TYPE = "roomType";
    public static final String PARAM_CHECKIN = "checkInDate";
    public static final String PARAM_CHECKOUT = "checkOutDate";


    public static final String BACKUP_DIR = "backups/";
    public static final String LOG_DIR = "logs/";


    public static final String MSG_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String MSG_USER_ALREADY_EXISTS = "User already exists";
    public static final String MSG_RESERVATION_SUCCESS = "Reservation added successfully";
    public static final String MSG_RESERVATION_DELETE_SUCCESS = "Reservation deleted successfully";
    public static final String MSG_INVALID_DATES = "Check-out date must be after check-in date";
    public static final String MSG_BILL_CALCULATED = "Bill calculated successfully";
    public static final String MSG_ROOM_NOT_AVAILABLE = "All rooms of this type are fully booked during the selected dates";
}

