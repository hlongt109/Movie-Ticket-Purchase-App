var express = require('express');
var router = express.Router();

const movieRouter = require('./movieRouter')
const movieTypeRouter = require('./movieTypeRouter')
const theaterRouter = require('./theaterRouter')
const cinemaHallRouter = require('./cinemaHallRouter')
const bookingRouter = require('./bookingRouter')
const bookingItemRouter = require('./bookingItemRouter')
const foodDrinkRouter = require('./foodDrinkRouter')
const paymentRouter = require('./paymentRouter')
const seatRouter = require('./seatRouter')
const showTimeRouter = require('./showtimeRouter')
const ticketRouter = require('./ticketRouter')
const userRouter = require('./userRouter')
const timeFrameRouter = require('./timeFrameRouter')
const favouriteRouter = require('./favouriteRouter')

router.use('',movieRouter)
router.use('',movieTypeRouter)
router.use('',theaterRouter)
router.use('',cinemaHallRouter)
router.use('',bookingRouter)
router.use('',bookingItemRouter)
router.use('',foodDrinkRouter)
router.use('',paymentRouter)
router.use('',seatRouter)
router.use('', showTimeRouter)
router.use('', ticketRouter)
router.use('', userRouter)
router.use('',timeFrameRouter)
router.use('',favouriteRouter)

module.exports = router