$('document').ready(function(){	
	var getFlag = 1;
	var page = 0;
 	$.ajax({
  		url:'/getEndedProductCardList',
    	type:'get',
    	data:{
     		page: page
    	},
    	success: function(resultList) {
    		for(var i in resultList){
    			$("#endedDrawProductCards").append("<div class=\"home-product\"><div class=\"d-flex flex-column align-items-center endedDrawProductCard\" id=\"card-"+resultList[i].id+"\" style=\"display:none;\"><a class=\"endedDrawProductCard\" href=\"/product-detail?id="+resultList[i].id+"\" style=\"opacity:0.4; display:none;\"><img src=\""+resultList[i].imgSrc_home+"\" class=\"home-product-img\"></a><span class=\"endedDrawProductCard\" id=\"home-product-model_kr\" style=\"display:none;\">"+resultList[i].model_kr+"</span></div></div>");
    		}
    		page++;
   		},
    	error: function(){
    		alert('error! : getEndedProductCardList');
    	}
	}); 
 	
	$(window).scroll(function() {
	    if (getFlag==1 && ($('#endedDrawProductCardsDiv').attr('is')=='1') && ($(window).scrollTop() == $(document).height() - $(window).height())) {
	      $.ajax({
		  		url:'/getEndedProductCardList',
		    	type:'get',
		    	data:{
		     		page: page
		    	},
		    	success: function(resultList) {
		    		for(var i in resultList){
		    			$("#endedDrawProductCards").append("<div class=\"home-product\"><div class=\"d-flex flex-column align-items-center endedDrawProductCard\" id=\"card-"+resultList[i].id+"\" style=\"display:none;\"><a class=\"endedDrawProductCard\" href=\"/product-detail?id="+resultList[i].id+"\" style=\"opacity:0.4; display:none;\"><img src=\""+resultList[i].imgSrc_home+"\" class=\"home-product-img\"></a><span class=\"endedDrawProductCard\" id=\"home-product-model_kr\" style=\"display:none;\">"+resultList[i].model_kr+"</span></div></div>");
		    		}
		    		if(resultList.length == 0)
		    			getFlag = 0;
		   		},
		    	error: function(){
		    		alert('error! : getEndedProductCardList');
		    	}
	   	  });
	      page++;
	    }
	});
	
	$('#endedDrawProductCardsDiv').click(function(){
	 	if($(this).attr('is') == '0'){
			$(this).attr('is','1');
			$('.endedDrawProductCard').show();
		}
		else {
			$(this).attr('is','0');
			$('.endedDrawProductCard').hide();
		}
	});
});