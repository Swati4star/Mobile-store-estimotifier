<?php

	require_once 'inc/connection.inc.php';
	require_once 'inc/function.inc.php';
	require_once 'inc/constants.inc.php';
	
	$final_response = array();
	
	$fixed_service = array(
		'service'	=> "Talk to a Service Executive",
		'flag'		=> 0
	);
	array_push($final_response, $fixed_service);
	
	if(isset($_GET['estimote'])){
		$beacon_id 	= $_GET['estimote'];
		
		$store_id = getStoreID($connection, $beacon_id);
		
		$query = "SELECT `services`.`service`,`services`.`flag`,`services`.`url` FROM `services` INNER JOIN `store_services` ON `store_services`.`service`=`services`.`id` WHERE `store_services`.`store`='$store_id' ORDER BY `services`.`service`";
		$query_run = mysqli_query($connection, $query);
		while($query_row = mysqli_fetch_assoc($query_run)){
			$temp = array(
				'service'	=> $query_row['service'],
				'flag'		=> (int)$query_row['flag'],
			);
			
			if($query_row['flag']){
				$temp['url'] = DOMAIN_NAME . $query_row['url'];
			}
			
			array_push($final_response, $temp);
		}
		
	}
	
	echo json_encode(array('services' => $final_response));