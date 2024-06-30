const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Seat = new Schema({
    cinemaHallId: {type: String},
    seatName: {type: String},
    status: {type: Number} // 0 ghế trông, 1 đang chọn, 2 đã đặt
})
module.exports = mongoose.model("seat",Seat)