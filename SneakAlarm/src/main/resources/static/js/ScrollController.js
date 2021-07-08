class ScrollController {
  constructor() { }

  scrollTo(elementId){
    const location = computeLocation(elementId);
    const intMoreUpHeight = heightForNavbar();
    window.scrollTo({top:location-intMoreUpHeight, behavior:'smooth'})

    function computeLocation(elementId){
      return document.querySelector('#'+elementId).offsetTop;
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