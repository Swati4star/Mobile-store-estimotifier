<?php
	require_once '../inc/connection.inc.php';
	require_once '../inc/function.inc.php';
	
	$user_id = (int)$_GET['id'];
	
	if(isset($_POST['submit'])){
		
		$message = encryptText($_POST['message']);
		$query = "INSERT INTO `chats` (`user_id`,`flag`,`message`) VALUES ('$user_id',0,'$message')";
		mysqli_query($connection, $query);
	}
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
            <div class="col-md-8">
<?php
	$names_array = array();
	
	if(isset($_GET['id'])){
		
		$query = "SELECT * FROM `chats` WHERE `user_id`='$user_id'";
		$query_run = mysqli_query($connection, $query);
		
		while($query_row = mysqli_fetch_assoc($query_run)){
			$timestamp = strtotime($query_row['time']);
			$timestamp = date('(d-M-y) h:i A' , $timestamp + 19800);
			
			if($query_row['flag']%2 != 0){
				echo '<div class="aloo person0">';
			} else {
				echo '<div class="aloo person1 left-margin-20">';
			}
			echo '<div class="text">' . decryptText($query_row['message']) . '</div>';
			echo '<div class="time">' . $timestamp . '</div>';
			echo "</div>\n";
		}
	}
?>

            </div>
        </div>
		<hr>
		<div class="row">
			<form method="POST">
				<div class="form-group">
					<label for="exampleInputEmail1">Text</label>
					<input type="text" class="form-control" name="message" placeholder="Email">
				</div>
				<button type="submit" name="submit" class="btn btn-default">Submit</button>
			</form>
		</div>
    </div>
</body>
</html>