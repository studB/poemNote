import config from '../config/index.js';

function fetchF(url, control){
    return fetch(url, control)
            .then( (response) => {
                if (response.status >= 200 && response.status < 300) {
                    return Promise.resolve(response)
                } else {
                    return Promise.reject(new Error(response.statusText))
                }
            })
            .then( (response) => {
                return response.json();
            })
}

function fetchApi(path){
    const url = config.BASE_URL + 'api/v1' + path;
    const control = {
        method: 'GET',
        headers: {
            // 'Access-Control-Allow-Origin': '*'
        }
    };
    if(config.isProudction){
        control['mode'] = 'cors';
        return fetchF(url, control);
    }else{
        control['mode'] = 'no-cors';
        return fetchF(url, control);
    }
}

function writeApi(path, data){
    const url = config.BASE_URL + 'api/v1' + path;
    const control = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify(data),
    }
    if(config.isProudction){
        control['mode'] = 'cors';
        return fetchF(url, control);
    }else{
        return fetchF(url, control);
    }
}

function deleteApi(path, textId){
    const url = config.BASE_URL + 'api/v1' + path + '/' + textId;
    const control = {
        method: 'DELETE',
        headers: {
            'Access-Control-Allow-Origin': '*'
        }
    }
    if(config.isProudction){
        control['mode'] = 'cors';
        return fetchF(url, control);
    }else{
        return fetchF(url, control);
    }
}

function reWriteApi(path, data){
    const url = config.BASE_URL + 'api/v1' + path;
    const control = {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify(data),
    }
    if(config.isProudction){
        control['mode'] = 'cors';
        return fetchF(url, control);
    }else{
        return fetchF(url, control);
    }
}

function publishApi(path, data){
    const url = config.BASE_URL + 'api/v1' + path + '/publish';
    const control = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        },
        body: JSON.stringify(data),
    }
    if(config.isProudction){
        control['mode'] = 'cors';
        return fetchF(url, control);
    }else{
        return fetchF(url, control);
    }
}

export {
    fetchApi,
    writeApi,
    deleteApi,
    reWriteApi,
    publishApi
}

