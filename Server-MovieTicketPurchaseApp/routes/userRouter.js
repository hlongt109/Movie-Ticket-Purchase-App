const express = require('express');
const router = express.Router();

const User = require('../models/User')
const Transporter = require('../config/common/mail')
const UploadFile = require('../config/common/multer')
//
router.post("register-new-account", async (req, res) => {
    try {
        const data = req.body
        const newUser = User({
            username: data.username,
            name: data.name,
            email: data.email,
            password: data.password,
            role: data.role,
            avatar: data.avatar,
            phoneNumber: data.phoneNumber
        })
        const result = await newUser.save()
        if (result) {
            const mailOptions = {
                from: "hlong109.it@gmail.com",
                to: result.email,
                subject: "Account registration successful",
                text: "Thank you for registering",
            };
            await Transporter.sendMail(mailOptions);
            res.json({
                "status": 200,
                "messenger": "Account registration successful",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Account registration failed.",
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
// login 
const JWT = require('jsonwebtoken')
// const e = require("express")
// const { token } = require("morgan")
const SECRETKEY = "LHBCINEMA"

router.post("/login", async (req, res) => {
    try {
        const { username, email, password } = req.body;
        const user = await User.findOne({ username, email, password })

        if(user){
            const token = JWT.sign({id: user._id}, SECRETKEY, {expiresIn: '1h'});
            const refeshToken = JWT.sign({id: user._id},SECRETKEY, {expiresIn: '30d'})

            res.json({
                "status": 200,
                "messenger": "Login successfully",
                "data": user,
                "token": token,
                "refeshToken": refeshToken,
                "role": user.role 
            })
        }else{
            res.json({
                "status": 400,
                "messenger": "Error login,account not found",
                "data": [],
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
// update personal information
router.put("update-personal-information/:id", UploadFile.single("avatar"), async(req,res) => {
    try {
        const {id} = req.params;
        const data = req.body
        const file = req.file;

        const userToUpdate = await User.findById(id);
        if (!userToUpdate) {
            return res.status(404).json({
                status: 404,
                message: 'No user found to update',
                data: []
            });
        }

        let avatar;

        if (file) {
            avatar = `${req.protocol}://${req.get("host")}/uploads/${file.filename}`;
        } else {
            avatar = userToUpdate.avatar;
        }

        // Update 
        userToUpdate.username = data.username ?? userToUpdate.username;
        userToUpdate.name = data.name ?? userToUpdate.name;
        userToUpdate.email = data.email ?? userToUpdate.email;
        userToUpdate.password = data.password ?? userToUpdate.password;
        userToUpdate.role = data.role ?? userToUpdate.role;
        userToUpdate.avatar = avatar;
        userToUpdate.phoneNumber = data.phoneNumber ?? userToUpdate.phoneNumber;

        const result = await userToUpdate.save();
        if(result){
            res.json({
                status: 200,
                message: "User information updated successfully",
                data: result
            });
        }else{
            res.json({
                status: 400,
                messenger: 'User information updated failed',
                data: []
            });
        }
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            status: 500,
            message: "Server error",
            error: error.message
        });
    }
})
// delete
router.get("/delete-account/:id", async(req, res) => {
    try {
        const {id} = req.params
        const result = await User.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Accpunt deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, Accpunt deletion failed",
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