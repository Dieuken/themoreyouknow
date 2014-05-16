


window.onload = getUsers();
function getUsers(){
   $.ajax({
		     url:"http://localhost:8080/The_more_you_know/api/users",
		     contentType: "application/json; charset=utf-8",
                            
		     success:function(data){
                        
                         for(var user in data){
                             user = data[user];
                             
                             var li = "<li>";
                             $("ul").append(li.concat(user.email + " " + user.lat + " " + user.lng));
                         }
                     }
			});
}

