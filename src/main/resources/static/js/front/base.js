const BaseHandler = function () {
}

BaseHandler.prototype.listenAuthBoxHover = function () {
    const authBox = $('.auth-box')
    const userMoreBox = $('.user-more-box')
    authBox.hover(() => {
        userMoreBox.show()
    }, () => {
        userMoreBox.hide()
    })
}

BaseHandler.prototype.listenNavActive = function () {
    const currentUrl = location.pathname
    const navLis = $('.nav li');
    navLis.each((index, ele) => {
        const li = $(ele)
        const href = li.children('a').prop('href');
        if (href.includes(currentUrl)) {
            li.addClass('active')
            return false
        }
    })
}

BaseHandler.prototype.run = function () {
    this.listenAuthBoxHover()
    this.listenNavActive()
}

$(function () {
    const handler = new BaseHandler();
    handler.run()
})