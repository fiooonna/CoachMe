<?php
$ch = curl_init();

$secretKey = '6LfmAVQjAAAAADgG0c3qpf01nYHl-_qfAKsyydgC';

$captcha = isset($_POST['recaptcha-response'] &&
                 !empty($_POST['recaptcha-response'])? $_POST['recaptcha-response']:'');

curl_setopt_array($ch,[
    CURLOPT_URL => 'https://www.google.com/recaptcha/api/siteverify',
    CURLOPT_POST => true,
    CURLOPT_POSTFIELDS => [
        'secret' => $secretKey,
        'response' => $captcha,
        'remoteip' => $_SERVER['REMOTE_ADDR']
    ],
    CURLOPT_RETURNTRANSFER => true
]);

$output = curl_exec($ch);
curl_close($ch);

$json = json_decode($output);
$res = array();
if ($json->success) {
    $res['success']=true;
    $res['message']='Successful';
} else {
    $res['success']=false;
    $res['message']='Failed';
}

echo json_encode($res);
?>