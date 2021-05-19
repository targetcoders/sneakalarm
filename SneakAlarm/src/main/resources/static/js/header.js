function showNavBar(){
    var windowWidth = $(window).width();
    if(windowWidth < 768){
        $('#nav-bottom-mobile').show();
        $('#nav-bottom-mobile-div').show();
        $('#nav-bottom-mobile-div-btn-today').show();
        $('#nav-bottom-mobile-div-btn-news').show();
    }
    else {
        $('#nav-bottom-mobile').hide();
        $('#nav-bottom-mobile-div').hide();
        $('#nav-bottom-mobile-div-btn-today').hide();
        $('#nav-bottom-mobile-div-btn-news').hide();
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
  if (getScrollTop() > 50) {
    $('#top-navbar').css({'box-shadow': '0px 0px 10px 1.5px rgba(0,0,0,0.1)'});
  } else {
    $('#top-navbar').css({'box-shadow': '0px 0px 0px 0px rgba(0,0,0,0)'});
  }
  console.log(getScrollTop());
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
