function showNavBar(){
    var windowWidth = $(window).width();
    if(windowWidth < 768){
        $('.navbar-bottom--mobile').show();

        $('.navbar-top-mobile').show();
        $('.navbar-top-pc').hide();
    }
    else {
        $('.navbar-bottom--mobile').hide();

        $('.navbar-top-mobile').hide();
        $('.navbar-top-pc').show();
    }
}

function toggleSearchBar(flag) {
  if(flag == true){
    $('#searchBar').show();
    $('#searchBar').focus();
  }
  else{
    $('#searchBar').hide();
    $('#searchBar').blur();
  }
}

function setShadowboxNavbar(){
  let windowWidth = $(window).width();
  if (getScrollTop() > 50) {
    $('.navbar-top--pc__container').css({'box-shadow': '0px 10px 10px -10px rgba(0,0,0,0.1)'});
    if(windowWidth < 768)
      $('.clock--mobile').hide();
  } else {
    $('.navbar-top--pc__container').css({'box-shadow': '0px 0px 0px 0px rgba(0,0,0,0)'});
    if(windowWidth < 768)
      $('.clock--mobile').show();
  }
}

// 현재 스크롤한 높이를 구하는 함수
function getScrollTop() {
	return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;
}
// 문서의 높이를 구하는 함수
function getDocumentHeight() {
	const body = document.body;
	const html = document.documentElement;

	return Math.max(
		body.scrollHeight, body.offsetHeight,
		html.clientHeight, html.scrollHeight, html.offsetHeight
	);
}

function moveToNowPage() {
  location.replace("/now");
  $('.navbar-top__menu').css("#585858");
}

function moveToLaunchPage() {
  location.replace("/");
  $('.navbar-top__menu').css("#585858");
}
