const mongoose = require('mongoose')
const Schema = mongoose.Schema

const Payment = new Schema({
   method: {type: String},
   status: {type: Boolean, default: false},
   transactionDate: {type: String}
})
module.exports = mongoose.model("payment",Payment)