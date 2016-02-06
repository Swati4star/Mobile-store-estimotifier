<?php

	require_once '../inc/connection.inc.php';
	require_once '../inc/function.inc.php';
	
	$user_id = (int)$_GET['user'];
	$message = encryptText($_GET['message']);
	$flag = intval(!(bool)$_GET['flag']);
	// 0 means admin, 1 means user
	
	$query = "INSERT INTO `chats` (`user_id`,`flag`,`message`) VALUES ('$user_id','$flag','$message')";
	if(mysqli_query($connection, $query)){
		echo 1;
	} else {
		echo 0;
	}