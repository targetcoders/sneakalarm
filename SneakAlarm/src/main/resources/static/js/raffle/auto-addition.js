$('document').ready(function(){
  if($('#productName').text() == '?'){
    $('#model_kr_tr').show();
  }
  $('#auto-insert-submit').on('click',function(){
    let productId = $('#productId').text();
    let url = $('#target-url').val();
    let storeName = $('#target-store-name').val();
    let model_kr = $('#model_kr').val();
    $.ajax({
      type: 'POST',
      url: '/raffle-auto-insert',
      data: {
        productId: productId,
        url: url,
        storeName: storeName,
        model_kr: model_kr
      },
      success: function(raffleId){
        let willGo = confirm('['+storeName+'] 응모 카드가 등록되었습니다.\nFeedText 생성 페이지로 이동하시겠습니까?');
        if(willGo){
          location.href='/feed-text/'+raffleId;
        }
      },
      error: function(response){
        alert(response.responseText);
      }
    });
  });
});