const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Booking = new Schema({
    userId: {type: String},
    showTimeId: {type: String},
    paymentId: {type: String},
    bookingDate: {type: String},
    totalAmount: {type: String},
})
module.exports = mongoose.model("booking",Booking)