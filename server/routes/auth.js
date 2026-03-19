const router = require("express").Router();
const bcrypt = require("bcryptjs");
const User = require("../models/User");
const sendCode = require("../utils/mailer");

router.post("/register", async (req, res) => {
    try {
        const { username, email, password } = req.body;
        const code = Math.floor(100000 + Math.random() * 900000);

        await sendCode(email, code);

        res.json({
            message : "Verification code is now sent",
            code
        })

        if (!username || !email || !password) {
            return res.status(400).json({ message: "All fields required" });
        }

        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).json({ message: "User already exists" });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const newUser = new User({
            username,
            email,
            password: hashedPassword
        });

        await newUser.save();

        res.status(201).json({ message: "Registered successfully" });

    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;