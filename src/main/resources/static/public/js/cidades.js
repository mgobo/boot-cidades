function estados(){		
	$.ajax({
		type: "GET",
		url: "/cidades/estados",
		data: "$format=json",
		dataType: "json",
		success: function(estados){			
			$("#estados").empty();
			$.each(estados, function(i,val){				
				$("#estados").append("<option value=\""+val+"\">"+val+"</option>");				
			});										
		}
	});
}	