$('document').ready(function () {  
  window.addEventListener("keydown", (e) => {
    if(e.key == 'Enter'){
      autoInsert();
    }
  });

  $('#auto-insert-submit').on('click', function() {
    autoInsert();
  });

  function autoInsert() {
    let productId = $('#productId').text();
    let url = $('#target-url').val();
    let storeName = $('#target-store-name').val();
    let model_kr = $('#model_kr').val();
  
    $.ajax({
      type: 'GET',
      url: '/raffles/' + productId + '/' + storeName,
      data: {},
      success: function (responseList) {
        let jsonResponseList = JSON.parse(responseList);
        if (jsonResponseList.length == 0) {
          insertRaffle();
        } else {
          let willInsert = confirm('[' + storeName + '] 이미 같은 매장의 드로우가 등록되어있습니다.\n중복해서 등록하시겠습니까?');
          if (willInsert) {
            insertRaffle();
          }
        }
      }
    });
  
    function insertRaffle() {
      $.ajax({
        type: 'POST',
        url: '/raffle-auto-insert',
        data: {
          productId: productId,
          url: url,
          storeName: storeName,
          model_kr: model_kr
        },
        success: function (raffleId) {
          let willGo = confirm('[' + storeName + '] 응모 카드가 등록되었습니다.\nFeedText 생성 페이지로 이동하시겠습니까?');
          if (willGo) {
            location.href = '/feed-text/' + raffleId;
          }
        },
        error: function (response) {
          alert(response.responseText);
        }
      });
    }
  }


  if ($('#productName').text() == '?') {
    $('#model_kr_tr').show();
  }
  $('#auto-insert-submit').on('click', autoInsert());
});

