var connection = require('../routes/database');

exports.album_list = function(req, res){
    connection.query('select * from album', function (error, results, fields) {
        console.log(results);
        res.append('Content-Type', 'application/json');
        res.send(JSON.stringify(results));
        //res.render('albumList', {title: 'Albums', album_list: results});
    });
};


exports.album_detail = function(req, res){

    var title;

    connection.query('select * from album where id = ' + req.params.id, function (error, results, fields) {
        title = results[0].name;
    });

    connection.query('select * from song where album_id = ' + req.params.id, function (error, results, fields) {
        console.log(results);
        res.append('Content-Type', 'application/json');
        res.send(JSON.stringify(results));
        //res.render('albumDetails', {title: title, song_list: results});
    });
};