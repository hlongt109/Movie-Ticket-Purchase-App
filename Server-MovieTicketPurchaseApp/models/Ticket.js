const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Ticket = new Schema({
   qrCode: {type: String},
   userId: {type: String},
   bookingId: {type: String},
   seatId: {type: String},
   price: {type: Number},
   expirationDate: {type: Number}, // han su dung ve
   status: {type: Boolean} // đã xem , chưa xem. nhân viên quét qr xác nhận
})
module.exports = mongoose.model("ticket",Ticket)