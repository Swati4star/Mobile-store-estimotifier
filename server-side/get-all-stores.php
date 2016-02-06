<?php

	require_once 'inc/connection.inc.php';
	$stores = array();
	
	$query = "SELECT * FROM `all_stores`";
	$query_run = mysqli_query($connection, $query);
	while($query_row = mysqli_fetch_assoc($query_run)){
		$temp = explode(':', $query_row['name']);
		$temp_array = array(
			'zone' 		=> trim($temp[0]),
			'short_add' => trim($temp[1]),
			'long_add' 	=> $query_row['add'],
			'lat' 		=> (float)$query_row['lat'],
			'lng' 		=> (float)$query_row['lng'],
		);
		
		array_push($stores, $temp_array);
	}
	
	echo json_encode(array('stores' => $stores));