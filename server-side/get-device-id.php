<?php
	require_once 'inc/connection.inc.php';
	require_once 'inc/function.inc.php';
	
	$user_id = NULL;
	
	
	// to check if it is a GET request or not
	if(isset($_GET['device']) && isset($_GET['service'])){
		$device_id = $_GET['device'];
		$service_provider = encryptText($_GET['service']);
		
		$query = "SELECT `id` FROM `users` WHERE `device_id`='$device_id'";
		$query_run = mysqli_query($connection, $query);
		
		// to check if device has already been registered or not
		if(mysqli_num_rows($query_run) > 0){
			$query_row = mysqli_fetch_assoc($query_run);
			$user_id = $query_row['id'];
		} else {
			$query_ins = "INSERT INTO `users`(`device_id`,`service_provider`) VALUES ('$device_id','$service_provider')";
			mysqli_query($connection, $query_ins);
			$user_id = mysqli_insert_id($connection);
		}
		
	}
	
	echo json_encode(array('user_id' => (int)$user_id));