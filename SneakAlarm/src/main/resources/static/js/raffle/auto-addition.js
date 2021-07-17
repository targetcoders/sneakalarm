$('document').ready(function(){
  $('#auto-insert-submit').on('click',function(){
    let productId = $('#productId').text();
    let url = $('#target-url').val();
    let storeName = $('#target-store-name').val();
    $.ajax({
      type: 'POST',
      url: '/raffle-auto-insert/'+productId,
      data: {
        url: url,
        storeName: storeName
      },
      success: function(){
        alert('['+storeName+'] 응모 카드가 등록되었습니다.');
      },
      error: function(errorMessage){
        alert('응모 카드 등록에 실패하였습니다.\n'+errorMessage);
      }
    });
  });
});