var path = require('path');
var webpack = require('webpack');

var node_dir = __dirname + '/node_modules';

module.exports = {
	entry : './src/main/js/app.js',
	devtool : 'sourcemaps',
	cache : true,
	debug : true,
	resolve : {
		alias : {
			'stompjs' : node_dir + '/stompjs/lib/stomp.js',
		}
	},
	output : {
		path : __dirname,
		filename : './src/main/resources/static/built/bundle.js'
	},
	module : {
		loaders : [ {
			test : path.join(__dirname, '.'),
			exclude : /(node_modules)/,
			loader : 'babel-loader',
			query : {
				cacheDirectory : true,
				presets : [ 'es2015', 'react' ]
			}
		}, {
			test : /\.html$/,
			loader : "file-loader?name=[name].[ext]",
		}, {
			test : /\.css$/,
			loader : "style-loader!css-loader"
		}, {
			test : /\.png$/,
			loader : "url-loader?limit=100000"
		}, {
			test : /\.jpg$/,
			loader : "file-loader"
		} ]
	},
	devServer : {
		historyApiFallback : true
	}
};