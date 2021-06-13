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

  getTableRowForRaffleInsert(productId) {
    return '<tr><td scope="row">'+this.seq+'</td><td scope="row"><a href="/raffles/addition/'+this.id+'?productId='+productId+'">'+this.name+'</a></td><td scope="row">'+this.insertDateTime+'</td></tr>';
  }


}

class RaffleSetting {

  constructor(id, raffleSettingName, storeName, country, raffleType, delivery, payType, specialCase, content, releasePrice, imgSrc, insertDateTime){
    this.id = id;
    this.raffleSettingName = raffleSettingName;
    this.storeName = storeName;
    this.country = country;
    this.raffleType = raffleType;
    this.delivery = delivery;
    this.payType = payType;
    this.specialCase = specialCase;
    this.content = content;
    this.releasePrice = releasePrice;
    this.imgSrc = imgSrc;
    this.insertDateTime = insertDateTime;
  }

  getRaffleSettingDetail(){
    let divTag = $('<div class="raffle-setting-detail"></div>');
    let tableTag = $('<table class="table setting-detail__table"></table>');
    tableTag.append('<tr>'+this.getDivId()+'</tr>');
    tableTag.append('<tr>'+this.getDivRaffleSettingName()+'</tr>');
    tableTag.append('<tr>'+this.getDivStoreName()+'</tr>');
    tableTag.append('<tr>'+this.getDivCountry()+'</tr>');
    tableTag.append('<tr>'+this.getDivRaffleType()+'</tr>');
    tableTag.append('<tr>'+this.getDivDelivery()+'</tr>');
    tableTag.append('<tr>'+this.getDivPayType()+'</tr>');
    tableTag.append('<tr>'+this.getDivSpecialCase()+'</tr>');
    tableTag.append('<tr>'+this.getDivContent()+'</tr>');
    tableTag.append('<tr>'+this.getDivReleasePrice()+'</tr>');
    tableTag.append('<tr>'+this.getDivImgSrc()+'</tr>');
    tableTag.append('<tr>'+this.getDivInsertDateTime()+'</tr>');
    divTag.append(tableTag);
    return divTag;
  }

  getDivId(){
    return '<td><b>id</b></td><td>'+this.id+'</td>';
  }
  getDivRaffleSettingName(){
      return '<td><b>raffleSettingName</b></td><td>'+this.raffleSettingName+'</td>';
  }
  getDivStoreName(){
      return '<td><b>storeName</b></td><td>'+this.storeName+'</td>';
  }
  getDivCountry(){
        return '<td><b>country</b></td><td>'+this.country+'</td>';
  }
  getDivRaffleType(){
        return '<td><b>raffleType</b></td><td>'+this.raffleType+'</td>';
  }
  getDivDelivery(){
        return '<td><b>delivery</b></td><td>'+this.delivery+'</td>';
  }
  getDivPayType(){
        return '<td><b>payType</b></td><td>'+this.payType+'</td>';
  }
  getDivSpecialCase(){
        return '<td><b>specialCase</b></td><td>'+this.specialCase+'</td>';
  }
  getDivContent(){
    return '<td><b>content</b></td><td><pre>'+this.content+'</pre></td>';
  }
  getDivReleasePrice(){
        return '<td><b>releasePrice</b></td><td>'+this.releasePrice+'</td>';
  }
  getDivImgSrc(){
        return '<td><b>imgSrc</b></td><td><img src="'+this.imgSrc+'" style="width=70px;height:70px;"/></td>';
  }
  getDivInsertDateTime(){
        return '<td><b>insertDateTime</b></td><td>'+this.insertDateTime+'</td>';
  }
}