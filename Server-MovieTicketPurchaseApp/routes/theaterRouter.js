var express = require('express');
var router = express.Router();
// model
const Theater = require("../models/Theater")

// api
router.post("/add-theater", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const newTheater = new Theater({
            name: data.name,
            location: data.location,
            contact: data.contact
        });
        const result = await newTheater.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add theater successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add theater failed",
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
router.get("/get-theater-list", async(req, res) =>{
    try {
        const data = await Theater.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get theater list failed",
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
router.delete("/delete-theater/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await Theater.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Theater deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, Theater deletion failed",
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
router.put("/update-theater/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const theaterUpdate = await Theater.findById(id)
        if (!theaterUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No Theater found to update',
                data: []
            });
        }

        theaterUpdate.name = data.name ?? theaterUpdate.name
        theaterUpdate.location = data.location ?? theaterUpdate.location
        theaterUpdate.contact = data.contact ?? theaterUpdate.contact

        const result = await theaterUpdate.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'theater update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'theater update failed',
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
router.get("/get-theater-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const theater = await Theater.findById(id)

        if (!theater) {
            return res.status(404).json({
                status: 404,
                messenger: 'theater not found'
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
