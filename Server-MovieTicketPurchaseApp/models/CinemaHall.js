const mongoose = require('mongoose')
const Schema = mongoose.Schema

const CinemaHall = new Schema({
    theaterId: {type: String},
    name:{type:String},
    capacity: {type: Number} // sức chứa
})
module.exports = mongoose.model("cinemaHall",CinemaHall)