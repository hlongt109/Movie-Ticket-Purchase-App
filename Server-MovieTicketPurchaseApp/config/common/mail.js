var nodemailer = require('nodemailer');
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth:{
        user : "hlong109.it@gmail.com", // email gui di
        pass: "whpj dkvg vqhy zmko", // mat email gui di
    }
});
module.exports = transporter;