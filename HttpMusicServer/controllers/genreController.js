var connection = require('../routes/database');


exports.genre_list = function(req, res){
    connection.query('select * from genre', function (error, results, fields) {
        console.log(results);
        res.append('Content-Type', 'application/json');
        res.send(JSON.stringify(results));
        //res.render('genreList', {title: 'Genres', genre_list: results});
    });
};


exports.genre_detail = function(req, res){

    var title;

    connection.query('select name from genre where id = ' + req.params.id, function (error, results, fields) {
        title = results[0].name
    });

    connection.query('select * from song where genre_id = ' + req.params.id, function (error, results, fields) {
        console.log(results);
        res.append('Content-Type', 'application/json');
        res.send(JSON.stringify(results));
        //res.render('genreDetails', {title: title, song_list: results});
    });
};