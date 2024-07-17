var express = require('express');
var router = express.Router();
// model
const CinemaHall = require("../models/CinemaHall")

// api
router.post("/add-cinemaHall", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const newCinemaHall = new CinemaHall({
            theaterId: data.theaterId,
            name: data.name,
            capacity: data.capacity
        });
        const result = await newCinemaHall.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add cinema hall successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add cinema hall failed",
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
router.get("/get-cinemahall-list", async(req, res) =>{
    try {
        const data = await CinemaHall.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get cinema hall list failed",
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
router.delete("/delete-cinemahall/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await CinemaHall.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "cinema hall deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, cinema hall deletion failed",
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
router.put("/update-cinemahall/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const cinemaHallToUpdate = await CinemaHall.findById(id)
        if (!cinemaHallToUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No cinema hall found to update',
                data: []
            });
        }

        cinemaHallToUpdate.theaterId = data.theaterId ?? cinemaHallToUpdate.theaterId
        cinemaHallToUpdate.name = data.name ?? cinemaHallToUpdate.name
        cinemaHallToUpdate.capacity = data.capacity ?? cinemaHallToUpdate.capacity

        const result = await cinemaHallToUpdate.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'cinema hall update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'cinema hall update failed',
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
router.get("/get-cinemahall-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const cinemaHall = await CinemaHall.findById(id)

        if (!cinemaHall) {
            return res.status(404).json({
                status: 404,
                messenger: 'cinema hall not found'
            });
        }

        res.status(200).send(cinemaHall)
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
