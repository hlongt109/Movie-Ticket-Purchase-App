var express = require('express');
var router = express.Router();

const movieRouter = require('./movieRouter')
const movieTypeRouter = require('./movieTypeRouter')

router.use('/movie',movieRouter)
router.use('/movieType',movieTypeRouter)

module.exports = router