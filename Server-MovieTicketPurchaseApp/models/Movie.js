const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Movie = new Schema({
    title: { type: String, required: true }, // tieu de
    description: { type: String, required: true }, // mo ta
    genre: { type: String, required: true }, // the loai
    director: { type: String, required: true }, // dao dien
    duration: {type: Number}, // thoi luong bao nhieu phut
    releaseDate: {type: String, required: true}, // ngay phat hanh
    showTime:{type: String},
    rating:{ type: Number}, // danh gia
    poster: { type: String, required: true}, // ap phich (avatar)
    images: { type: Array, required: true}, 
    trailer: { type: String, required: true},
    status: {type: Number}
})
module.exports = mongoose.model("movie", Movie)