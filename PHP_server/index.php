<?php
/*
 * Created on 2012-7-22
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 
 if(isset($_REQUEST['tag'])&&$_REQUEST['tag']!=''){
 	$tag=$_REQUEST['tag'];
 	
 	require_once 'DB_Functions.php';
 	$db=new DB_Functions(); //建立连接
 	$response=array("tag"=>$tag, "success"=>0,"error"=>0);
 	
 	if($tag=='login'){
 		$email=$_REQUEST['email'];
 		$password=$_REQUEST['password'];
 		$user=$db->getUserByEmailAndPassword($email,$password);
 		if($user!=false){
 			$response["success"]=1;
 			$response["uid"]=$user["unique_id"];
 			$response["user"]["name"]=$user["name"]; //array变多维数组了。。
 			$response["user"]["email"]=$user["email"];
            $response["user"]["created_at"] = $user["created_at"];
            $response["user"]["updated_at"] = $user["updated_at"];
            echo json_encode($response);
 		}
  /*这个多维数组转化为jason 格式的样子...
   * {
    "tag": "register",
    "success": 1,
    "error": 0,
    "uid": "4f074ca1e3df49.06340261",
    "user": {
        "name": "Ravi Tamada",
        "email": "ravi8x@gmail.com",
        "created_at": "2012-01-07 01:03:53",
        "updated_at": null
    }
	}
   */
 	
 		}
 	else if($tag=='addfriend'){
 		$myemail = $_REQUEST['myemail'];
 		$friendemail=$_REQUEST['friendemail'];
 		if($db->ishasfriendship($myemail,$friendemail)){
            $response["error"] = 1;
            $response["error_msg"] = "alread friend";
            echo json_encode($response);
 		}
		else if($myemail==$friendemail){
			$response["error"]=1;
			$response["error_msg"] = "you cannot add yourself";
			 echo json_encode($response);
		}
 		else{
	 		$user=$db->addfriend($myemail,$friendemail);
	 		if($user!=false){
	                $response["success"] = 1;  
					$response["error_msg"] = ""; 
	                echo json_encode($response);       		      						
	 		} 		
	 		else{	
	 			echo "add friend failed";
	 		}
 		}
 	}	
	
 	else if($tag=='deletefriend'){
 		$myemail = $_REQUEST['myemail'];
 		$friendemail=$_REQUEST['friendemail'];
 		if($db->ishasfriendship($myemail,$friendemail)){
		
			$user=$db->deletefriend($myemail,$friendemail);
	 		if($user!=false){
	                $response["success"] = 1;  
					$response["error_msg"] = ""; 
	                echo json_encode($response);       		      						
	 		} 		
	 		else{	
	 			echo "add friend failed";
	 		}
	
 		}
 		else{
            $response["error"] = 1;
            $response["error_msg"] = "no friendship";
            echo json_encode($response);
 		}
 	}		
	
	
	
	
	
	
	
	
	
 	
 	else if($tag=='showfriends'){
 		
  		$myemail = $_REQUEST['myemail'];
  		$user=$db->getfriendinfo($myemail);		
 		if($user){			
 			echo json_encode($user,JSON_FORCE_OBJECT);					
 		}
 		else{
 			echo "unknown error!";
 		}
 		
 	}
 		
 		
 		
 	else if($tag=='register'){
	        $name = $_REQUEST['name'];
	        $email = $_REQUEST['email'];
	        $password = $_REQUEST['password'];
      	 if ($db->isUserExisted($email)) {
            // user is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } 
        else {
           $user = $db->storeUser($name, $email, $password);    
           if($user){
                 // user stored successfully
                $response["success"] = 1;
                $response["uid"] = $user["unique_id"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["created_at"] = $user["created_at"];
                $response["user"]["updated_at"] = $user["updated_at"];
                echo json_encode($response);          	

           }    
           else{
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);           	
           	
           }	
        	
        }  			
 			
 		}
 	else{
 		echo "invalid request";	
 	}
 }

 else{
 	
 	var_dump($_REQUEST);
 	
 }
 
 
 
 
?>
