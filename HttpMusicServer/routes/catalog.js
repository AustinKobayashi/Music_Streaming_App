var express = require('express');
var router = express.Router();

var song_controller = require('../controllers/songController');
var artist_controller = require('../controllers/artistController');
var album_controller = require('../controllers/albumController');
var genre_controller = require('../controllers/genreController');


router.get('/', artist_controller.artist_list);

router.get('/songs', song_controller.song_list);
router.get('/song/:id', song_controller.song_detail);

router.get('/artists', artist_controller.artist_list);
router.get('/artist/:id', artist_controller.artist_detail);

router.get('/albums', album_controller.album_list);
router.get('/album/:id', album_controller.album_detail);

router.get('/genres', genre_controller.genre_list);
router.get('/genre/:id', genre_controller.genre_detail);


module.exports = router;

