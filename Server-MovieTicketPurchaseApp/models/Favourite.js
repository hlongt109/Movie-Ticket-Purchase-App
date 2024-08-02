const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Favourite = new Schema({
    userId: {type: String},
    movieId: {type: String}
})
module.exports = mongoose.model("favourite",Favourite)