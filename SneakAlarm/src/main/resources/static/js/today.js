$(document).ready(function(){
  (adsbygoogle = window.adsbygoogle || []).push({});
});

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
  $('#raffle-category-ready').on('click', function(){
    closeCategoryPopUp();
    scrollController.scrollTo('#readyDraws-korea');
  });
}
function setCookieSearchConditions() {
  let searchDrawConditionList = searchCookies("searchDrawConditions");
  if(searchDrawConditionList.length > 0 ){
    return;
  }
  searchDrawConditionList.push('ended');
  setCookie('searchDrawConditions', searchDrawConditionList.join('/'), 30);
}
function addDrawCondition(status){
  setCookie('searchDrawConditions', status, 30);
}
function addCookie(raffleId) {
  let myRaffleList = searchCookies("myRaffles");
  myRaffleList.push(raffleId);
  setCookie('myRaffles', myRaffleList.join('/'), 30);
}
function setCookie(cookie_name, value, days) {
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + days);
  var cookie_value = escape(value) + ((days == null) ? '' : '; expires=' + exdate.toUTCString());
  document.cookie = cookie_name + '=' + cookie_value;
}
function searchCookies(keyword) {
  let cookieList = document.cookie.split(';');
  for (i in cookieList) {
      let splitCookie = cookieList[i].split('=');
      if (splitCookie[0].trim() == keyword) {
        return splitCookie[1].split('/');
      }
  }
  return [];
}
function onClickCheckMyRaffles(raffleId) {
  let id = raffleId;
  let myRaffleList = searchCookies("myRaffles");
  if (myRaffleList.includes(id)) {
    let idx = myRaffleList.indexOf(id);
    myRaffleList.splice(idx, 1);
    setCookie('myRaffles', myRaffleList.join('/'), 30);
    $('#check-' + id).attr('src', '/image/icon/check_off.svg');
    $('#drawCard-' + id).children('div').css('opacity', '1');
  } else {
    addCookie(id);
    $('#check-' + id).attr('src', '/image/icon/check_on.svg');
    $('#drawCard-' + id).children('div').css('opacity', '0.5');
  }
}

function setCardsMaxHeight() {
  let todayDrawContainerList = document.getElementsByClassName('todayDrawContainer')
  for (let i in todayDrawContainerList) {
    let heightArr = [];
    $('#' + todayDrawContainerList[i].id + ' .todayDrawContent').each(function () {
      heightArr.push($(this).css('height'));
    });
    $('#' + todayDrawContainerList[i].id + ' .todayReadyDrawContent').each(function () {
      heightArr.push($(this).css('height'));
    });
    heightArr.sort(function(h1,h2){
      return Number(h1.substring(0,h1.length-2)) - Number(h2.substring(0,h2.length-2));
    });
    $('#' + todayDrawContainerList[i].id + ' .todayDrawContent').css('height', heightArr[heightArr.length - 1]);
    $('#' + todayDrawContainerList[i].id + ' .todayReadyDrawContent').css('height', heightArr[heightArr.length - 1]);
  }
}
