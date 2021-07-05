// https://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid
function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function getTextId(selectedCategory){
    const uuid = uuidv4();
    switch (selectedCategory) {
        case 'poem':
            return 'p-' + uuid;
        case 'review':
            return 'r-' + uuid;
        case 'theory':
            return 't-' + uuid;
        case 'opinion':
            return 'o-' + uuid;
        case 'line':
            return 'l-' + uuid;
    }
}

export {
    getTextId
}