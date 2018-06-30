var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {

    res.redirect('/catalog');
    //res.setHeader('Content-Type', 'application/json');
    //res.append('Test', 'hnng');
    //res.send(JSON.stringify({"id": 0, "name":"Heatstroke", "link":"/testmusic.mp3"}));
    //res.render('index', { title: 'Express' });
});

module.exports = router;
