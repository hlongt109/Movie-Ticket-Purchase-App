const mongoose = require('mongoose')
const Schema = mongoose.Schema

const TimeFrame = new Schema({
    startTime: {type: String},       
    endTime: {type: String}   
})
module.exports = mongoose.model("timeFrame",TimeFrame)