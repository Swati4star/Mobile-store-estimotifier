<?php

	require_once 'inc/connection.inc.php';
	require_once 'inc/function.inc.php';
	
	$ONE_HOUR = 3600;
	$OFFER_FREQUENCY = 10;
	
	if(isset($_GET['estimote']) && isset($_GET['user'])){
		$beacon_id 	= $_GET['estimote'];
		$user_id	= $_GET['user'];
		$error = false;
		
		$store_id = getStoreID($connection, $beacon_id);
		$current_time = time();
		$check_time = $current_time - $ONE_HOUR;
		
		// query to check if user has any log in this store in past one hour or not
		$query_check = "SELECT `id` FROM `log` WHERE `user_id`='$user_id' AND `store_id`='$store_id' AND `create_time_tx`>='$check_time'";
		$query_check_run = mysqli_query($connection, $query_check);
		
		if(mysqli_num_rows($query_check_run) == 0){
			// check if this user is eligible for special offer or not
			$last_offered_timestamp = getLastOfferedTime($connection, $store_id);
			
			$query_offer = "SELECT count(`id`) AS count FROM `log` WHERE `create_time_tx` >= '$last_offered_timestamp' AND `store_id`='$store_id'";
			
			$query_row = mysqli_fetch_assoc(mysqli_query($connection, $query_offer));
			$count = $query_row['count'];
			
			// since no log was present in past one hour so create a log and check for special offer
			if($count % $OFFER_FREQUENCY == 0){
				$offer = true;
				
				$query_ins = "INSERT INTO `log` (`user_id`,`store_id`,`create_time_tx`,`offered_flag`) VALUES ('$user_id','$store_id','$current_time',1)";
			} else {
				$offer = false;
				
				$query_ins = "INSERT INTO `log` (`user_id`,`store_id`,`create_time_tx`) VALUES ('$user_id','$store_id','$current_time')";
			}
			mysqli_query($connection, $query_ins);
			
		} else {
			$error = true;
		}
		
		$final_response = array(
			'log_created'	=> (int)!$error,
			'special_offer'	=> (int)$offer
		);
		
		if($offer){
			$final_response['offer'] = getRandomSpecialPlan($connection);
		}
		
		echo json_encode($final_response);
	}