var express = require('express');
var router = express.Router();

const movieRouter = require('./movieRouter')
const movieTypeRouter = require('./movieTypeRouter')

router.use('',movieRouter)
router.use('',movieTypeRouter)

module.exports = router