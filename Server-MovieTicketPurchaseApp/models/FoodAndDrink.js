const mongoose = require('mongoose')
const Schema = mongoose.Schema

const FoodAndDrink = new Schema({
    name: {type: String,required: true},
    price: {type: Number,required: true},
    image: {type: String}
})
module.exports = mongoose.model("foodAndDrink",FoodAndDrink)