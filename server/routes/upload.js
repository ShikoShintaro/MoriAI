const express = require("express");
const multer = require("multer");
const cloudinary = require("../config/cloudinary");

const router = express.Router();
const upload = multer({ dest : "temp/" });

router.post("/", upload.single("image"), async (req, res) => {
    try {
        const result = await cloudinary.uploader.upload(req.file.path, {
            folder: "profileImages",
            public_id : req.body.userId
        });

        res.json({
            imageUrl : result.secure_url
        });

    } catch (err) {
        res.status(500).json({ error : err.message });
    }
});

module.exports = router;