<?php
 
/**
 * @author Ravi Tamada
 * @link https://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */
 
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }

   public function insertUserLogs($log_fields,$log_values){

    $stmt = $this->conn->prepare("INSERT INTO userlogs($log_fields) VALUES($log_values)");
        $result = $stmt->execute();
        $stmt->close();
        if($result){
            return true;
        }
        else{
            return false;
        }
    }

    public function getdataid($date,$station_id,$TIME,$metarOrSpeci){

        $stmt = $this->conn->prepare("SELECT id FROM observationslip where Date='".$date."' and Station='".$station_id."' and TIME='"
        .$TIME."' and speciOrMetar='".$metarOrSpeci."' limit 1");
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            while ($row = $result->fetch_array(MYSQLI_NUM)){
                foreach($row as $r){
                    $data_id=$r;
                }
            }
            $stmt->close();
            return $data_id;
        }else {
            return NULL;
        }
    }

    public function insertData($fields,$values){
        $stmt = $this->conn->prepare("INSERT INTO observationslip($fields) VALUES($values)");
        $result = $stmt->execute();
        $stmt->close();
        if($result){
            return true;
        }
        else{
            return false;
        }


    }

    public function checkforduplicate($date,$station_id,$TIME,$metarOrSpeci){

        $stmt = $this->conn->prepare("SELECT * FROM observationslip where Date='".$date."' and Station='".$station_id."' and TIME='"
        .$TIME."' and speciOrMetar='".$metarOrSpeci."' limit 1");
		//$stmt->bind_param("s", $email);
		if ($stmt->execute()) {
			 $record = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			return $record;
		}else {
            return null;
        }

    }


    public function identifyStationById($station,$stationNumber){
       
                $stmt = $this->conn->prepare("SELECT station_id FROM stations where StationName='".$station."' and StationNumber='".$stationNumber."' limit 1");
                //$stmt->bind_param("s", $email);
                if ($stmt->execute()) {
                    $result = $stmt->get_result();
                    while ($row = $result->fetch_array(MYSQLI_NUM)){
                        foreach($row as $r){
                            $station_id=$r;
                        }
                    }
                    $stmt->close();
                    return $station_id;
                }else {
                    return NULL;
                }

    }

	
	public function getUser($email, $password){
		$stmt = $this->conn->prepare("SELECT * FROM systemusers left join stations on station_id = station where UserName='".$email."' and UserPassword='".$password."'");
		//$stmt->bind_param("s", $email);
		if ($stmt->execute()) {
			 $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
			return $user;
		}else {
            return NULl;
        }
		
	}
	
    
 
}
 
?>