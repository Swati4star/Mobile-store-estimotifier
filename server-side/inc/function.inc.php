<?php

	function getStoreID($connection, $beacon_id){
		$store_id = 0;
		
		$query = "SELECT `id` FROM `stores` WHERE `major`='$beacon_id' LIMIT 1";
		$query_row = mysqli_fetch_assoc(mysqli_query($connection, $query));
		$store_id = (int)$query_row['id'];
		
		return $store_id;
	}
	
	function getLastOfferedTime($connection, $store_id){
		$timestamp = 0;
		
		$query = "SELECT `create_time_tx` FROM `log` WHERE `offered_flag`=1 AND `store_id`='$store_id' ORDER BY `create_time_tx` DESC LIMIT 1";
		$query_row = mysqli_fetch_assoc(mysqli_query($connection, $query));
		$timestamp = $query_row['create_time_tx'];
		
		return $timestamp;
	}
	
	function getRandomSpecialPlan($connection){
		$query = "SELECT `offer` FROM `special_offers` ORDER BY RAND() LIMIT 1";
		$query_row = mysqli_fetch_assoc(mysqli_query($connection, $query));
		return $query_row['offer'];
	}
	
	function curl_URL_call($url){
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1); 
		$output = curl_exec($ch);
		curl_close($ch);
		return $output;
	}
	
	function encryptText($text){
		return strip_tags(addslashes($text));
	}
	
	function decryptText($text){
		return stripcslashes($text);
	}
	
	function getTimeStamp($time){
		return date("Y-m-d H:i:s", $time);
	}