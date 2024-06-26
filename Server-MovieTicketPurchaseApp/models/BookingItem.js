const mongoose = require('mongoose')
const Schema = mongoose.Schema

const BookingItem = new Schema({
    bookingId: {type:String},
    itemId: {type: String},
    quantity: {type: Number}
})
module.exports = mongoose.model("bookingItem",BookingItem)