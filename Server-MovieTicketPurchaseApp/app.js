var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var apiRouter = require('./routes/api')

const database = require('./config/db');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: true })); // to parse URL-encoded bodies
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

// Routers
app.use('/', indexRouter);
app.use('/api',apiRouter)
database.connect();


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

   // Log the error for debugging purposes
   console.error(err.stack);

    // Respond with JSON for API requests
  if (req.originalUrl.startsWith('/api')) {
    return res.status(err.status || 500).json({
      status: err.status || 500,
      message: err.message || 'Internal Server Error',
      error: err.stack // You can adjust what you want to send back as the error information
    });
  }

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
