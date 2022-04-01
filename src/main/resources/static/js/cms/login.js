const LoginHandler = function () {
    this.$form = $('#login-form')
    this.$submit = $('#submit-btn')
}

LoginHandler.prototype.listenSubmitEvent = function () {
    this.$submit.on('click', e => {
        e.preventDefault()
        let telephone = this.$form.find('input[name="telephone"]').val();
        let password = this.$form.find('input[name="password"]').val();
        let rememberMe = this.$form.find('input[name="remember-me"]').prop('checked') === true ? 1 : 0
        $.post('/login', {telephone, password, rememberMe}, res => {
            if (res.code === 200) return alert("ok")
            alert(res.message)
        })
    })
}

LoginHandler.prototype.run = function () {
    this.listenSubmitEvent()
}

$(function () {
    const handler = new LoginHandler();
    handler.run()
});