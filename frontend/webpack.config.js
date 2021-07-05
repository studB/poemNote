const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    mode: 'production',
    entry: './src/main.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, '../src/main/resources/static'),
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                use: [
                    {
                        loader: 'vue-loader'
                    }
                ]
            },
            {
                test: /\.css$/,
                use: ['vue-style-loader', 'css-loader']
            },
            {
                test: /\.(png|jpe?g|gif)$/i,
                loader: 'file-loader',
                options: {
                    name: 'img/[name].[ext]'
                }
            }
        ]
    },
    // devServer: {
    //     port: 9000,
    //     clientLogLevel: 'debug',
    //     onListening: function(){
    //         console.log("Dev Server Started");
    //     },
    //     proxy: {
    //         '/api': 'http://localhost:8080'
    //     }
    // },
    plugins: [
        new VueLoaderPlugin(),
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, 'public/index.html')
        })
    ]
};