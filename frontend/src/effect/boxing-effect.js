function autoHeight(selector, height) {
    const box = document.querySelector(selector);
    box.style.height = height + "px";
    box.style.height = (box.scrollHeight) + "px";
}

function lineUpMoving(prpp, prp, pr, line, po, pop, popp) {
    prpp.classList.remove('pre-pre-pre-line');
    prpp.classList.add('post-post-post-line');
    prp.classList.remove('pre-pre-line');
    prp.classList.add('pre-pre-pre-line');
    pr.classList.remove('pre-line');
    pr.classList.add('pre-pre-line');
    line.classList.remove('main-line');
    line.classList.add('pre-line');
    po.classList.remove('post-line');
    po.classList.add('main-line');
    pop.classList.remove('post-post-line');
    pop.classList.add('post-line');
    popp.classList.remove('post-post-post-line');
    popp.classList.add('post-post-line');
}

function lineDownMoving(prpp, prp, pr, line, po, pop, popp) {
    prpp.classList.remove('pre-pre-pre-line');
    prpp.classList.add('pre-pre-line');
    prp.classList.remove('pre-pre-line');
    prp.classList.add('pre-line');
    pr.classList.remove('pre-line');
    pr.classList.add('main-line');
    line.classList.remove('main-line');
    line.classList.add('post-line');
    po.classList.remove('post-line');
    po.classList.add('post-post-line');
    pop.classList.remove('post-post-line');
    pop.classList.add('post-post-post-line');
    popp.classList.remove('post-post-post-line');
    popp.classList.add('pre-pre-pre-line');
}

export {
    autoHeight,
    lineUpMoving,
    lineDownMoving
}