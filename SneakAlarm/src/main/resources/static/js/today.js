
function getActiveProductList(){
  return new Promise(function(resolve){
    $.ajax({
        url: '/today/activeProductList',
        type: 'get',
        data: {},
        success: function(activeProductList){
          for(let i in activeProductList){
            let id = activeProductList[i].id;
            let imgSrc_home = activeProductList[i].imgSrc_home;
            let deliveryTypes = activeProductList[i].deliveryTypes;
            console.log('deliveryTypes: '+deliveryTypes);
            let splitedType = deliveryTypes.split(',');


            for(let i in splitedType) {
              console.log('splitedType: '+splitedType[i]);
              if (splitedType[i] == '직배') {
                if($('#todayDirectDraw-'+id).length > 0) continue;
                $('#activeDraws-direct')
                .append('<div id="todayDirectDraw-'+id+'" class="todayDrawContainer d-flex flex-column justify-content-center align-items-center"><img id="todayProductImg-'+id+'" class="todayProductImg" src='+imgSrc_home+'></div>')
                .append('<div class="todayDrawContentDirect-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
              }
              else if (splitedType[i] == '배대지') {
                if($('#todayAgentDraw-'+id).length > 0) continue;
                $('#activeDraws-agent')
                .append('<div id="todayAgentDraw-'+id+'" class="todayDrawContainer d-flex flex-column justify-content-center align-items-center"><img id="todayProductImg-'+id+'" class="todayProductImg" src='+imgSrc_home+'></div>')
                .append('<div class="todayDrawContentAgent-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
              }
              else if (splitedType[i] == '방문수령' || splitedType[i] == '택배배송') {
                console.log($('#todayKoreaDraw-'+id).length);
                if($('#todayKoreaDraw-'+id).length > 0) continue;

                $('#activeDraws-korea')
                .append('<div id="todayKoreaDraw-'+id+'" class="todayDrawContainer d-flex flex-column justify-content-center align-items-center"><img id="todayProductImg-'+id+'" class="todayProductImg" src='+imgSrc_home+'></div>')
                .append('<div class="todayDrawContentKorea-'+id+' todayDrawContentBox d-flex flex-wrap justify-content-center"></div>');
              }
            }
            resolve(deliveryTypes);

            console.log(id);
            console.log(imgSrc_home);
            console.log(deliveryTypes);
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
      		    console.log(resultList[i]);
      		    for(let j in resultList[i].todayDraws){
      		      let delivery = resultList[i].todayDraws[j].delivery;
      		      console.log('getDrawsByDeliveryType-delivery['+i+'] = '+delivery);

      		      if(delivery == '택배배송' || delivery == '방문수령'){
      		        $('.todayDrawContentKorea-'+resultList[i].productId)
                  .append('<a href="'+resultList[i].url+'" style="text-decoration: none;"><div class="todayDrawContent"><div>'+resultList[i].todayDraws[j].raffleType+'&nbsp;-&nbsp;'+resultList[i].todayDraws[j].storeName+'</div><div>종료: '+resultList[i].todayDraws[j].endDate+'&nbsp;&nbsp;'+resultList[i].todayDraws[j].endTime+'</div></div></a>');
      		      }
      		      else if(delivery == '직배'){
                  $('.todayDrawContentDirect-'+resultList[i].productId)
                  .append('<a href="'+resultList[i].url+'" style="text-decoration: none;"><div class="todayDrawContent"><div>'+resultList[i].todayDraws[j].raffleType+'&nbsp;-&nbsp;'+resultList[i].todayDraws[j].storeName+'</div><div>종료: '+resultList[i].todayDraws[j].endDate+'&nbsp;&nbsp;'+resultList[i].todayDraws[j].endTime+'</div></div></a>');
      		      }
      		      else {
                  $('.todayDrawContentAgent-'+resultList[i].productId)
                  .append('<a href="'+resultList[i].url+'" style="text-decoration: none;"><div class="todayDrawContent"><div>'+resultList[i].todayDraws[j].raffleType+'&nbsp;-&nbsp;'+resultList[i].todayDraws[j].storeName+'</div><div>종료: '+resultList[i].todayDraws[j].endDate+'&nbsp;&nbsp;'+resultList[i].todayDraws[j].endTime+'</div></div></a>');
      		      }

      		    }
      		  }
      		},
      		error: function() {

      		}
    });
}

$('document').ready(function() {
  getActiveProductList()
  .then(getDrawsByDeliveryType);
});
