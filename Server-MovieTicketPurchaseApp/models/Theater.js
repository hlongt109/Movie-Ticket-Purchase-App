const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Theater = new Schema({
    name: {type: String},
    location: {type: String},
    contact: {type: String}
})
module.exports = mongoose.model("theater",Theater)