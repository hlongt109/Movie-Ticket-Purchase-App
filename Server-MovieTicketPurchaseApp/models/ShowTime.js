const mongoose = require('mongoose')
const Schema = mongoose.Schema

const ShowTime = new Schema({
    movieId: {type: String},
    cinemaHallId: {type: String},
    showTimeStart: {type: String},
    showTimeEnd: {type: String},
    price: {type: Number}
})
module.exports = mongoose.model("showTime",ShowTime)