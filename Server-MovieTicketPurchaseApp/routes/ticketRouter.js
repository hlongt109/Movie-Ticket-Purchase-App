var express = require('express');
var router = express.Router();
// model
const Ticket = require("../models/Ticket")

// api
router.post("/add-ticket", async (req, res) => {
    try {
        const data = req.body;
        console.log("Received data:", data);

        const ticket = new Ticket({
            ticketCode: data.ticketCode,
            userId: data.userId,
            bookingId: data.bookingId,
            seatId: data.seatId,
            price: data.price,
            expirationDate: data.expirationDate,
            status: data.status
        });
        const result = await ticket.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Add ticket successfully",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add ticket failed",
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
router.get("/get-ticket-list", async(req, res) =>{
    try {
        const data = await Ticket.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get ticket list failed",
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
router.delete("/delete-ticket/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const result = await Ticket.findByIdAndDelete(id)
        if (result) {
            res.json({
                "status": 200,
                "messenger": "ticket deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, ticket deletion failed",
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
router.put("/update-ticket/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const ticket = await Ticket.findById(id)
        if (!showtime) {
            return res.status(404).json({
                status: 404,
                messenger: 'No ticket found to update',
                data: []
            });
        }

        ticket.ticketCode = data.ticketCode ?? ticket.ticketCode
        ticket.userId = data.userId ?? ticket.userId
        ticket.bookingId = data.bookingId ?? ticket.bookingId
        ticket.seatId = data.seatId ?? ticket.seatId
        ticket.price = data.price ?? ticket.price
        ticket.expirationDate = data.expirationDate ?? ticket.expirationDate
        ticket.status = data.status ?? ticket.status

        const result = await showtime.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'ticket update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'ticket update failed',
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
router.get("/get-ticket-details/:id", async(req, res) =>{
    try {
        const {id} = req.params
        const showtime = await Ticket.findById(id)

        if (!showtime) {
            return res.status(404).json({
                status: 404,
                messenger: 'ticket not found'
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
