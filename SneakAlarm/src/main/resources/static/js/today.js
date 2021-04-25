$('document').ready(function() {
getActiveProductIdList();
getDrawsByDeliveryType('택배배송,방문수령');

});

function getActiveProductIdList(){
  $.ajax({
    url: '/today/activeProductList',
    type: 'get',
    data: {},
    success: function(activeProductList){
      for(let i in activeProductList){
        let id = activeProductList[i].productId;
        let imgSrc = activeProductList[i].imgSrc;
        let deliveryTypes = activeProductList[i].deliveryTypes;
        console.log(id);
        console.log(imgSrc);
        console.log(deliveryTypes);
      }
    }
  });
}

function getDrawsByDeliveryType(deliveryType){
    $.ajax({
      url: '/today/drawList',
      		type: 'get',
      		data: {
      		  deliveryType: deliveryType
      		},
      		success: function(res) {
      		  console.log(res.productId);
      		  console.log(res.imgSrc);
      		  console.log(res.todayDraws)
      		},
      		error: function() {

      		}
    });
}