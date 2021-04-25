
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
            if(deliveryTypes.indexOf('택배배송') != -1 || deliveryTypes.indexOf('방문수령') != -1){
              $('#activeDraws-korea').append('<div id="todayKoreaDraw-'+id+'" class="todayDrawContainer d-flex flex-column justify-content-center align-items-center"><h1>국내</h1><img id="todayProductImg-'+id+'" class="todayProductImg" src='+imgSrc_home+'></div>')
              .append('<div id="todayDrawContent-'+id+'" class="d-flex flex-wrap justify-content-center"></div>');
            }
            console.log(id);
            console.log(imgSrc_home);
            console.log(deliveryTypes);
            resolve(deliveryTypes);
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
      		      $('#todayDrawContent-'+resultList[i].productId)
      		      .append('<div class="todayDrawContent"><div>'+resultList[i].todayDraws[j].raffleType+'&nbsp;-&nbsp;'+resultList[i].todayDraws[j].storeName+'</div><div>종료: '+resultList[i].todayDraws[j].endDate+'&nbsp;&nbsp;'+resultList[i].todayDraws[j].endTime+'</div></div></div>');
      		    }
        		  //resultList[i].todayDraws[0]
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
