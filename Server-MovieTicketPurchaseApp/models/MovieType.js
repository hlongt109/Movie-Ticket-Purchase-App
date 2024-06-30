const mongoose = require('mongoose')
const Schema = mongoose.Schema

const MovieType = new Schema({
    name: { type: String}
})
module.exports = mongoose.model("movieType",MovieType)