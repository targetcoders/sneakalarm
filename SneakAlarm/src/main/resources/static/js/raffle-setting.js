$('document').ready(function() {
  $.ajax({
    url: '/raffle-settings',
    method: 'GET',
    success: function(responseData) {
      let jsonData = JSON.parse(responseData)
      for(let i in jsonData){
        console.log(jsonData[i]);
        let dateTime = getDateTimeString(jsonData[i].insertDateTime.date, jsonData[i].insertDateTime.time);
        let raffleSetting = new RaffleSetting(i, jsonData[i].id, jsonData[i].raffleSettingName, dateTime);
        $('.raffle-setting-rows').append(raffleSetting.getRaffleSettingTableRow());
      }
    },
    error: function() {

    }
  });
});

class RaffleSetting {
  constructor(seq, id, name, insertDateTime){
    this.seq = seq;
    this.id = id;
    this.name = name;
    this.insertDateTime = insertDateTime;
  }

  getRaffleSettingTableRow() {
    return '<tr><td scope="row">'+this.seq+'</td><td scope="row"><a href="/raffle-settings/detail?id='+this.id+'">'+this.name+'</a></td><td scope="row">'+this.insertDateTime+'</td></tr>';
  }
}
