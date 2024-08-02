package com.lhb.movieticketpurchaseapp.model

//data class Seat(
//    val id: String,
//    val cinemaHallId: String,
//    val seatName: String,
//    val status: Int // 0 is empty, 1 is selected, 2 is ordered
//)
data class Seat(
    val id: String,
    var row: Char,
    val number: Int,
    var status: SeatStatus
)