$('document').ready(function() {
  addDrawsByActiveProductList();
  setHide();
  setPopup();
  setScrollMoving(new ScrollController());
  $('.navbar__search-icon').hide();
});

function setHide(){
  if($('#activeDraws-unregistered').length == 0){
    $('.product-unregistered').hide();
  }
}

function setAds(){
  if($(window).width() < 768){
    $('.popAds1').show();
    $('.popAds2').show();
  } else {
    $('.popAds1').hide();
    $('.popAds2').hide();
  }
}
function setPopup(){
  $('#category-open-btn').on('click',function(){
    $('#category-list-popup').fadeIn(200);
    $('.navbar-top--mobile__container').css('background-color','rgba(255,255,255,0)');
    $('.navbar-top--pc__container').css('background-color','rgba(255,255,255,0)');
    $('.fixed-bottom').hide();
    $('.fixed-bottom').css('z-index',1028);
    setAds();
  });
  $('#category-close-btn, .popBg').on('click',function(){
    $('.navbar-top--mobile__container').css('background-color','rgba(0,0,0,0)');
    $('.navbar-top--pc__container').css('background-color','rgba(255,255,255,0.9)');
    closeCategoryPopUp();
  });
}
function closeCategoryPopUp(){
  $('.fixed-bottom').show();
  $('#category-list-popup').fadeOut(200);
}
function setScrollMoving(scrollController){
  $('#raffle-category-korea').on('click', function(){
    scrollController.scrollTo('#activeDraws-korea');
    closeCategoryPopUp();
  });
  $('#raffle-category-direct').on('click', function(){
    scrollController.scrollTo('#activeDraws-direct');
    closeCategoryPopUp();
  });
  $('#raffle-category-agent').on('click', function(){
    scrollController.scrollTo('#activeDraws-agent');
    closeCategoryPopUp();
  });
}

function addDrawsByActiveProductList() {
  return new Promise(function(resolve){
    $.ajax({
      url: '/now/productList',
      type: 'get',
      data: {},
      success: function(activeProductList){
        for(let i in activeProductList){
          addDraws(createAdataToAddDraws(activeProductList[i]));
        }
      }
    });
  });

  function createAdataToAddDraws(activeProduct) {
    let id = activeProduct.id;
    let imgSrc_home = activeProduct.imgSrc_home;
    let deliveryTypes = activeProduct.deliveryTypes;
    let model_kr = activeProduct.model_kr;
    let listDeliveryType = deliveryTypes.split(',');
    return {id, listDeliveryType, imgSrc_home, model_kr};
  }
  
  function addDraws(aDataToAddDraws) {
    appendProructContainerForDraw(aDataToAddDraws);
    appendDrawsToProductContainers(aDataToAddDraws.listDeliveryType);
    
    function appendProructContainerForDraw(aDataToAddDraws) {
      for(let i in aDataToAddDraws.listDeliveryType) {
        let deliveryType = aDataToAddDraws.listDeliveryType[i];
        let id = aDataToAddDraws.id;
        let imgSrc_home = aDataToAddDraws.imgSrc_home;
        let model_kr = aDataToAddDraws.model_kr;

        if (model_kr == '?') {
          if($('#todayUnregisteredDraw-'+id).length > 0) continue;
          $('#activeDraws-unregistered').append('<div id="todayUnregisteredDraw-'+id+'" class="todayDrawContainer"><a href=/product-detail?id='+id+'><img id="todayProductImg-'+id+'" class="todayProductImg" src="'+imgSrc_home+'"></a></div>');
          $('#todayUnregisteredDraw-'+id).append('<div class="todayUnregisteredDrawContent-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
          continue;
        }

        if (deliveryType == '직배') {
          if($('#todayDirectDraw-'+id).length > 0) continue;
          $('#activeDraws-direct').append('<div id="todayDirectDraw-'+id+'" class="todayDrawContainer"><a href=/product-detail?id='+id+'><img id="todayProductImg-'+id+'" class="todayProductImg" src="'+imgSrc_home+'"></a></div>')
          $('#todayDirectDraw-'+id).append('<div class="todayDraw-model_kr">'+model_kr+'</div>')
          $('#todayDirectDraw-'+id).append('<div class="todayDrawContentDirect-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
        } else if (deliveryType == '배대지') {
          if($('#todayAgentDraw-'+id).length > 0) continue;
          $('#activeDraws-agent').append('<div id="todayAgentDraw-'+id+'" class="todayDrawContainer"><a href=/product-detail?id='+id+'><img id="todayProductImg-'+id+'" class="todayProductImg" src="'+imgSrc_home+'"></a></div>')
          $('#todayAgentDraw-'+id).append('<div class="todayDraw-model_kr">'+model_kr+'</div>')
          $('#todayAgentDraw-'+id).append('<div class="todayDrawContentAgent-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
        } else if (deliveryType == '방문수령' || deliveryType == '택배배송') {
          if($('#todayKoreaDraw-'+id).length > 0)  continue;
          $('#activeDraws-korea').append('<div id="todayKoreaDraw-'+id+'" class="todayDrawContainer"><a href=/product-detail?id='+id+'><img id="todayProductImg-'+id+'" class="todayProductImg" src="'+imgSrc_home+'"></a></div>')
          $('#todayKoreaDraw-'+id).append('<div class="todayDraw-model_kr">'+model_kr+'</div>')
          $('#todayKoreaDraw-'+id).append('<div class="todayDrawContentKorea-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
        }
      } 
    }
  
    function appendDrawsToProductContainers(listDeliveryType){
      for(let i in listDeliveryType) {
        $.ajax({
          url: '/now/drawList',
          type: 'get',
          data: {
            deliveryType: listDeliveryType[i]
          },
          success: function(resultList) {
            for(let i in resultList){
              for(let j in resultList[i].todayDraws){
                let id = resultList[i].todayDraws[j].id;
                let delivery = resultList[i].todayDraws[j].delivery;
                let drawUrl = resultList[i].todayDraws[j].url;
                let raffleType = resultList[i].todayDraws[j].raffleType;
                let storeName = resultList[i].todayDraws[j].storeName;
                let endDate = resultList[i].todayDraws[j].endDate;
                let endTime = resultList[i].todayDraws[j].endTime;
                let startDate = resultList[i].todayDraws[j].startDate;
                let startTime = resultList[i].todayDraws[j].startTime;
                let status = resultList[i].todayDraws[j].status;
                let startWeek = resultList[i].todayDraws[j].startWeek;
                let endWeek = resultList[i].todayDraws[j].endWeek;
                let specialCase = resultList[i].todayDraws[j].specialCase;
                let model_kr = resultList[i].todayDraws[j].model_kr;
                let releasePrice = resultList[i].todayDraws[j].releasePrice;
      
                if($('#drawCard-'+id).length > 0) continue;
                if(releasePrice == '미등록 제품') {
                  if(status == 'active') {
                    if(delivery == '택배배송' || delivery == '방문수령' || delivery == '직배' || delivery == '배대지'){
                      if(raffleType == '응모'){
                        $('.todayUnregisteredDrawContent-'+resultList[i].productId)
                          .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div><span class="draw-model_kr">'+model_kr+'</span></div><div class="draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endWeek+'&nbsp;&nbsp;'+endTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                      } else {
                        $('.todayUnregisteredDrawContent-'+resultList[i].productId)
                        .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div><span class="draw-model_kr">'+model_kr+'</span></div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                      }
                    }
                  }
                  continue;
                }
      
                if(status == 'active') {
                  if(delivery == '택배배송' || delivery == '방문수령'){
                    if(raffleType == '응모'){
                      $('.todayDrawContentKorea-'+resultList[i].productId)
                        .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;">\
                        <div><span class="draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div>\
                        <div class="draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endWeek+'&nbsp;&nbsp;'+endTime+'</div>\
                        <div class="draw-specialcase">'+specialCase+'</div>\
                        </a></div>');
                      } else {
                        $('.todayDrawContentKorea-'+resultList[i].productId)
                        .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                      }
                  } else if (delivery == '직배') {
                    if(raffleType == '응모'){
                      $('.todayDrawContentDirect-'+resultList[i].productId)
                      .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endWeek+'&nbsp;&nbsp;'+endTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                    } else {
                      $('.todayDrawContentDirect-'+resultList[i].productId)
                      .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                    }
                  } else {
                    if(raffleType == '응모'){
                      $('.todayDrawContentAgent-'+resultList[i].productId)
                      .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endWeek+'&nbsp;&nbsp;'+endTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                    } else {
                      $('.todayDrawContentAgent-'+resultList[i].productId)
                      .append('<div class="todayDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                    }
                  }
                } else {
                  if(delivery == '택배배송' || delivery == '방문수령'){
                    if(raffleType == '응모'){
                      $('.todayDrawContentKorea-'+resultList[i].productId)
                      .append('<div class="todayReadyDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+storeName+'</span></div><div class="ready-draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="ready-draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endWeek+'&nbsp;&nbsp;'+endTime+'</div><div class="ready-draw-specialcase">'+specialCase+'</div></a></div>');
                    } else {
                      $('.todayDrawContentKorea-'+resultList[i].productId)
                      .append('<div class="todayReadyDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+storeName+'</span></div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                    }
                  } else if(delivery == '직배'){
                    if(raffleType == '응모'){
                      $('.todayDrawContentDirect-'+resultList[i].productId)
                      .append('<div class="todayReadyDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+storeName+'</span></div><div class="ready-draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="ready-draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endWeek+'&nbsp;&nbsp;'+endTime+'</div><div class="ready-draw-specialcase">'+specialCase+'</div></a></div>');
                    } else {
                      $('.todayDrawContentDirect-'+resultList[i].productId)
                      .append('<div class="todayReadyDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+storeName+'</span></div><div class="draw-end-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                    }
                  } else {
                   if(raffleType == '응모'){
                     $('.todayDrawContentAgent-'+resultList[i].productId)
                     .append('<div class="todayReadyDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+storeName+'</span></div><div class="ready-draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="ready-draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endWeek+'&nbsp;&nbsp;'+endTime+'</div><div class="ready-draw-specialcase">'+specialCase+'</div></a></div>');
                   } else {
                     $('.todayDrawContentAgent-'+resultList[i].productId)
                     .append('<div class="todayReadyDrawContent"><a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+storeName+'</span></div><div class="draw-end-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startWeek+'&nbsp;&nbsp;'+startTime+'</div><div class="draw-specialcase">'+specialCase+'</div></a></div>');
                   }
                 }
               }
             }
            }
          },
          error: function() {
      
          }
        });
      }
    }
  }
}


