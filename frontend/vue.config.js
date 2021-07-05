module.exports = {
    devServer: {
        port: 9000,
        clientLogLevel: 'debug',
        onListening: function(){
            console.log("Dev Server Started");
        },
        proxy: {
            '/api': {
                target: 'http://localhost:8080'
            }
        }
    }
}