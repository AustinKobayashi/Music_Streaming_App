var connection = require('../routes/database');

exports.song_list = function(req, res){
    connection.query('select * from song', function (error, results, fields) {
        console.log(results);
        res.append('Content-Type', 'application/json');
        res.send(JSON.stringify(results));
        //res.render('songList', {title: 'Songs', song_list: results});
    });
};


exports.song_detail = function(req, res){

    connection.query('select * from song where id = ' + req.params.id, function (error, results, fields) {
        console.log(results);
        res.redirect('../' + results[0].url);
        //res.render('songList', {title: 'Songs', song_list: results});
    });
};