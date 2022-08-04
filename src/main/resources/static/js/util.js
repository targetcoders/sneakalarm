var getParam = function(key){
    var _parammap = {};
    document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
        function decode(s) {
            return decodeURIComponent(s.split("+").join(" "));
        }

        _parammap[decode(arguments[1])] = decode(arguments[2]);
    });

    return _parammap[key];
};

function getDateTimeString(date, time) {
  let year = date.year;
  let month = date.month;
  let day = date.day;
  let hour = time.hour;
  let minute = time.minute;

  month = (month<10) ? '0'+month:month;
  day = (day<10) ? '0'+day:day;
  hour = (hour<10) ? '0'+hour:hour;
  minute = (minute<10) ? '0'+minute:minute;
  return year+'/'+month+'/'+day+' '+hour+':'+minute;
}