var express = require('express');
var router = express.Router();
// model
const Payment = require("../models/Payment")

// api
router.post("/add-payment", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const payment = new Payment({
            method: data.method,
            status: data.status,
            transactionDate: data.transactionDate
        });
        const result = await payment.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add payment successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add payment failed",
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
router.get("/get-payment-list", async(req, res) =>{
    try {
        const data = await Payment.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get payment list failed",
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
router.delete("/delete-payment/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await Payment.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "payment deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, payment deletion failed",
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
router.put("/update-payment/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const payment = await Payment.findById(id)
        if (!payment) {
            return res.status(404).json({
                status: 404,
                messenger: 'No payment found to update',
                data: []
            });
        }

        payment.method = data.method ?? payment.method
        payment.status = data.status ?? payment.status
        payment.transactionDate = data.transactionDate ?? payment.transactionDate

        const result = await payment.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'pay update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'pay update failed',
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
router.get("/get-payment-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const payment = await Payment.findById(id)

        if (!payment) {
            return res.status(404).json({
                status: 404,
                messenger: 'payment not found'
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
