var express = require('express');
var router = express.Router();
// model
const BookingItem = require("../models/BookingItem")

// api
router.post("/add-booking-item", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const newBookingItem = new BookingItem({
            bookingId: data.bookingId,
            itemId: data.itemId,
            price: data.price,
            quantity: data.quantity
        });
        const result = await newBookingItem.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add booking item successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add booking item failed",
                "data": []
            });
        }
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
});
// get
router.get("/get-booking-item-list", async(req, res) =>{
    try {
        const data = await BookingItem.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get booking item list failed",
                "data": []
            })
        }
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
})
// delete
router.delete("/delete-booking-item/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await BookingItem.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "booking item deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, booking item deletion failed",
                "data": []
            })
        }
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
})
// update
router.put("/update-booking-item/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const bookingItemToUpdate = await BookingItem.findById(id)
        if (!bookingItemToUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No booking item found to update',
                data: []
            });
        }

        bookingItemToUpdate.bookingId = data.bookingId ?? bookingItemToUpdate.bookingId
        bookingItemToUpdate.itemId = data.itemId ?? bookingItemToUpdate.itemId
        bookingItemToUpdate.price = data.price ?? bookingItemToUpdate.price
        bookingItemToUpdate.quantity = data.quantity ?? bookingItemToUpdate.quantity

        const result = await bookingItemToUpdate.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'booking item update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'booking item update failed',
                data: []
            });
        }
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
})
// get one
router.get("/get-booking-item-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const bookingItem = await BookingItem.findById(id)

        if (!bookingItem) {
            return res.status(404).json({
                status: 404,
                messenger: 'booking item not found'
            });
        }

        res.status(200).send(bookingItem)
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
})

module.exports = router;
