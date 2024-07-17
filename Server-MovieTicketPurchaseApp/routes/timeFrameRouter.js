var express = require('express');
var router = express.Router();
// model
const TimeFrame = require("../models/TimeFrame")

// api
router.post("/add-time-frame", async (req, res) => {
    try {
        const data = req.body;

        const timeFrame = new TimeFrame({
           startTime: data.startTime,
           endTime: data.endTime 
        });
        const result = await timeFrame.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add time frame successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add time frame failed",
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
router.get("/get-time-frame-list", async(req, res) =>{
    try {
        const data = await TimeFrame.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get time frame list failed",
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
router.delete("/delete-time-frame/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await TimeFrame.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, delete failed",
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
router.put("/update-time-frame/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const timeFrame = await TimeFrame.findById(id)
        if (!timeFrame) {
            return res.status(404).json({
                status: 404,
                messenger: 'No time frame found to update',
                data: []
            });
        }

        timeFrame.startTime = data.startTime ?? timeFrame.startTime
        timeFrame.endTime = data.endTime ?? timeFrame.endTime

        const result = await timeFrame.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'time frame update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'time frame update failed',
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
router.get("/get-time-frame-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const timeFrame = await TimeFrame.findById(id)

        if (!timeFrame) {
            return res.status(404).json({
                status: 404,
                messenger: 'not found'
            });
        }

        res.status(200).send(timeFrame)
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
