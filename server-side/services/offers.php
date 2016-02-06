<?php
	require_once '../inc/connection.inc.php';
	
	$final_response = array();
	$query = "SELECT `offer` FROM `special_offers` ORDER BY `offer`";
	$query_run = mysqli_query($connection, $query);
	while($query_row = mysqli_fetch_assoc($query_run)){
		$temp = array(
			'offer'	=> $query_row['offer'],
			'url'	=> 'http://www.airtel.in/myoffer',
			'image'	=> 'https://pbs.twimg.com/profile_images/644805995705692160/hByAwFLY_normal.png',
		);
		
		array_push($final_response, $temp);
	}
	
	echo json_encode(array('results' => $final_response));	
	