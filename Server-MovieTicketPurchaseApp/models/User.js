const mongoose = require('mongoose')
const Schema = mongoose.Schema

const User = new Schema({
    username: {type: String},
    name: {type: String},
    email: {type: String},
    password: {type: String},
    role: { type: Number, default: 1 }, // 0 nhan vien, 1 khach hang
    avatar: {type: String},
    phoneNumber: {type: String},
})
module.exports = mongoose.model("user",User)