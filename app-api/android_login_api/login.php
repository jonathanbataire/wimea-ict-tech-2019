<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $email = $_POST['email'];
    $password = $_POST['password'];
	
	$user = $db->getUser($email,md5($password));
	if ($user != false) {
		$response["error"] = FALSE;
        $response["Userid"] = $user["Userid"];
        $response["user"]["UserName"] = $user["FirstName"]." ".$user["SurName"];
		$response["user"]["email"] = $user["UserRole"];
        $response["user"]["created_at"] = "7/20/2018";
        $response["user"]["updated_at"] = "7/20/2018";
		$response["user"]["latitude"] = $user["Latitude"];
		$response["user"]["longitude"] = $user["Longitude"];
		$response["user"]["station_name"] = $user["StationName"];
		$response["user"]["station_number"] = $user["StationNumber"];
	    echo json_encode($response);
	}else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
	
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}
?>