const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Ticket = new Schema({
   ticketCode: {type: String},
   userId: {type: String},
   bookingId: {type: String},
   seatId: {type: Array},
   price: {type: Number},
   expirationDate: {type: String}, // han su dung ve
   status: {type: Boolean} // đã xem , chưa xem. nhân viên quét qr xác nhận
})
module.exports = mongoose.model("ticket",Ticket)