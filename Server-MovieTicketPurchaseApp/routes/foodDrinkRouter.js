var express = require('express');
var router = express.Router();
// model
const FoodDrink = require("../models/FoodAndDrink")
const Upload = require("../config/common/upload")

// api
router.post("/add-fooddrink",Upload.single('image'), async (req, res) => {
    try {
        const data = req.body;
        const {file} = req
       
        if(!file){
            return res.status(400).json({
                "status": 400,
                "message": "Khong co file anh duoc tai len"
            })
        }

        const imageFd = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`;

        const newFoodDrink = new FoodDrink({
            name: data.name,
            price: data.price,
            image: imageFd
        });
        const result = await newFoodDrink.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add food drink successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add food drink failed",
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
router.get("/get-fooddrink-list", async(req, res) =>{
    try {
        const data = await FoodDrink.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get food drink list failed",
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
router.delete("/delete-fooddrink/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await FoodDrink.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "food drink deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, food drink deletion failed",
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
router.put("/update-fooddrink/:id",Upload.single("image"), async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;
        const {file} = req

        const foodDrink = await FoodDrink.findById(id)

        if (!foodDrink) {
            return res.status(404).json({
                status: 404,
                messenger: 'No food drink found to update',
                data: []
            });
        }

        let imageFd;

        if(file && file.length > 0){
            imageFd = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`;
        }else{
            imageFd = foodDrink.image
        }

        foodDrink.name = data.name ?? foodDrink.name
        foodDrink.price = data.price ?? foodDrink.name
        foodDrink.image = imageFd

        const result = await foodDrink.save()

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
router.get("/get-fooddrink-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const foodDrink = await FoodDrink.findById(id)

        if (!foodDrink) {
            return res.status(404).json({
                status: 404,
                messenger: 'food drink not found'
            });
        }

        res.status(200).send(foodDrink)
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
