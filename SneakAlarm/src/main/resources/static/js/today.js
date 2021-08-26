function setPopup(){
  $('#category-open-btn').on('click',function(){
    $('.navbar__clock__date').css('color','white');
    $('.navbar__clock__week').css('color','white');
    $('.navbar__bogo').attr('src','image/SneakAlarm-white.png');
    $('#category-list-popup').fadeIn(200);
    $('.navbar-top--mobile__container').css('background-color','rgba(255,255,255,0)');
    $('.navbar-top--pc__container').css('background-color','rgba(255,255,255,0)');
    $('.fixed-bottom').hide();
    $('.fixed-bottom').css('z-index',1028);
  });
  $('#category-close-btn, .popBg').on('click',function(){
    $('.navbar-top--mobile__container').css('background-color','rgba(0,0,0,0)');
    $('.navbar-top--pc__container').css('background-color','rgba(255,255,255,0.9)');
    closeCategoryPopUp();
  });
}
function closeCategoryPopUp(){
  $('.navbar__clock__date').css('color','black');
  $('.navbar__clock__week').css('color','black');
  $('.navbar__bogo').attr('src','image/pc/SneakAlarm_white_870x180.png');
  $('.fixed-bottom').show();
  $('#category-list-popup').fadeOut(200);
}
function setScrollMoving(scrollController){
  $('#raffle-category-korea').on('click', function(){
    closeCategoryPopUp();
    scrollController.scrollTo('#activeDraws-korea');
  });
  $('#raffle-category-direct').on('click', function(){
    closeCategoryPopUp();
    scrollController.scrollTo('#activeDraws-direct');
  });
  $('#raffle-category-agent').on('click', function(){
    closeCategoryPopUp();
    scrollController.scrollTo('#activeDraws-agent');
  });
}
function addCookie(raffleId) {
  let myRaffleList = myRaffleCookies();
  myRaffleList.push(raffleId);
  setCookie('myRaffles', myRaffleList.join('/'), 30);
}
function setCookie(cookie_name, value, days) {
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + days);
  var cookie_value = escape(value) + ((days == null) ? '' : '; expires=' + exdate.toUTCString());
  document.cookie = cookie_name + '=' + cookie_value;
}
function myRaffleCookies() {
  let cookieList = document.cookie.split(';');
  for (i in cookieList) {
      let splitCookie = cookieList[i].split('=');
      if (splitCookie[0].trim() == 'myRaffles') {
        console.log(unescape(splitCookie[1]).split('/'));
        return splitCookie[1].split('/');
      }
  }
  return [];
}


