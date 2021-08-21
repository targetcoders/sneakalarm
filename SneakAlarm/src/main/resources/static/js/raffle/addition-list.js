$('document').ready(function() {
	let keyFlag = 1;

	$('#raffle-settings__search-box').keyup(function(key){
	  if(keyFlag == 1){
	  	if(key.keyCode != 13){
	  		return;
	  	}

	  	keyFlag = 0;
	  	setTimeout(function(){
	  		var keyword=$('#raffle-settings__search-box').val();
	  		$('.raffle-setting-rows').empty();

	  		if(keyword == '') {
          $.ajax({
              url: '/raffle-settings',
              method: 'GET',
              success: function(responseData) {
                let jsonData = JSON.parse(responseData)
                appendRaffleSettingTableForRaffleInsert(jsonData);
              },
              error: function() {

              }
            });
	  		} else {
	  		  $.ajax({
	  			  url: '/raffle-settings/keyword/'+keyword,
	  	  		type: 'GET',
	  	  		success: function(responseData) {
	  	  			let jsonData = JSON.parse(responseData)
	  	  			appendRaffleSettingTableForRaffleInsert(jsonData);
	  	  		},
	  	  		error: function() {
	  	  			alert('error: searchError!');
	  	  		}
	  	  	});
	  		}
	  		keyFlag = 1;
	  	},100);
	  }
	});
});

function appendRaffleSettingTableForRaffleInsert(jsonData){
  let splitPath = document.location.href.split('=');
  let productId = splitPath[splitPath.length-1];

  for(let i in jsonData){
    console.log(jsonData[i]);
    let dateTime = getDateTimeString(jsonData[i].insertDateTime.date, jsonData[i].insertDateTime.time);
    let raffleSettingTableRow = new RaffleSettingTableRow(i, jsonData[i].id, jsonData[i].raffleSettingName, dateTime);
    $('.raffle-setting-rows').append(raffleSettingTableRow.getTableRowForRaffleInsert(productId));
  }
}
