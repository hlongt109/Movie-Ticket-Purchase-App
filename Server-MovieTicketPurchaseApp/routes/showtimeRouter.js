var express = require('express');
var router = express.Router();
// model
const ShowTime = require("../models/ShowTime")

// api
router.post("/add-showtime", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const showTime = new ShowTime({
            movieId: data.movieId,
            nameMovie: data.nameMovie,
            theaterId: data.theaterId,
            cinemaHallId: data.cinemaHallId,
            showTimeDate: data.showTimeDate,
            timeFrameId: data.timeFrameId,
            price: data.price
        });
        const result = await showTime.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add shotime successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add showtime failed",
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
router.get("/get-showtime-list", async(req, res) =>{
    try {
        const data = await ShowTime.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get showtime list failed",
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
router.delete("/delete-showtime/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await ShowTime.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "showtime deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, showtime deletion failed",
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
router.put("/update-showtime/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const showtime = await ShowTime.findById(id)
        if (!showtime) {
            return res.status(404).json({
                status: 404,
                messenger: 'No showtime found to update',
                data: []
            });
        }

        showtime.movieId = data.movieId ?? showtime.movieId,
        showtime.nameMovie = data.nameMovie ?? showtime.nameMovie
        showtime.theaterId = data.theaterId ?? showtime.theaterId
        showtime.cinemaHallId = data.cinemaHallId ?? showtime.cinemaHallId
        showtime.showTimeDate = data.showTimeDate ?? showtime.showTimeDate
        showtime.timeFrameId = data.timeFrameId ?? showtime.timeFrameId
        showtime.price = data.price ?? showtime.price

        const result = await showtime.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'showtime update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'showtime update failed',
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
router.get("/get-showtime-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const showtime = await ShowTime.findById(id)

        if (!showtime) {
            return res.status(404).json({
                status: 404,
                messenger: 'showtime not found'
            });
        }

        res.status(200).send(showtime)
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
