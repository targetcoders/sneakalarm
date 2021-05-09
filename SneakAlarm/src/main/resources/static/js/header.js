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
