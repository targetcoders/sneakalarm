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
      let raffleSetting = new RaffleSetting(jsonData);

      $('.raffle-setting-detail-contents').append(raffleSetting.getRaffleSettingDetail());
    }
  });
});