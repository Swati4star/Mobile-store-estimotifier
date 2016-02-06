function getMessages(time){
	var url = config_path_ajax
            + "/recieve.php?user=" + user_id
			+ "&time=" + time;

    var chat_div = $('#chat-box');
	
	$.ajax({
        url : url,
        success : function(data){
			data = jQuery.parseJSON(data);
			
			if(time == 0){
				if(data.length == 0){
					chat_div.html('No Chats');
				} else {
					chat_div.html('');
				}
			}
                
			for(var count in data){
				var chat = data[count];
				var chat = data[count];
				
				if(chat.flag == 1){
					chat_div.prepend('<div class="aloo person0"><div class="text">' + chat.message + '</div><div class="time">' + chat.time + '</div></div>');
				} else {
					chat_div.prepend('<div class="aloo person1 left-margin-20"><div class="text">' + chat.message + '</div><div class="time">' + chat.time + '</div></div>');
				}
			}
        }
    });

}

function sendMessage(message){
	var url = config_path_ajax
            + "/send.php?user=" + user_id
			+ "&message=" + message
			+ "&flag=1";
	
	$.ajax({
        url : url,
        success : function(data){
			getCurrentMessages();
        }
    });
}

function getCurrentMessages(){
	var current_time = Math.round(+new Date()/1000);
	// console.log(current_time);
	getMessages(0);	
}

$(document).ready(function(){
	
	$(document).on("click","#submit-button",function(){
        var selectedForm = $(this).parents('form'),
            message = selectedForm.find("input").val();
        
        if(message != ""){
            sendMessage(message);
        }
    });
	
	$(document).on("click","#refresh-button",function(){
        getCurrentMessages();
    });
	
	setInterval(function() {
		getCurrentMessages();
	}, 10000);
	
	getMessages(0);
});