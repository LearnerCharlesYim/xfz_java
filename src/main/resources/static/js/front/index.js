const BannerHandler = function () {
}

BannerHandler.prototype.initBanner = () => {
    const mySwiper = new Swiper('.swiper', {
        loop: true, // 循环模式选项
        autoplay: {
            delay: 3000,
            disableOnInteraction: false,
            pauseOnMouseEnter: true,
        },
        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        // 如果需要前进后退按钮
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    })
}

BannerHandler.prototype.run = function () {
    this.initBanner()
};

$(function () {
    const banner = new BannerHandler();
    banner.run();
});