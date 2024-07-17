const mongoose = require('mongoose')
const Schema = mongoose.Schema

const ShowTime = new Schema({
    movieId: {type: String},
    theaterId: {type: String},
    nameMovie: {type: String},
    cinemaHallId: {type: String},
    showTimeDate: {type:String},
    timeFrameId: {type: Array},
    price: {type: Number}
})
module.exports = mongoose.model("showTime",ShowTime)