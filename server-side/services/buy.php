<?php

	$final_response = array();
	
	$json = file_get_contents('data/ebay.json');
	$response = json_decode($json, true);
	foreach($response['findItemsAdvancedResponse'][0]['searchResult'][0]['item'] as $item){
		$temp_array = array(
			'name'		=> $item['title'][0],
			'url'		=> $item['viewItemURL'][0],
			'image'		=> $item['galleryURL'][0],
			'value'		=> (float)$item['sellingStatus'][0]['currentPrice'][0]['__value__'] * 64.85,
			
		);
		array_push($final_response, $temp_array);
	}

	echo json_encode(array('results' => $final_response));