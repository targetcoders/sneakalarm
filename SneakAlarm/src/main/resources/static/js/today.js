function getActiveProductList(){
  return new Promise(function(resolve){
    $.ajax({
      url: '/today/activeProductList',
      type: 'get',
      data: {},
      success: function(activeProductList){
        for(let i in activeProductList){
          new Promise(function(resolve){
            let id = activeProductList[i].id;
            let imgSrc_home = activeProductList[i].imgSrc_home;
            let deliveryTypes = activeProductList[i].deliveryTypes;
            let model_kr = activeProductList[i].model_kr;
            let splitedType = deliveryTypes.split(',');
            let data = {id, splitedType, imgSrc_home, model_kr};
            resolve(data);
          })
          .then(function(data){
            let splitedType = data.splitedType;
            let id = data.id;
            let imgSrc_home = data.imgSrc_home;
            let model_kr = data.model_kr;
            for(let i in splitedType) {
                if (splitedType[i] == '직배') {
                  if($('#todayDirectDraw-'+id).length > 0) continue;
                  $('#activeDraws-direct')
                  .append('<div id="todayDirectDraw-'+id+'" class="todayDrawContainer d-flex flex-column justify-content-center align-items-center"><img id="todayProductImg-'+id+'" class="todayProductImg" src="'+imgSrc_home+'"></div>')
                  .append('<div class="todayDraw-model_kr">'+model_kr+'</div>')
                  .append('<div class="todayDrawContentDirect-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
                  getDrawsByDeliveryType(splitedType[i]);
                } else if (splitedType[i] == '배대지') {
                  if($('#todayAgentDraw-'+id).length > 0) continue;
                  $('#activeDraws-agent')
                  .append('<div id="todayAgentDraw-'+id+'" class="todayDrawContainer d-flex flex-column justify-content-center align-items-center"><img id="todayProductImg-'+id+'" class="todayProductImg" src="'+imgSrc_home+'"></div>')
                  .append('<div class="todayDraw-model_kr">'+model_kr+'</div>')
                  .append('<div class="todayDrawContentAgent-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
                  getDrawsByDeliveryType(splitedType[i]);
                } else if (splitedType[i] == '방문수령' || splitedType[i] == '택배배송') {
                  if($('#todayKoreaDraw-'+id).length > 0)  continue;
                  $('#activeDraws-korea')
                  .append('<div id="todayKoreaDraw-'+id+'" class="todayDrawContainer d-flex flex-column justify-content-center align-items-center"><img id="todayProductImg-'+id+'" class="todayProductImg" src="'+imgSrc_home+'"></div>')
                  .append('<div class="todayDraw-model_kr">'+model_kr+'</div>')
                  .append('<div class="todayDrawContentKorea-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
                  getDrawsByDeliveryType(splitedType[i]);
                }
            }
          });
        }
      }
    });
  });
}
function getDrawsByDeliveryType(deliveryType){
  $.ajax({
    url: '/today/drawList',
    type: 'get',
    data: {
      deliveryType: deliveryType
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

          if($('#drawCard-'+id).length > 0) continue;

          if(delivery == '택배배송' || delivery == '방문수령'){
            if(raffleType == '응모'){
              $('.todayDrawContentKorea-'+resultList[i].productId)
                .append('<a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div class="todayDrawContent"><div><span class="draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endTime+'</div></div></a>');
              } else {
                $('.todayDrawContentKorea-'+resultList[i].productId)
                .append('<a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div class="todayDrawContent"><div><span class="draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startTime+'</div></div></a>');
              }
          }
          else if(delivery == '직배'){
            if(raffleType == '응모'){
               $('.todayDrawContentDirect-'+resultList[i].productId)
               .append('<a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div class="todayDrawContent"><div><span class="draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endTime+'</div></div></a>');
              } else {
               $('.todayDrawContentDirect-'+resultList[i].productId)
               .append('<a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div class="todayDrawContent"><div><span class="draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startTime+'</div></div></a>');
              }
          }
          else {
            if(raffleType == '응모'){
                $('.todayDrawContentAgent-'+resultList[i].productId)
                .append('<a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div class="todayDrawContent"><div><span class="draw-raffle-type-raffle">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-end-datetime">종료: '+endDate+'&nbsp;&nbsp;'+endTime+'</div></div></a>');
              } else {
                $('.todayDrawContentAgent-'+resultList[i].productId)
                .append('<a id="drawCard-'+id+'" href="'+drawUrl+'" style="text-decoration: none;"><div class="todayDrawContent"><div><span class="draw-raffle-type-firstcome">'+raffleType+'</span>&nbsp;-&nbsp;'+storeName+'</div><div class="draw-start-datetime">시작: '+startDate+'&nbsp;&nbsp;'+startTime+'</div></div></a>');
              }
          }
        }
      }
    },
    error: function() {

    }
  });
}

$('document').ready(function() {
  getActiveProductList();
});
