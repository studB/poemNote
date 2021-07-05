function enterMainPage() {
    this.$router.push('/main-page');
}

function enterWritePage() {
    this.$router.push('/write-page');
}

function enterContentPage(category) {
    if(this.$router.history.current.path === `/content-page/${category}` ) return;
    this.$router.push(`/content-page/${category}`);
}

function enterPublishPage(category, textId) {
    this.$router.push(`/publish-page/${category}/${textId}`);
    
}

export {
    enterMainPage,
    enterWritePage,
    enterContentPage,
    enterPublishPage
}