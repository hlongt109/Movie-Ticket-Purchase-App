var express = require('express');
var router = express.Router();
// model
const Movie = require('../models/Movie')
const uploadFile = require('../config/common/upload');

// api
router.post('/add-movie', uploadFile.fields([
    { name: 'poster', maxCount: 1 },
    { name: 'trailer', maxCount: 1 },
    { name: 'images', maxCount: 5 }
]), async (req, res) => {
    try {
        const data = req.body;
        const files = req.files;

        if (!files || !files.images || !files.poster || !files.trailer) {
            return res.status(404).json({
                status: 404,
                message: "Missing image or video files"
            });
        }

        const poster = `${req.protocol}://${req.get("host")}/uploads/${files.poster[0].filename}`;
        const trailer = `${req.protocol}://${req.get("host")}/uploads/${files.trailer[0].filename}`;
        const images = files.images.map(file => `${req.protocol}://${req.get("host")}/uploads/${file.filename}`);

        const newMovie = new Movie({
            title: data.title,
            description: data.description,
            genre: data.genre,
            director: data.director,
            duration: data.duration,
            releaseDate: data.releaseDate,
            showTime: data.showTime,
            rating: data.rating,
            status: data.status,
            poster: poster,
            images: images,
            trailer: trailer
        });

        const result = await newMovie.save();
        if (result) {
            res.json({
                status: 200,
                message: "Add movie successfully",
                data: result
            });
        } else {
            res.json({
                status: 400,
                message: "Error, Add movie failed",
                data: []
            });
        }
    } catch (error) {
        console.error("Error: " + error.message);
        res.status(500).json({
            status: 500,
            message: "Server error",
            error: error.message
        });
    }
});
// update
router.put("/update-movie/:id", uploadFile.fields([
    { name: 'images', maxCount: 5 },
    { name: 'poster', maxCount: 1 },
    { name: 'trailer', maxCount: 1 }
]), async (req, res) => {
    try {
        const { id } = req.params;
        const data = req.body;
        const files = req.files;

        // find the movie to be update
        const MovieToUpdate = await Movie.findById(id);

        if (!MovieToUpdate) {
            return res.status(404).json({
                status: 404,
                messenger: 'No movie found to update',
                data: []
            });
        }

        let images;
        let poster;
        let trailer;

        if (files && files.images) {
            images = files.map(file => `${req.protocol}://${req.get("host")}/uploads/${file.filename}`);
        } else {
            images = MovieToUpdate.images
        }

        if (files && files.poster && files.poster[0]) {
            poster = `${req.protocol}://${req.get("host")}/uploads/${files.poster[0].filename}`
        } else {
            poster = MovieToUpdate.poster
        }

        if (files && files.trailer && files.trailer[0]) {
            trailer = `${req.protocol}://${req.get("host")}/uploads/${files.trailer[0].filename}`;
        } else {
            trailer = MovieToUpdate.trailer
        }

        // update movie fields
        MovieToUpdate.title = data.title ?? MovieToUpdate.title;
        MovieToUpdate.description = data.description ?? MovieToUpdate.description;
        MovieToUpdate.genre = data.genre ?? MovieToUpdate.genre;
        MovieToUpdate.director = data.director ?? MovieToUpdate.director;
        MovieToUpdate.duration = data.duration ?? MovieToUpdate.duration;
        MovieToUpdate.releaseDate = data.releaseDate ?? MovieToUpdate.releaseDate;
        MovieToUpdate.showTime = data.showTime ?? MovieToUpdate.showTime;
        MovieToUpdate.rating = data.rating ?? MovieToUpdate.rating;
        MovieToUpdate.status = data.status ?? MovieToUpdate.status;
        MovieToUpdate.poster = poster;
        MovieToUpdate.images = images;
        MovieToUpdate.trailer = trailer;

        const result = await MovieToUpdate.save();

        if (result) {
            res.json({
                status: 200,
                messenger: 'Movie update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'Movie update failed',
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
// get
router.get("/get-movie-list", async (req, res) => {
    try {
        const data = await Movie.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get movie list failed",
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
router.delete("/delete-movie/:id", async (req, res) => {
    try {
        const {id} = req.params
        const result = await Movie.findByIdAndDelete(id);
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
// get one movie
router.get("/get-movie-details/:id",async(req, res) =>{
    try {
        const {id} = req.params
        const movie = await Movie.findById(id);

        if (!movie) {
            return res.status(404).json({
                status: 404,
                messenger: 'Movie not found'
            });
        }

        // res.status(200).json({
        //     "status":200,
        //     "data": movie
        // })

        // if (movie) {
        //     res.json({
        //         status: 200,
        //         messenger: 'get movie details successfully',
        //         data: movie
        //     });
        // } else {
        //     res.json({
        //         status: 400,
        //         messenger: 'get movie details',
        //         data: []
        //     });
        // }

        res.status(200).send(movie) // cach 2
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