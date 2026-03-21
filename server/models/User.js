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
    }

});

module.exports = mongoose.model("User", UserSchema);