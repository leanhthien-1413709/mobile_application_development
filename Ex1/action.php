<?php

header('Content-type: application/json; charset=UTF-8');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "examples";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);

    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

	/**
	 * Calculates the great-circle distance between two points, with
	 * the Vincenty formula.
	 */

	function distance(
	  $latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo)
	{

		$earthRadius = 6371000;

	  // convert from degrees to radians
	  $latFrom = deg2rad($latitudeFrom);
	  $lonFrom = deg2rad($longitudeFrom);
	  $latTo = deg2rad($latitudeTo);
	  $lonTo = deg2rad($longitudeTo);

	  $lonDelta = $lonTo - $lonFrom;
	  $a = pow(cos($latTo) * sin($lonDelta), 2) +
	    pow(cos($latFrom) * sin($latTo) - sin($latFrom) * cos($latTo) * cos($lonDelta), 2);
	  $b = sin($latFrom) * sin($latTo) + cos($latFrom) * cos($latTo) * cos($lonDelta);

	  $angle = atan2(sqrt($a), $b);
	  return $angle * $earthRadius;
	}

	$obj = json_decode(file_get_contents('php://input'), true);

	$lat_1 = (float)$obj['lat_1'];
	$lng_1 = (float)$obj['lng_1'];
	$lat_2 = (float)$obj['lat_2'];
	$lng_2 = (float)$obj['lng_2'];

	$res = distance($lat_1, $lng_1, $lat_2, $lng_2);

    $result = array('res' => $res);

	echo json_encode($result);

    }

catch(PDOException $e)
    {
    echo "Error: " . $e->getMessage();
    }

$conn = null;
?>
