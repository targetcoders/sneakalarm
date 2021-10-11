
$('document').ready(function() {
	var getFlag = 1;
	var passFlag = 1;
	var keyFlag = 1;
	var searchBarShowingFlag = false;
	var page = 0;

	$('#navbar-bottom--mobile__menu--launch').attr('src','/image/icon/navbar/btmnav_icn_launch_ver1_on.svg');

	$('.navbar__search-icon').click(
    function(){
      searchBarShowingFlag = (searchBarShowingFlag) ? false : true;
	    toggleSearchBar(searchBarShowingFlag);
    }
	);

$('#endedDrawProductCardsDiv').on('click',function(){
    if(page > 0){
      return;
    }
  	$.ajax({
  		url: '/getEndedProductCardList',
  		type: 'get',
  		data: {
  			page: page
  		},
  		success: function(resultList) {
  			for (let i in resultList) {
				if(resultList[i].imgSrc_home != null){
  					$("#endedDrawProductCards").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '" style="opacity:0.4;"><img src="' + resultList[i].imgSrc_home + '" class="home-product-img"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
				} else {
					$("#endedDrawProductCards").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '" style="opacity:0.4;"><img src="https://s3.ap-northeast-2.amazonaws.com/sneakalarm.media/product/'+resultList[i].code+'/size450/'+resultList[i].model_en.replace(/ /g, '_')+'.jpg" class="home-product-img lozad" alt="'+resultList[i].model_kr+'"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
				}
  			}
  			page++;
  		},
  		error: function() {
  			alert('error! : getEndedProductCardList');
  		}
  	});
});


	window.addEventListener('scroll', () => {
		if (passFlag == 1 && getFlag == 1 && ($('#endedDrawProductCardsDiv').attr('is') == '1') && !(getScrollTop() < getDocumentHeight() - window.innerHeight - 100)) {
			passFlag = 0;
			$.ajax({
				url: '/getEndedProductCardList',
				type: 'get',
				data: {
					page: page
				},
				success: function(resultList) {
					setTimeout(function() {
						for (let i in resultList) {
							if (resultList[i].imgSrc_home != null) {
								$("#endedDrawProductCards").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '" style="opacity:0.4;"><img src="' + resultList[i].imgSrc_home + '" class="home-product-img"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
							} else {
								$("#endedDrawProductCards").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '" style="opacity:0.4;"><img src="https://s3.ap-northeast-2.amazonaws.com/sneakalarm.media/product/' + resultList[i].code + '/size450/' + resultList[i].model_en.replace(/ /g, '_') + '.jpg" class="home-product-img lozad" alt="' + resultList[i].model_kr + '"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
							}
						}
						if (resultList.length < 8)
							getFlag = 0;
						page++;
						passFlag = 1;
					}, 150);
				},
				error: function() {
					alert('error! : getEndedProductCardList');
				}
			});
		}
	});

	$('#endedDrawProductCardsDiv').click(function() {
		if ($(this).attr('is') == '0') {
			$(this).attr('is', '1');
			$('.endedDrawProductCard').show();
		}
		else {
			$(this).attr('is', '0');
			$('.endedDrawProductCard').hide();
		}
	});
	
	$('#searchBar').keyup(function(key){
	if(keyFlag == 1){
		if(key.keyCode != 13){
			getFlag=0;
			return;
		}
		
		keyFlag = 0;
		setTimeout(function(){
			$('#productSearchResult').empty();
			$('.home-product').hide();
			$('#endedDrawProductCardsDiv').hide();
			$('.home-readyDrawNum').hide();
			
			var keyword=$('#searchBar').val();
			if(keyword == ''){
				getFlag=1;
				$('.home-product').show();
				$('#endedDrawProductCardsDiv').show();
				$('.home-readyDrawNum').show();
				$('#endedDrawProductCards').hide();
			}
			$.ajax({
				url: '/getProductCardListByKeyword',
				type: 'get',
				data: {
					keyword: keyword
				},	
				success: function(resultList) {
					for (let i in resultList) {
						if (resultList[i].status == 'going') {
							if (resultList[i].imgSrc_home != null) {
								$("#productSearchResult").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '"><img src="' + resultList[i].imgSrc_home + '" class="home-product-img"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
							} else {
								$("#productSearchResult").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '"><img src="https://s3.ap-northeast-2.amazonaws.com/sneakalarm.media/product/' + resultList[i].code + '/size450/' + resultList[i].model_en.replace(/ /g, '_') + '.jpg" class="home-product-img lozad" alt="' + resultList[i].model_kr + '"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
							}
						} else {
							if (resultList[i].imgSrc_home != null) {
								$("#productSearchResult").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '" style="opacity:0.4;"><img src="' + resultList[i].imgSrc_home + '" class="home-product-img"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
							} else {
								$("#productSearchResult").append('<div class="home-product endedDrawProductCard"><div class="d-flex flex-column align-items-center endedDrawProductCard" id="card-' + resultList[i].id + '"><a class="endedDrawProductCard item-zoom" href="/product-detail?id=' + resultList[i].id + '" style="opacity:0.4;"><img src="https://s3.ap-northeast-2.amazonaws.com/sneakalarm.media/product/' + resultList[i].code + '/size450/' + resultList[i].model_en.replace(/ /g, '_') + '.jpg" class="home-product-img lozad" alt="' + resultList[i].model_kr + '"></a><span class="endedDrawProductCard home-product-model_kr">' + resultList[i].model_kr + '</span><span class="endedDrawProductCard" id="home-product-releaseDate">' + resultList[i].releaseDate + ' 출시</span></div></div>');
							}
						}
					}
				},
				error: function() {
					alert('error: searchError!');
				}
			});
			keyFlag = 1;
		},100);
	}
	});
});

// 현재 스크롤한 높이를 구하는 함수 
function getScrollTop() {
	return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;
}
// 문서의 높이를 구하는 함수
function getDocumentHeight() {
	const body = document.body;
	const html = document.documentElement;

	return Math.max(
		body.scrollHeight, body.offsetHeight,
		html.clientHeight, html.scrollHeight, html.offsetHeight
	);
}
