$('document').ready(function() {
  let splitPath = window.location.pathname.split('/');
  let id = splitPath[splitPath.length-1];

  $.ajax({
    method: 'GET',
    url: '/raffle-settings/'+id,
    success: function(responseData) {
      let jsonData = JSON.parse(responseData);
      let startDateTime,endDateTime, insertDateTime;
      if(jsonData.startDateTime != null)
        startDateTime = getDateTimeString(jsonData.startDateTime.date, jsonData.startDateTime.time);
      if(jsonData.endDateTime != null)
        endDateTime = getDateTimeString(jsonData.endDateTime.date, jsonData.endDateTime.time);
      if(jsonData.insertDateTime != null)
        insertDateTime = getDateTimeString(jsonData.insertDateTime.date, jsonData.insertDateTime.time);

      let raffleSetting = new RaffleSetting(jsonData.id, jsonData.raffleSettingName, jsonData.storeName, jsonData.country, jsonData.delivery, jsonData.payType, jsonData.specialCase, jsonData.productPrice, jsonData.imgSrc, startDateTime, endDateTime, insertDateTime);
      $('.raffle-setting-detail-contents').append(raffleSetting.getRaffleSettingDetail());
    }
  });
});