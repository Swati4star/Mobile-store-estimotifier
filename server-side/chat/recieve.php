<?php

	require_once '../inc/connection.inc.php';
	require_once '../inc/function.inc.php';
	
	$final_response = array();
	$user_id = (int)$_GET['user'];
	$last_time = getTimeStamp((int)$_GET['time']);
	
	$query = "SELECT `flag`,`message`,`time` FROM `chats` WHERE `user_id`='$user_id' AND `time`>='$last_time' ORDER BY `time` ASC";
	$query_run = mysqli_query($connection, $query);
	
	while($query_row = mysqli_fetch_assoc($query_run)){
		$query_row['flag'] = (int)$query_row['flag'];
		$query_row['time'] = strtotime($query_row['time']);
		
		array_push($final_response, $query_row);
	}
	
	echo json_encode($final_response);
	