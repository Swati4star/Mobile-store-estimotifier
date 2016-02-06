<?php
	date_default_timezone_set('Asia/Kolkata'); 
	$date = date('y.m.d-Hi-', time());

	$waiting_time = 5 * rand(1,7) * 60;
	
	$response = array(
		'token number'		=> $date . substr( md5(rand()), 0, 6),
		'estimated_time'	=> time() + $waiting_time,
		'counter_num'		=> rand(1,5)
	);
	
	echo json_encode($response);