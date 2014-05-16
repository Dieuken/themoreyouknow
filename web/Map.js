window.onload = fillMap();
function fillMap(){
                $.ajax({
		     url:"http://localhost:8080/The_more_you_know/api/users",
		     contentType: "application/json; charset=utf-8",
                            
		     success:function(data){
                        
                         for(var user in data){
                             user = data[user];
                             
                             addMarker(user.lat, user.lng);
                         }
                     }
			});
}