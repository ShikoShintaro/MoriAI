const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema({
    username: String,
    email: {
        type: String,
        unique: true
    },
    password: String,
    otp : {
        type: String
    },
    otpExpires : {
        type: Date
    },
    verified : {
        type : Boolean,
        default : false
    },
    resetOtp : {
        type : String,
    },
    resetOtpExpires : {
        type : Date
    },

    fullName : {
        type : String,
        required : true
    },

    course : {
        type : String,
        required : true
    },

    birthdate : {
        type : String,
        required : true
    },

    section : {
        type : String,
        required : true
    },

    year : {
        type : String
        required : true
    },



});

module.exports = mongoose.model("User", UserSchema);