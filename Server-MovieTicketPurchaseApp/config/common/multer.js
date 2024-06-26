const multer = require('multer')
const path = require('path')

const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/');
    },
    filename: (req, file, cb) => {
        cb(null, `${Date.now()}-${file.originalname}`)
    },
});

const fileFilter = (req, file, cb) =>{
    // allow only images and video
    const filetypes = /jpeg|jpg|png|mp4/;
    const extname = filetypes.test(path.extname(file.originalname).toLowerCase())
    const mimetype = filetypes.test(file.mimetype)

    if(extname && mimetype){
        return cb(null, true)
    }else{
        cb("Error: Image and video only")
    }
}

const uploadFile = multer({
    storage,
    fileFilter,
    limits:{fieldSize: 1024 * 1024 * 100 } // limit file size to 100MB
})

module.exports = uploadFile;