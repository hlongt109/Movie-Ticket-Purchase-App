var express = require('express');
var router = express.Router();
// model
const Saet = require("../models/Seat");
const Seat = require('../models/Seat');

// api
router.post("/add-seat", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const seat = new Seat({
            cinemaHallId: data.cinemaHallId,
            seatName: data.seatName,
            status: data.status
        });
        const result = await seat.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add seat successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add seat failed",
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
router.get("/get-seat-list", async(req, res) =>{
    try {
        const data = await Seat.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get seat list failed",
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
router.delete("/delete-seat/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await Seat.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "seat deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, seat deletion failed",
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
router.put("/update-seat/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const seat = await Seat.findById(id)
        if (!seat) {
            return res.status(404).json({
                status: 404,
                messenger: 'No seat found to update',
                data: []
            });
        }

        seat.cinemaHallId = data.cinemaHallId ?? seat.cinemaHallId
        seat.seatName = data.seatName ?? seat.seatName
        seat.status = data.status ?? seat.status

        const result = await seat.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'seat update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'seat update failed',
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
router.get("/get-seat-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const seat = await Seat.findById(id)

        if (!seat) {
            return res.status(404).json({
                status: 404,
                messenger: 'seat not found'
            });
        }

        res.status(200).send(seat)
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
