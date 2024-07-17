var express = require('express');
var router = express.Router();
// model
const Booking = require("../models/Booking")

// api
router.post("/add-booking", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const newBooking = new Booking({
            userId: data.userId,
            showTimeId: data.showTimeId,
            paymentId: data.paymentId,
            bookingDate: data.bookingDate,
            totalAmount: data.totalAmount
        });
        const result = await newBooking.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add booking successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add booking failed",
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
router.get("/get-booking-list", async(req, res) =>{
    try {
        const data = await Booking.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get booking list failed",
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
router.delete("/delete-booking/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await Booking.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Booking deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, Booking deletion failed",
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
router.put("/update-booking/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const booingToUpdate = await Booking.findById(id)
        if (!booingToUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No booking found to update',
                data: []
            });
        }

        booingToUpdate.userId = data.userId ?? booingToUpdate.userId
        booingToUpdate.showTimeId = data.showTimeId ?? booingToUpdate.showTimeId
        booingToUpdate.paymentId = data.paymentId ?? booingToUpdate.paymentId
        booingToUpdate.bookingDate = data.bookingDate ?? booingToUpdate.bookingDate
        booingToUpdate.totalAmount = data.totalAmount ?? booingToUpdate.totalAmount

        const result = await booingToUpdate.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'boking update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'booking update failed',
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
router.get("/get-booking-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const theater = await Booking.findById(id)

        if (!theater) {
            return res.status(404).json({
                status: 404,
                messenger: 'booking not found'
            });
        }

        res.status(200).send(theater)
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
