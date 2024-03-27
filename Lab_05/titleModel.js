const mongoose = require('mongoose');

const TitleSchema = new mongoose.Schema({
    name: {
        type: String,
        require: true
    }
})
const titleModel = new mongoose.model('tieude', TitleSchema);
module.exports = titleModel;