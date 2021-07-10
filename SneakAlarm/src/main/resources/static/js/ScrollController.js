class ScrollController {
  constructor() { }

  scrollTo(elementSelector){
    const location = computeLocation(elementSelector);
    const intMoreUpHeight = heightForNavbar();
    window.scrollTo({top:location-intMoreUpHeight, behavior:'smooth'})

    function computeLocation(elementSelector){
      return document.querySelector(elementSelector).offsetTop;
    }
    function heightForNavbar(){
      const windowWidth = $(window).width();
      return (windowWidth < 768) ? heightForMobileNavbar() : heightForPcNavbar();

      function heightForMobileNavbar(){
        return document.querySelector('.navbar-top-mobile').offsetHeight;
      }
      function heightForPcNavbar(){
        return document.querySelector('.navbar-top-pc').offsetHeight;
      }
    }
  }
}