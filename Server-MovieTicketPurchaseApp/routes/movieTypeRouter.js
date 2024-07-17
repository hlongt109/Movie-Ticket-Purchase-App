var express = require('express');
var router = express.Router();
// model
const MovieType = require("../models/MovieType")

// api
router.post("/add-movie-type", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const newMovieType = new MovieType({
            name: data.name
        });
        const result = await newMovieType.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add movie type successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add movie type failed",
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
router.get("/get-movie-type-list", async(req, res) =>{
    try {
        const data = await MovieType.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get movie type list failed",
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
router.delete("/delete-movie-type/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await MovieType.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Movie deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, movie deletion failed",
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
router.put("/update-movie-type/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const movieTypeToUpdate = await MovieType.findById(id)
        if (!movieTypeToUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No movie type found to update',
                data: []
            });
        }

        movieTypeToUpdate.name = data.name ?? movieTypeToUpdate.name
        const result = await movieTypeToUpdate.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'Movie type update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'Movie type update failed',
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
// get one movie type
router.get("/get-movie-type-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const movieType = await MovieType.findById(id)

        if (!movieType) {
            return res.status(404).json({
                status: 404,
                messenger: 'Movie type not found'
            });
        }

        res.status(200).send(movieType)
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
