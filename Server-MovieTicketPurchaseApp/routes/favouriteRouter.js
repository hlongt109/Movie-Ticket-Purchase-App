var express = require('express');
var router = express.Router();

const Favourite = require("../models/Favourite")

router.post("/add-favourite", async (req, res) => {
    try {
        const data = req.body

        const favourite = new Favourite({
            userId: data.userId,
            movieId: data.movieId
        })
        const result = await favourite.save()

        if (result) {
            res.json({
                "status": 200,
                "message": "Add favourite successfully",
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add favourite failed",
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

router.delete("/delete-favourite/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await Favourite.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "favourite deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, favourite deletion failed",
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
router.get("/get-favourite-list", async(req, res) =>{
    try {
        const data = await Favourite.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get favourite list failed",
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
module.exports = router