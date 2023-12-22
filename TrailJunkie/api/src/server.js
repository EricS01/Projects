const express = require('express');
const router = require('./routes/routes');
const cookieParser = require("cookie-parser");

const app = express();
const PORT = process.env.PORT;

app.use(cookieParser());
app.use(express.json());

app.use(router);

app.listen(PORT, () => console.log(`Server listening on port: ${PORT}`));