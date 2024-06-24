const mongooes = require('mongoose');
mongooes.set('strictQuery' , true)
const url_db = "mongodb://127.0.0.1:27017/Movie_Ticket_Purchase_App"
const connect = async () =>{
    try {
        await mongooes.connect(url_db)
        console.log('Connect success');
    } catch (error) {
        console.log("error :"+ error);
        console.log('Connect failed');
    }
}

module.exports = {connect}