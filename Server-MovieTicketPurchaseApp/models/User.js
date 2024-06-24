const mongoose = require('mongoose')
const Schema = mongoose.Schema

const User = new Schem({
    name: {type: String},
    email: {type: String},
    password: {type: String}
})