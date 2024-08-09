var express = require('express');
var router = express.Router();
const bcrypt = require('bcrypt');
const User = require('../models/User')
const Transporter = require('../config/common/mail')
const UploadFile = require('../config/common/multer')
//
router.post("/register-new-account", async (req, res) => {
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
                text: "Thank you for registering an account to use the LHB Cinema app",
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
// router.post("/register-new-account", async (req, res) => {
//     try {
//         const data = req.body;
//         const saltRounds = 10; // số lần băm

//         // Băm mật khẩu
//         const hashedPassword = await bcrypt.hash(data.password, saltRounds);

//         const newUser = new User({
//             username: data.username,
//             name: data.name,
//             email: data.email,
//             password: hashedPassword, // lưu mật khẩu đã băm
//             role: data.role,
//             avatar: data.avatar,
//             phoneNumber: data.phoneNumber
//         });

//         const result = await newUser.save();
//         if (result) {
//             const mailOptions = {
//                 from: "hlong109.it@gmail.com",
//                 to: result.email,
//                 subject: "Account registration successful",
//                 text: "Thank you for registering an account to use the LHB Cinema app",
//             };
//             await Transporter.sendMail(mailOptions);
//             res.json({
//                 "status": 200,
//                 "messenger": "Account registration successful",
//                 "data": result
//             });
//         } else {
//             res.json({
//                 "status": 400,
//                 "messenger": "Account registration failed.",
//                 "data": []
//             });
//         }
//     } catch (error) {
//         console.error("Error: " + error);
//         res.status(500).json({
//             "status": 500,
//             "message": "Server error",
//             "error": error.message
//         });
//     }
// });
// login 
const JWT = require('jsonwebtoken')
const SECRETKEY = "LHBCINEMA"

router.post("/login", async (req, res) => {
    try {
        const {email, password } = req.body;
        console.log(`email:${email},password:${password}`)
        const user = await User.findOne({ email, password })

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
                "data": {},
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
// router.post("/login", async (req, res) => {
//     try {
//         const { email, password } = req.body;

//         const user = await User.findOne({ email, password })

//         if (user) {
//             // Kiểm tra mật khẩu
//             const match = await bcrypt.compare(password, user.password);
//             if (match) {
//                 const token = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '1h' });
//                 const refreshToken = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '30d' });

//                 res.json({
//                     "status": 200,
//                     "messenger": "Login successfully",
//                     "data": user,
//                     "token": token,
//                     "refeshToken": refreshToken,
//                     "role": user.role
//                 })
//             } else {
//                 res.json({
//                     "status": 400,
//                     "messenger": "Error login,account not found",
//                     "data": {},
//                 })
//             }
//         } else {
//             res.json({
//                 "status": 400,
//                 "messenger": "Error login,account not found",
//                 "data": {},
//             })
//         }
//     } catch (error) {
//         console.error("Error: " + error);
//         res.status(500).json({
//             "status": 500,
//             "message": "Server error",
//             "error": error.message
//         });
//     }
// });
// update personal information
router.put("update-personal-information/:id", UploadFile.single("avatar"), async (req, res) => {
    try {
        const { id } = req.params;
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
        if (result) {
            res.json({
                status: 200,
                message: "User information updated successfully",
                data: result
            });
        } else {
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
router.delete("/delete-account/:id", async (req, res) => {
    try {
        const { id } = req.params
        const result = await User.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Account deleted successfully",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Error, Account deletion failed",
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
router.get("/get-infomation-details/:id", async (req, res) => {
    try {
        const { id } = req.params
        const user = await User.findById(id)

        if (!user) {
            return res.status(404).json({
                status: 404,
                messenger: 'user not found'
            });
        }

        res.status(200).send(user)
    } catch (error) {
        console.error("Error: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Server error",
            "error": error.message
        });
    }
})
router.get("/get-all-user", async (req, res) =>{
    try {
        const data = await User.find();
        if (data) {
            res.status(200).send(data)
        } else {
            res.json({
                "status": 400,
                "messenger": "Get user list failed",
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
router.post("/add-user", async (req, res) => {
    try {
        const data = req.body

        const user = new User({
            username: data.username,
            name: data.name,
            email: data.email,
            password: data.password,
            role: data.role,
            avatar: data.avatar,
            phoneNumber: data.phoneNumber
        })
        const result = await user.save()

        if (result) {
            res.json({
                "status": 200,
                "message": "Add user successfully",
            });
        } else {
            res.json({
                "status": 400,
                "message": "Error, Add user failed",
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
router.put("/update-user/:id",async(req, res) =>{
    try {
        const {id} = req.params
        const data = req.body;

        const user = await User.findById(id)
        if(!user){
            return res.status(404).json({
                status: 404,
                messenger: 'No user found to update',
                data: []
            });
        }

        user.username = data.username ?? user.username
        user.name = data.name ?? user.name;
        user.email = data.email ?? user.email;
        user.password = data.password ?? user.password;
        user.role = data.role ?? user.role;
        user.avatar = data.avatar ?? user.avatar;
        user.phoneNumber = data.phoneNumber ?? user.phoneNumber;

        const result = await user.save()

        if (result) {
            res.json({
                status: 200,
                messenger: 'user update successfully',
                data: result
            });
        } else {
            res.json({
                status: 400,
                messenger: 'user update failed',
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

module.exports = router