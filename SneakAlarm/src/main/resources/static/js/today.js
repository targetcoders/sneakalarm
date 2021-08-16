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

function addDrawsByActiveProductList() {
  return new Promise(function (resolve, reject) {
    $.ajax({
      url: '/now/draw-list/unregistered',
      type: 'get',
      data: {},
      success: function (unregisteredDrawList) {
        const unregisteredDrawListJson = JSON.parse(unregisteredDrawList);
        if(unregisteredDrawListJson.length==0){
          return;
        }
        $('.product-unregistered').show();
        for (let i in unregisteredDrawListJson) {
          const product = unregisteredDrawListJson[i].productVO;
          $('#activeDraws-unregistered').append('<div id="todayUnregisteredDraw-'+product.id+'" class="todayDrawContainer"><a href=/product-detail?id='+product.id+'><img id="todayProductImg-'+product.id+'" class="todayProductImg lozad" data-src="'+product.imgSrc_home+'" src="image/loading.png" alt="loading..."/></a></div>');
          $('#todayUnregisteredDraw-'+product.id).append('<div class="todayUnregisteredDrawContent-'+product.id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');

          const targetRaffleVOList = unregisteredDrawListJson[i].targetRaffleVOList;
          for (let i in targetRaffleVOList) {
            const raffle = targetRaffleVOList[i];
            if (raffle.status == 'active') {
              if(raffle.raffleType == '응모'){
                $('.todayUnregisteredDrawContent-'+raffle.productId)
                  .append('<div class="todayDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div class="div-draw-model_kr"><span class="draw-model_kr">'+raffle.model_kr+'</span></div><div><span class="draw-raffle-type-raffle">'+raffle.raffleType+'</span>&nbsp;-&nbsp;'+raffle.storeName+'</div><div class="draw-end-datetime">종료: '+raffle.endDate+'&nbsp;&nbsp;'+raffle.endWeek+'&nbsp;&nbsp;'+raffle.endTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              } else {
                $('.todayUnregisteredDrawContent-'+raffle.productId)
                .append('<div class="todayDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div class="div-draw-model_kr"><span class="draw-model_kr">'+raffle.model_kr+'</span></div><div><span class="draw-raffle-type-firstcome">'+raffle.raffleType+'</span>&nbsp;-&nbsp;'+raffle.storeName+'</div><div class="draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              }
            } else {
              if(raffleType == '응모'){
                $('.todayUnregisteredDrawContent-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div class="div-draw-model_kr"><span class="draw-model_kr">'+raffle.model_kr+'</span></div><div><span class="ready-draw-raffle-type-raffle">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="ready-draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="ready-draw-end-datetime">종료: '+raffle.endDate+'&nbsp;&nbsp;'+raffle.endWeek+'&nbsp;&nbsp;'+raffle.endTime+'</div><div class="ready-draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              } else {
                $('.todayUnregisteredDrawContent-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div class="div-draw-model_kr"><span class="draw-model_kr">'+raffle.model_kr+'</span></div><div><span class="ready-draw-raffle-type-firstcome">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              }
            }
          }
        }
      }
    });

    $.ajax({
      url: '/now/draw-list/korea',
      type: 'get',
      data: {},
      success: function (koreaDrawList) {
        const koreaDrawListJson = JSON.parse(koreaDrawList);
        for (let i in koreaDrawListJson) {
          const product = koreaDrawListJson[i].productVO;
          if(product.model_kr == '?') continue;

          $('#activeDraws-korea').append('<div id="todayKoreaDraw-' + product.id + '" class="todayDrawContainer"><a href=/product-detail?id=' + product.id + '><img id="todayProductImg-' + product.id + '" class="todayProductImg lozad" data-src="'+product.imgSrc_home+'" src="image/loading.png" alt="'+product.model_kr+'"/></a></div>');
          $('#todayKoreaDraw-' + product.id).append('<div class="todayDraw-model_kr">' + product.model_kr + '</div>');
          $('#todayKoreaDraw-' + product.id).append('<div class="todayDrawContentKorea-' + product.id + ' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');

          const targetRaffleVOList = koreaDrawListJson[i].targetRaffleVOList;
          for (let i in targetRaffleVOList) {
            const raffle = targetRaffleVOList[i];
            if (raffle.status == 'active') {
              if (raffle.raffleType == '응모') {
                $('.todayDrawContentKorea-' + raffle.productId)
                  .append('<div class="todayDrawContent"><a id="drawCard-' + raffle.id + '" href="' + raffle.url + '" style="text-decoration: none;">\
                <div><span class="draw-raffle-type-raffle">'+ raffle.raffleType + '</span>&nbsp;-&nbsp;' + raffle.storeName + '</div>\
                <div class="draw-end-datetime">종료: '+ raffle.endDate + '&nbsp;&nbsp;' + raffle.endWeek + '&nbsp;&nbsp;' + raffle.endTime + '</div>\
                <div class="draw-specialcase">'+ raffle.specialCase + '</div>\
                </a></div>');
              } else {
                $('.todayDrawContentKorea-' + raffle.productId)
                  .append('<div class="todayDrawContent"><a id="drawCard-' + raffle.id + '" href="' + raffle.url + '" style="text-decoration: none;"><div><span class="draw-raffle-type-firstcome">' + raffle.raffleType + '</span>&nbsp;-&nbsp;' + raffle.storeName + '</div><div class="draw-start-datetime">시작: ' + raffle.startDate + '&nbsp;&nbsp;' + raffle.startWeek + '&nbsp;&nbsp;' + raffle.startTime + '</div><div class="draw-specialcase">' + raffle.specialCase + '</div></a></div>');
              }
            } else {
              if(raffle.raffleType == '응모'){
                $('.todayDrawContentKorea-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-raffle">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="ready-draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="ready-draw-end-datetime">종료: '+raffle.endDate+'&nbsp;&nbsp;'+raffle.endWeek+'&nbsp;&nbsp;'+raffle.endTime+'</div><div class="ready-draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              } else {
                $('.todayDrawContentKorea-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-firstcome">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              }
            }
          }
        }
      }
    });
    $.ajax({
      url: '/now/draw-list/direct',
      type: 'get',
      data: {},
      success: function (directDrawList) {
        const directDrawListJson = JSON.parse(directDrawList);
        for (let i in directDrawListJson) {
          const product = directDrawListJson[i].productVO;
          if(product.model_kr == '?') continue;

          $('#activeDraws-direct').append('<div id="todayDirectDraw-'+product.id+'" class="todayDrawContainer"><a href=/product-detail?id='+product.id+'><img id="todayProductImg-'+product.id+'" class="todayProductImg lozad" data-src="'+product.imgSrc_home+'" src="image/loading.png" alt="'+product.model_kr+'"/></a></div>');
          $('#todayDirectDraw-'+product.id).append('<div class="todayDraw-model_kr">'+product.model_kr+'</div>')
          $('#todayDirectDraw-'+product.id).append('<div class="todayDrawContentDirect-'+product.id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');

          const targetRaffleVOList = directDrawListJson[i].targetRaffleVOList;
          for (let i in targetRaffleVOList) {
            const raffle = targetRaffleVOList[i];
            if (raffle.status == 'active') {
              if(raffle.raffleType == '응모'){
                $('.todayDrawContentDirect-'+raffle.productId)
                .append('<div class="todayDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div><span class="draw-raffle-type-raffle">'+raffle.raffleType+'</span>&nbsp;-&nbsp;'+raffle.storeName+'</div><div class="draw-end-datetime">종료: '+raffle.endDate+'&nbsp;&nbsp;'+raffle.endWeek+'&nbsp;&nbsp;'+raffle.endTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              } else {
                $('.todayDrawContentDirect-'+raffle.productId)
                .append('<div class="todayDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.drawUrl+'" style="text-decoration: none;"><div><span class="draw-raffle-type-firstcome">'+raffle.raffleType+'</span>&nbsp;-&nbsp;'+raffle.storeName+'</div><div class="draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              }
            } else {
              if(raffle.raffleType == '응모'){
                $('.todayDrawContentDirect-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-raffle">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="ready-draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="ready-draw-end-datetime">종료: '+raffle.endDate+'&nbsp;&nbsp;'+raffle.endWeek+'&nbsp;&nbsp;'+raffle.endTime+'</div><div class="ready-draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              } else {
                $('.todayDrawContentDirect-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-firstcome">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="draw-end-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              }
            }
          }
        }
      }
    });

    $.ajax({
      url: '/now/draw-list/agent',
      type: 'get',
      data: {},
      success: function (agentDrawList) {
        const agentDrawListJson = JSON.parse(agentDrawList);
        for (let i in agentDrawListJson) {
          const product = agentDrawListJson[i].productVO;
          if(product.model_kr == '?') continue;

          $('#activeDraws-agent').append('<div id="todayAgentDraw-'+product.id+'" class="todayDrawContainer"><a href=/product-detail?id='+product.id+'><img id="todayProductImg-'+product.id+'" class="todayProductImg lozad" data-src="'+product.imgSrc_home+'" src="image/loading.png" alt="'+product.model_kr+'"/></a></div>');
          $('#todayAgentDraw-'+product.id).append('<div class="todayDraw-model_kr">'+product.model_kr+'</div>')
          $('#todayAgentDraw-'+product.id).append('<div class="todayDrawContentAgent-'+product.id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');

          const targetRaffleVOList = agentDrawListJson[i].targetRaffleVOList;
          for (let i in targetRaffleVOList) {
            const raffle = targetRaffleVOList[i];
            if (raffle.status == 'active') {
              if(raffle.raffleType == '응모'){
                $('.todayDrawContentAgent-'+raffle.productId)
                .append('<div class="todayDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div><span class="draw-raffle-type-raffle">'+raffle.raffleType+'</span>&nbsp;-&nbsp;'+raffle.storeName+'</div><div class="draw-end-datetime">종료: '+raffle.endDate+'&nbsp;&nbsp;'+raffle.endWeek+'&nbsp;&nbsp;'+raffle.endTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              } else {
                $('.todayDrawContentAgent-'+raffle.productId)
                .append('<div class="todayDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.url+'" style="text-decoration: none;"><div><span class="draw-raffle-type-firstcome">'+raffle.raffleType+'</span>&nbsp;-&nbsp;'+raffle.storeName+'</div><div class="draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              }
            } else {
              if(raffle.raffleType == '응모'){
                $('.todayDrawContentAgent-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-raffle">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="ready-draw-start-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="ready-draw-end-datetime">종료: '+raffle.endDate+'&nbsp;&nbsp;'+raffle.endWeek+'&nbsp;&nbsp;'+raffle.endTime+'</div><div class="ready-draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              } else {
                $('.todayDrawContentAgent-'+raffle.productId)
                .append('<div class="todayReadyDrawContent"><a id="drawCard-'+raffle.id+'" href="'+raffle.drawUrl+'" style="text-decoration: none;"><div><span class="ready-draw-raffle-type-firstcome">'+raffle.raffleType+'</span>&nbsp;<span class="ready-draw-storeName">-&nbsp;'+raffle.storeName+'</span></div><div class="draw-end-datetime">시작: '+raffle.startDate+'&nbsp;&nbsp;'+raffle.startWeek+'&nbsp;&nbsp;'+raffle.startTime+'</div><div class="draw-specialcase">'+raffle.specialCase+'</div></a></div>');
              }
            }
          }
        }
      }
    });
    resolve(true);
  });
}


