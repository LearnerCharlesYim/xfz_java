const Auth = function () {
    this.$maskWrapper = $(".mask-wrapper")
    this.$scrollWrapper = $(".scroll-wrapper")
    this.loginGroup = $('.signin-group')
    this.registerGroup = $('.signup-group')
    this.graphButton = $('.img-captcha')
}

Auth.prototype.dialogShow = function () {
    this.$maskWrapper.fadeIn("fast")
}

Auth.prototype.dialogHidden = function () {
    this.$maskWrapper.fadeOut("fast")
    this.restForm()
}

Auth.prototype.listenDialogEvent = function () {
    let $login = $('.login-btn');
    let $register = $('.register-btn');
    let $close = $('.close-btn');

    $login.on('click', () => {
        this.dialogShow()
        this.$scrollWrapper.css({"left": '0'});
    })
    $register.on('click', () => {
        this.dialogShow()
        this.$scrollWrapper.css({"left": '-400px'});
        this.createGraphCaptcha()
    })

    $close.on('click', () => {
        this.dialogHidden()
    })

}

Auth.prototype.listenDialogSwitchEvent = function () {
    let $switch = $(".switch");
    $switch.on('click', () => {
        let currentLeft = this.$scrollWrapper.css("left")
        currentLeft = parseInt(currentLeft)
        if (currentLeft < 0) {
            this.$scrollWrapper.animate({"left": '0'})
        } else {
            this.$scrollWrapper.animate({"left": "-400px"})
            this.createGraphCaptcha()
        }
    })
}

Auth.prototype.listenLoginEvent = function () {

    const telephoneInput = this.loginGroup.find('input[name="telephone"]')
    const passwordInput = this.loginGroup.find('input[name="password"]')
    const rememberMeInput = this.loginGroup.find('input[name="remember"]')

    const submitBtn = this.loginGroup.find('.submit-btn')
    submitBtn.on('click', (e) => {
        e.preventDefault()
        const telephone = telephoneInput.val()
        const password = passwordInput.val()
        const rememberMe = rememberMeInput.prop('checked') ? 1 : 0
        $.post('/login', {telephone, password, rememberMe}, res => {
            if (res.code === 200) {
                setTimeout(() => location.reload(), 1000)
                return messageBox.showSuccess('登录成功')
            }
            messageBox.show(res.message)
        })
    })
}

Auth.prototype.restForm = function () {
    this.loginGroup.find('form').get(0).reset()
    this.registerGroup.find('form').get(0).reset()
}

Auth.prototype.listenGraphCaptchaEvent = function () {
    this.graphButton.on('click', () => {
        this.createGraphCaptcha()
    })
}

Auth.prototype.createGraphCaptcha = function () {
    this.graphButton.prop('src', '/captcha/img?t=' + new Date().getTime())
}

Auth.prototype.listenRegisterEvent = function () {
    const telephoneInput = this.registerGroup.find('input[name="telephone"]')
    const usernameInput = this.registerGroup.find('input[name="username"]')
    const imgCaptchaInput = this.registerGroup.find('input[name="img-captcha"]')
    const password1Input = this.registerGroup.find('input[name="password1"]')
    const password2Input = this.registerGroup.find('input[name="password2"]')

    const submitBtn = this.registerGroup.find('.submit-btn')
    submitBtn.on('click', (e) => {
        e.preventDefault()
        const telephone = telephoneInput.val()
        const username = usernameInput.val()
        const imgCaptcha = imgCaptchaInput.val()
        const password = password1Input.val()
        const repeatPassword = password2Input.val()
        const captchaUuid = $.cookie('captcha')

        $.post('/register', {telephone, username, imgCaptcha, password, repeatPassword, captchaUuid}, res => {
            if (res.code === 200) {
                messageBox.showSuccess("注册成功")
                this.restForm()
                this.$scrollWrapper.css({"left": '0'});
            } else {
                messageBox.show(res.message)
            }
        })
    })
}

Auth.prototype.run = function () {
    this.listenDialogEvent()
    this.listenDialogSwitchEvent()
    this.listenLoginEvent()
    this.listenGraphCaptchaEvent()
    this.listenRegisterEvent()
}

$(function () {
    const auth = new Auth()
    auth.run()
})