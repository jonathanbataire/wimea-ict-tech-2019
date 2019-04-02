<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);

function getRealIpAddr()
                    {
                        if (!empty($_SERVER['HTTP_CLIENT_IP']))   //check ip from share internet
                        {
                        $ip=$_SERVER['HTTP_CLIENT_IP'];
                        }
                        elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR']))   //to check ip is pass from proxy
                        {
                        $ip=$_SERVER['HTTP_X_FORWARDED_FOR'];
                        }
                        else
                        {
                        $ip=$_SERVER['REMOTE_ADDR'];
                        }
                        return $ip;
                    }
 
 
    // receiving the post params
    $form_date = strtotime($_POST['Date']);
    $date = date('Y-m-d',$form_date);
    $TIME = $_POST['TIME'];
    $user= $_POST['O_SubmittedBy'];
    $id = $_POST['Userid'];
	
	    $station = $_POST['station_name'];
        $stationNumber = $_POST['station_number'];
        $totalAmountOfAllClouds = intval($_POST['TotalAmountOfAllClouds']);
        $totalAmountOfLowClouds = intval($_POST['TotalAmountOfLowClouds']);

        $TypeOfLowClouds1 = intval($_POST['TypeOfLowClouds1']);
        $OktasOfLowClouds1= intval($_POST['OktasOfLowClouds1']);
        $HeightOfLowClouds1 = intval($_POST['HeightOfLowClouds1']);
        $CLCODEOfLowClouds1 = $_POST['CLCODEOfLowClouds1'];
        $TypeOfLowClouds2 = intval($_POST['TypeOfLowClouds2']);
        $OktasOfLowClouds2= intval($_POST['OktasOfLowClouds2']);
        $HeightOfLowClouds2 = intval($_POST['HeightOfLowClouds2']);
        $CLCODEOfLowClouds2 = $_POST['CLCODEOfLowClouds2'];
        $TypeOfLowClouds3 = intval($_POST['TypeOfLowClouds3']);
        $OktasOfLowClouds3= intval($_POST['OktasOfLowClouds3']);
        $HeightOfLowClouds3 = intval($_POST['HeightOfLowClouds3']);
        $CLCODEOfLowClouds3 = $_POST['CLCODEOfLowClouds3'];

        $TypeOfMediumClouds1 = intval($_POST['TypeOfMediumClouds1']);
        $OktasOfMediumClouds1= intval($_POST['OktasOfMediumClouds1']);
        $HeightOfMediumClouds1 = intval($_POST['HeightOfMediumClouds1']);
        $CLCODEOfMediumClouds1 = $_POST['CLCODEOfMediumClouds1'];
        $TypeOfMediumClouds2 = intval($_POST['TypeOfMediumClouds2']);
        $OktasOfMediumClouds2= intval($_POST['OktasOfMediumClouds2']);
        $HeightOfMediumClouds2 = intval($_POST['HeightOfMediumClouds2']);
        $CLCODEOfMediumClouds2 = $_POST['CLCODEOfMediumClouds2'];
        $TypeOfMediumClouds3 = intval($_POST['TypeOfMediumClouds3']);
        $OktasOfMediumClouds3= intval($_POST['OktasOfMediumClouds3']);
        $HeightOfMediumClouds3 = intval($_POST['HeightOfMediumClouds3']);
        $CLCODEOfMediumClouds3 = $_POST['CLCODEOfMediumClouds3'];

        $TypeOfHighClouds1 = intval($_POST['TypeOfHighClouds1']);
        $OktasOfHighClouds1= intval($_POST['OktasOfHighClouds1']);
        $HeightOfHighClouds1 = intval($_POST['HeightOfHighClouds1']);
        $CLCODEOfHighClouds1 = $_POST['CLCODEOfHighClouds1'];
        $TypeOfHighClouds2 = intval($_POST['TypeOfHighClouds2']);
        $OktasOfHighClouds2= intval($_POST['OktasOfHighClouds2']);
        $HeightOfHighClouds2 = intval($_POST['HeightOfHighClouds2']);
        $CLCODEOfHighClouds2 = $_POST['CLCODEOfHighClouds2'];
        $TypeOfHighClouds3 = intval($_POST['TypeOfHighClouds3']);
        $OktasOfHighClouds3= intval($_POST['OktasOfHighClouds3']);
        $HeightOfHighClouds3 = intval($_POST['HeightOfHighClouds3']);
        $CLCODEOfHighClouds3 = $_POST['CLCODEOfHighClouds3'];
        
        $CloudSearchLightReading = intval($_POST['CloudSearchLightReading']);
        $Rainfall= $_POST['Rainfall'];
        $Dry_Bulb= floatval($_POST['Dry_Bulb']);
        $Wet_Bulb = floatval($_POST['Wet_Bulb']);
        $Present_Weather = $_POST['Present_Weather'];
        $Present_WeatherCode = $_POST['Present_WeatherCode'];

        $Past_Weather = $_POST['Past_Weather'];
		$Past_WeatherCode = $_POST['Past_Weather_code'];
        $Visibility = floatval($_POST['Visibility']);
        $Wind_Direction = $_POST['Wind_Direction'];
        $Wind_Speed = $_POST['Wind_Speed'];
        $Gusting = floatval($_POST['Gusting']);
        $AttdThermo = floatval($_POST['AttdThermo']);
        $PrAsRead = floatval($_POST['PrAsRead']);
        $Correction = floatval($_POST['Correction']);
        $CLP = $_POST['CLP'];
        $MSLPr = floatval($_POST['MSLPr']);
        $gmt_mff = $_POST['GrassMinTemp'];
        $Max_temp = $_POST['Max_temp'];
        $Min_temp = $_POST['Min_temp'];

        $TimeMarksBarograph = floatval($_POST['TimeMarksBarograph']);
        $TimeMarksAnemoograph = floatval($_POST['TimeMarksAnemograph']);
       $OtherTMarks = $_POST['OtherTMarks'];
        $Remarks = $_POST['Remarks'];
        $DurationOfSunshine=$_POST['sunduration'];
        $dopop_mff = $_POST['DurationOfPeriodOfPrecipitation'];

        $UnitOfWindSpeed_mff = $_POST['UnitOfWindSpeed'];
		$IndOrOmissionOfPrecipitation_mff = $_POST['IndOrOmissionOfPrecipitation'];
		$TypeOfStation_Present_Past_Weather_mff = $_POST['TypeOfStation_Present_Past_Weather'];
		$heightOfLowestCloud_mff = $_POST['HeightOfLowestCloud'];
		$StandardIsobaricSurface_mff= $_POST['StandardIsobaricSurface'];
		$CI_OfPrecipitation_mff = $_POST['CI_OfPrecipitation'];
		$BE_OfPrecipitation_mff = $_POST['BE_OfPrecipitation'];
		$IndicatorOfTypeOfIntrumentation_mff = $_POST['IndicatorOfTypeOfIntrumentation'];
		$SignOfPressureChange_mff = $_POST['SignOfPressureChange'];
		$supplementaryinformation_mff= $_POST['Supp_Info'];
		$VapourPressure_mff = $_POST['VapourPressure'];
        $thgraph_mff = $_POST['T_H_Graph'];
        $approved="FALSE";
        $InputType="Mobile";
        $metarOrSpeci=$_POST['speciormetar'];

        $station_id= $db->identifyStationById($station,$stationNumber);

        $insertObservationSlipFormData=array(
            'Date'=>$date,'station'=>$station_id,'Userid'=>$id,
            'TIME'=> $TIME,'DeviceType '=> $InputType,
            'TotalAmountOfAllClouds'=>$totalAmountOfAllClouds,  'TotalAmountOfLowClouds'=> $totalAmountOfLowClouds,
            'TypeOfLowClouds1'=> $TypeOfLowClouds1, 'OktasOfLowClouds1'=> $OktasOfLowClouds1,
            'HeightOfLowClouds1'=>$HeightOfLowClouds1, 'CLCODEOfLowClouds1'=> $CLCODEOfLowClouds1,
            'TypeOfLowClouds2'=> $TypeOfLowClouds2, 'OktasOfLowClouds2'=> $OktasOfLowClouds2,
            'HeightOfLowClouds2'=>$HeightOfLowClouds2, 'CLCODEOfLowClouds2'=> $CLCODEOfLowClouds2,
            'TypeOfLowClouds3'=> $TypeOfLowClouds3, 'OktasOfLowClouds3'=> $OktasOfLowClouds3,
            'HeightOfLowClouds3'=>$HeightOfLowClouds3, 'CLCODEOfLowClouds3'=> $CLCODEOfLowClouds3,
            'TypeOfMediumClouds1'=> $TypeOfMediumClouds1, 'OktasOfMediumClouds1'=> $OktasOfMediumClouds1,
            'HeightOfMediumClouds1'=>$HeightOfMediumClouds1, 'CLCODEOfMediumClouds1'=> $CLCODEOfMediumClouds1,
            'TypeOfMediumClouds2'=> $TypeOfMediumClouds2, 'OktasOfMediumClouds2'=> $OktasOfMediumClouds2,
            'HeightOfMediumClouds2'=>$HeightOfMediumClouds2, 'CLCODEOfMediumClouds2'=> $CLCODEOfMediumClouds2,

            'TypeOfMediumClouds3'=> $TypeOfMediumClouds3, 'OktasOfMediumClouds3'=> $OktasOfMediumClouds3,
            'HeightOfMediumClouds3'=>$HeightOfMediumClouds3, 'CLCODEOfMediumClouds3'=> $CLCODEOfMediumClouds3,

            'TypeOfHighClouds1'=> $TypeOfHighClouds1, 'OktasOfHighClouds1'=> $OktasOfHighClouds1,
            'HeightOfHighClouds1'=>$HeightOfHighClouds1, 'CLCODEOfHighClouds1'=> $CLCODEOfHighClouds1,

            'TypeOfHighClouds2'=> $TypeOfHighClouds2, 'OktasOfHighClouds2'=> $OktasOfHighClouds2,
            'HeightOfHighClouds2'=>$HeightOfHighClouds2, 'CLCODEOfHighClouds2'=> $CLCODEOfHighClouds2,

            'TypeOfHighClouds3'=> $TypeOfHighClouds3, 'OktasOfHighClouds3'=> $OktasOfHighClouds3,
            'HeightOfHighClouds3'=>$HeightOfHighClouds3, 'CLCODEOfHighClouds3'=> $CLCODEOfHighClouds3,

            'CloudSearchLightReading'=> $CloudSearchLightReading,
            'Rainfall'=> $Rainfall, 'Dry_Bulb'=>$Dry_Bulb,'Wet_bulb'=> $Wet_Bulb,
            'Present_Weather'=>$Present_Weather,'Present_WeatherCode'=>$Present_WeatherCode,
            'Past_Weather'=>$Past_Weather,'Past_WeatherCode'=>$Past_WeatherCode,
			'Visibility'=>$Visibility,

            'Max_temp'=>$Max_temp, 'Min_temp'=>$Min_temp,


            'Wind_Direction'=>$Wind_Direction,'Wind_Speed'=>$Wind_Speed,
            'Gusting'=>$Gusting,            'AttdThermo'=>$AttdThermo,
            'PrAsRead'=>$PrAsRead,            'Correction'=>$Correction,
            'CLP'=>$CLP,            'MSLPr'=>$MSLPr,
            'TimeMarksBarograph'=>$TimeMarksBarograph,     'TimeMarksAnemograph'=>$TimeMarksAnemoograph,
            'OtherTMarks'=>$OtherTMarks,
            'Remarks'=>$Remarks,
            'sunduration'=> $DurationOfSunshine,'Approved'=>$approved,

            'speciOrMetar'=>$metarOrSpeci,
            'UnitOfWindSpeed'=>$UnitOfWindSpeed_mff, 'IndOrOmissionOfPrecipitation'=>$IndOrOmissionOfPrecipitation_mff,
            'TypeOfStation_Present_Past_Weather'=>$TypeOfStation_Present_Past_Weather_mff, 	'HeightOfLowestCloud'=>$heightOfLowestCloud_mff,
            'StandardIsobaricSurface'=>$StandardIsobaricSurface_mff,
            'DurationOfPeriodOfPrecipitation'=>$dopop_mff,'GrassMinTemp'=>$gmt_mff,
            'CI_OfPrecipitation'=>$CI_OfPrecipitation_mff, 'BE_OfPrecipitation'=>$BE_OfPrecipitation_mff,
            'IndicatorOfTypeOfIntrumentation'=>$IndicatorOfTypeOfIntrumentation_mff, 	'SignOfPressureChange'=>$SignOfPressureChange_mff,
            'Supp_Info'=>$supplementaryinformation_mff, 'VapourPressure'=>$VapourPressure_mff,
        'T_H_Graph'=>$thgraph_mff ,'O_SubmittedBy'=>$user);

            $checkduplicateform = $db->checkforduplicate($date,$station_id,$TIME,$metarOrSpeci);
             
            if($checkduplicateform != true){
                $fieldsarr=array_keys($insertObservationSlipFormData);
                $fields = implode(",",$fieldsarr);
                $values = "'".implode("','",$insertObservationSlipFormData)."'";

                $insertsuccess= $db->insertData($fields,$values);
                

                if ($insertsuccess){

                    $ip = getRealIpAddr();
                    $data_id = $db->getdataid($date,$station_id,$TIME,$metarOrSpeci);

                    $userlogs = array('Userid' => $id,'Action' => 'Added Observation Slip',
                        'Details' => $user .' added an Observation Slip via Mobile App','data_id'=>$data_id,
                        'IP' =>$ip);
                        $logsarr=array_keys($userlogs);
                        $log_fields = implode(",",$logsarr);
                        $log_values = "'".implode("','",$userlogs)."'";
                        $log = $db->insertUserLogs($log_fields,$log_values);
		
                    $response["error"] = false;
					$response["dup_code"] = false;
                    $response["server_msg"] = "New Observation Slip Info added successfully!";
                    echo json_encode($response);
                }
                else{
                    $response["error"] = true;
					$response["dup_code"] = false;
                    $response["server_msg"] ="Server Error Occurred";
                    echo json_encode($response);
                }
            }else{
                $response["error"] = true;
				$response["dup_code"] = true;
                $response["server_msg"] ="A Record for this time has Already Been Submitted";
                echo json_encode($response);
            }

        
	
    
     /**/


	
    
?>