function setLogoMovingEvent(){
    const box = document.querySelector('.logo-box');
    const quil = document.querySelector('.logo-box > img');

    box.addEventListener('mousemove', (e) => {
        let xAxis = ( window.innerWidth / 2 - e.pageX ) / 5;
        let yAxis = ( window.innerHeight / 4 - e.pageY ) / 5;
        box.style.transform = `rotateY(${xAxis}deg) rotateX(${yAxis}deg)`;
    })
    box.addEventListener('mouseenter', () => {
        box.style.transition = 'all 0.2s ease';
        quil.style.transform = 'translateZ(100px)';
    })
    box.addEventListener('mouseleave', () => {
        box.style.transform = `rotateY(0deg) rotateX(0deg)`;
        box.style.transition = 'all 0.5s ease';
        quil.style.transform = 'translateZ(0px)';
    })
}

function removeLogoMovingEvent() {
    const box = document.querySelector('.logo-box');
    box.removeEventListener('mousemove');
    box.removeEventListener('mouseenter');
    box.removeEventListener('mouseleave');
}

export {
    setLogoMovingEvent,
    removeLogoMovingEvent
}