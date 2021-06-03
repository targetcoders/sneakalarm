$('document').ready(function() {
  $.ajax({
    url: '/raffle-settings',
    method: 'GET',
    success: function(responseData) {
      let jsonData = JSON.parse(responseData)
      for(let i in jsonData){
        console.log(jsonData[i]);
        let dateTime = getDateTimeString(jsonData[i].insertDateTime.date, jsonData[i].insertDateTime.time);
        let raffleSettingTableRow = new RaffleSettingTableRow(i, jsonData[i].id, jsonData[i].raffleSettingName, dateTime);
        $('.raffle-setting-rows').append(raffleSettingTableRow.getTableRow());
      }
    },
    error: function() {

    }
  });
});

class RaffleSettingTableRow {

  constructor(seq, id, name, insertDateTime){
    this.seq = seq;
    this.id = id;
    this.name = name;
    this.insertDateTime = insertDateTime;
  }

  getTableRow() {
    return '<tr><td scope="row">'+this.seq+'</td><td scope="row"><a href="/raffle-setting/detail/'+this.id+'">'+this.name+'</a></td><td scope="row">'+this.insertDateTime+'</td></tr>';
  }
}

class RaffleSetting {

  constructor(id, raffleSettingName, storeName, country, delivery, payType, specialCase, productPrice, imgSrc, startDateTime, endDateTime, insertDateTime){
    this.id = id;
    this.raffleSettingName = raffleSettingName;
    this.storeName = storeName;
    this.country = country;
    this.delivery = delivery;
    this.payType = payType;
    this.specialCase = specialCase;
    this.productPrice = productPrice;
    this.imgSrc = imgSrc;
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.insertDateTime = insertDateTime;
  }

  getRaffleSettingDetail(){
    let divTag = $('<div class="raffle-setting-detail"></div>');
    divTag.append(this.getDivId());
    divTag.append(this.getDivRaffleSettingName());
    divTag.append(this.getDivStoreName());
    divTag.append(this.getDivCountry());
    divTag.append(this.getDivDelivery());
    divTag.append(this.getDivPayType());
    divTag.append(this.getDivSpecialCase());
    divTag.append(this.getDivProductPrice());
    divTag.append(this.getDivImgSrc());
    divTag.append(this.getDivStartDateTime());
    divTag.append(this.getDivEndDateTime());
    divTag.append(this.getDivInsertDateTime());

    return divTag;
  }

  getDivId(){
    return '<div>id: '+this.id+'</div>';
  }
  getDivRaffleSettingName(){
      return '<div>raffleSettingName: '+this.raffleSettingName+'</div>';
  }
  getDivStoreName(){
      return '<div>storeName: '+this.storeName+'</div>';
  }
  getDivCountry(){
        return '<div>country: '+this.country+'</div>';
  }
  getDivDelivery(){
        return '<div>delivery: '+this.delivery+'</div>';
  }
  getDivPayType(){
        return '<div>payType: '+this.payType+'</div>';
  }
  getDivSpecialCase(){
        return '<div>specialCase: '+this.specialCase+'</div>';
  }
  getDivProductPrice(){
        return '<div>productPrice: '+this.productPrice+'</div>';
  }
  getDivImgSrc(){
        return '<div>imgSrc: '+this.imgSrc+'</div>';
  }
  getDivStartDateTime(){
        return '<div>startDateTime: '+this.startDateTime+'</div>';
  }
  getDivEndDateTime(){
        return '<div>endDateTime: '+this.endDateTime+'</div>';
  }
  getDivInsertDateTime(){
        return '<div>insertDateTime: '+this.insertDateTime+'</div>';
  }
}
