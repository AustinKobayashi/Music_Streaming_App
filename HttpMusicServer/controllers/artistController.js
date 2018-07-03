var connection = require('../routes/database');

exports.artist_list = function(req, res){

    connection.query('SELECT * FROM artist', function (error, results, fields) {
        console.log(results);
        res.append('Content-Type', 'application/json');
        res.send(JSON.stringify(results));
        //res.render('artistList', {title: 'Artists', artist_list: results});
    });
};

exports.artist_detail = function(req, res){

    var title;

    connection.query('select * from artist where id = ' + req.params.id, function (error, results, fields) {
        title = results[0].name;
    });

    connection.query('SELECT * FROM album where artist_id = ' + req.params.id, function (error, results, fields) {
        console.log(results);
        res.append('Content-Type', 'application/json');
        res.send(JSON.stringify(results));
        //res.render('artistAlbums', {title: title, album_list: results});
    });
};

