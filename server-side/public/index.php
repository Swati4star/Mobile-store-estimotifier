<?php
	require_once '../inc/connection.inc.php';

	if(isset($_GET['id'])){
		$user_id = (int)$_GET['id'];
?>

<!doctype html>
<html>
<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="author" content="prabhakar gupta">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="../css/style.css">
	<script type="text/javascript">
		var user_id = <?php echo $user_id; ?>;
		config_path_ajax = "http://csinsit.org/prabhakar/airtel/chat";
	</script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-8" id="chat-box"></div>
			<div class="col-md-4">
				<form method="POST">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="enter your message here...">
					</div>
					<button type="button" class="btn btn-default" id="submit-button">Submit</button>
					<button type="button" class="btn btn-default" id="refresh-button">Refresh</button>
					<a href="http://csinsit.org/prabhakar/airtel/public/"><button type="button" class="btn btn-default">Go Back</button></a>
				</form>
			</div>
        </div>
    </div>
	
	
	
<script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" type="text/javascript"></script>	
<script type="text/javascript" src="../js/custom.js"></script>

	
</body>
</html>
<?php
	} else {
?>

<!doctype html>
<html>
<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="author" content="prabhakar gupta">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <div class="row">
			<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
<?php
	$query = "SELECT * FROM `users`";
	$query_run = mysqli_query($connection, $query);
	
	$count = 0;
	while($query_row = mysqli_fetch_assoc($query_run)){
		$id = $query_row['device_id'];
		$count++;
		
		echo '<div class="panel panel-default">
					<div class="panel-heading" role="tab" id="heading' . $id . '">
						<h4 class="panel-title">
							<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse' . $id . '" aria-expanded="false" aria-controls="collapse' . $id . '">
								User #' . $count . '
							</a>
						</h4>
					</div>
					<div id="collapse' . $id . '" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading' . $id . '">
						<div class="panel-body">
							<strong>User ID</strong> : ' . $query_row['id'] . '<br>
							<strong>Device ID</strong> : ' . $id . '<br>
							<strong>Service Provider</strong> : ' . $query_row['service_provider'] . '<br>
							<!-- <strong>Resolved</strong> : <strong class="text-danger">true</strong><br>-->
							<strong><a href="http://' . $_SERVER[HTTP_HOST] . $_SERVER[REQUEST_URI] . 'support/' . $query_row['id'] . '">Open Chat</a></strong>
						</div>
					</div>
				</div>';
	}

?>
			</div>
        </div>
    </div>
	
<script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" type="text/javascript"></script>	

</body>
</html>

<?php		
	}
?>