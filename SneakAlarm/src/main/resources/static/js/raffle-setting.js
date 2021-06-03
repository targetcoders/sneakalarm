$('document').ready(function() {
  $.ajax({
    url: '/raffle-settings',
    method: 'GET',
    success: function(responseData) {
      let jsonData = JSON.parse(responseData)
      for(let i in jsonData){
        console.log(jsonData[i]);
        let dateTime = getDateTimeString(jsonData[i].insertDateTime.date, jsonData[i].insertDateTime.time);
        $('.raffle-setting-rows').append('<tr><td scope="row">'+i+'</td><td scope="row"><a href="/raffle-settings/detail?id='+jsonData[i].id+'">'+jsonData[i].raffleSettingName+'</a></td><td scope="row">'+dateTime+'</td></tr>');
      }
    },
    error: function() {

    }
  });
});