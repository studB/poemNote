const DEV_URL = 'http://localhost:9999/'
const PROD_URL = 'http://localhost:9000/'

const config = {
    isProudction: process.env.NODE_ENV !== 'development', 
    BASE_URL: process.env.NODE_ENV === 'development' ? DEV_URL : PROD_URL,
}

export default config;