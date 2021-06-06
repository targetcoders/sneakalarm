$('document').ready(function() {
  let splitPath = window.location.pathname.split('/');
  let id = splitPath[splitPath.length-1];
    $('.delete-raffle-setting').click(
      function() {
        var confirm = window.confirm("삭제하시겠습니까?");
        if(confirm) {
          $.ajax({
            method: 'DELETE',
            url: '/raffle-settings/'+id,
            success: function(responseData) {
              alert("삭제되었습니다.");
              location.replace("/raffle-setting");
            }
          });
        }
      });
  $.ajax({
    method: 'GET',
    url: '/raffle-settings/'+id,
    success: function(responseData) {
      let jsonData = JSON.parse(responseData);
      let insertDateTime;
      if(jsonData.insertDateTime != null)
        insertDateTime = getDateTimeString(jsonData.insertDateTime.date, jsonData.insertDateTime.time);

      let raffleSetting = new RaffleSetting(jsonData.id, jsonData.raffleSettingName, jsonData.storeName, jsonData.country,
                                jsonData.raffleType, jsonData.delivery, jsonData.payType, jsonData.specialCase, jsonData.content,
                                jsonData.releasePrice, jsonData.imgSrc, insertDateTime);

      $('.raffle-setting-detail-contents').append(raffleSetting.getRaffleSettingDetail());
    }
  });
});