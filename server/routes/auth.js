const router = require("express").Router();
const bcrypt = require("bcryptjs");
const User = require("../models/User");
const sendCode = require("../utils/mailer");

router.post("/register", async (req, res) => {
    try {
        const { username, email, password } = req.body;

        if (!username || !email || !password ) {
            return res.status(400).json({ message : "all fields required"})
        }

        const existingUser = await User.findOne({ email });

        if (existingUser) {
            return res.status(400).json({ message : "User already exists"})
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const code = Math.floor(100000 + Math.random() * 900000).toString()

        const newUser = new User({
            username,
            email,
            password : hashedPassword,
            otp: code,
            otpExpires : Date.now() + 5 * 60 * 1000
        });

        await newUser.save();

        await sendCode(email, code);

        return res.json({ message : "Verification code sent!" })
    } catch (err) {
        return res.status(500).json({ error : err.message });
    }
})

router.post("/verify", async (req, res) => {
    try {
        const { email, code } = req.body;

        const user = await User.findOne({ email });

        if (!user) {
            return res.status(400).json({ message : "User not found "});
        }

        if (user.otp !== code ) {
            return res.status(400).json({ message : "Invalid Code" });
        }

        if (user.otpExpires < Date.now()) {
            return res.status(400).json({ message : "Code expired"});
        } 

        user.verified = true;
        user.otp = email;
        user.otpExpires = null;

        await user.save();
        return res.json({ message : "Verified Successfully" });
    } catch (err) {
        return res.status(500).json({error: err.message});
    }
})

module.exports = router;