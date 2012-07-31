<?php
/*
 * Created on 2012-7-22
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 *
 *
 *
 */
          require_once 'config.php';	
 //这个类的实例可以用db调用这些函数
 class DB_Functions {
	private $con;
	
    function __construct() { 	
        // connecting to mysql
     $this->con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
  		  if(!$this->con) { 
  			 die("Database connection failed: " . mysql_error()); 
			}
        // selecting database
        else{
    		$db_select=mysqli_select_db($this->con,DB_DATABASE);
    		 if (!$db_select) { 
     		  	die("Database selection failed: ". mysql_error()); 
  			 } 
        }
        return $this->con;
    }

  	public function addfriend($myemail, $friendemail){
  		
  		$myid=mysqli_query($this->con, "SELECT unique_id FROM users WHERE email='$myemail'");
  		if($myid){
	  		$myid=mysqli_fetch_array($myid);
	  		$myid=$myid['unique_id'];
  		}
  		$frid=mysqli_query($this->con, "SELECT unique_id FROM users WHERE email='$friendemail'");
  		if($frid){
	   		$frid=mysqli_fetch_array($frid);
	  		$frid=$frid['unique_id'];
  		}
 		$result = mysqli_query($this->con, "INSERT INTO friends(UserID,FriendID) VALUES('$myid','$frid')");
 
 		if($result){
 			$uid=mysqli_insert_id($this->con);  //上一次insert产生的auto imcremented id
 			$result=mysqli_query($this->con, "SELECT * FROM friends WHERE uid='$uid'"); //资源标识符
 			return mysqli_fetch_array($result);			
 		}
 		else{
 			return false;  //insert fail
 		}
 	}	
 	
 	
 	public function getfriendinfo($myemail){
 		
   		$myid=mysqli_query($this->con, "SELECT unique_id FROM users WHERE email='$myemail'");
    	if($myid){
	  		$myid=mysqli_fetch_array($myid);
	  		$myid=$myid['unique_id'];
  		}				
 		$result = mysqli_query($this->con, "SELECT FriendID FROM friends WHERE UserID = '$myid'"); 		
 		$friend_array1=mysqli_fetch_array($result,MYSQLI_ASSOC);
 		$result = mysqli_query($this->con, "SELECT UserID FROM friends WHERE FriendID = '$myid'"); 				
  		$friend_array2=mysqli_fetch_array($result,MYSQLI_ASSOC);
  		if($friend_array1!=NULL &&$friend_array2!=NULL)
  			$friend_array=array_merge($friend_array1, $friend_array2);	
  		else if($friend_array1!=NULL)
  			$friend_array=$friend_array1;
  		else if($friend_array2!=NULL)
  			$friend_array=$friend_array2;
  		else return false;
  		$friend_info=array();
  		foreach ($friend_array as $value){
    		$info=mysqli_query($this->con, "SELECT name, email FROM users WHERE unique_id='$value'"); 			
	  		if($info){
		  		$info=mysqli_fetch_array($info,MYSQLI_ASSOC);
				array_push($friend_info,$info);
  			}  			
  		}
  		return $friend_info;
 	}
 	
 	
 	
 	public function ishasfriendship($myemail,$friendemail){
   		$myid=mysqli_query($this->con, "SELECT unique_id FROM users WHERE email='$myemail'");
   		//从result获得元素 还有没有更好的办法？
  		if($myid){
	  		$myid=mysqli_fetch_array($myid);
	  		$myid=$myid['unique_id'];
  		}
  		$frid=mysqli_query($this->con, "SELECT unique_id FROM users WHERE email='$friendemail'");
  		if($frid){
	   		$frid=mysqli_fetch_array($frid);
	  		$frid=$frid['unique_id'];
  		}
  		
		$result = mysqli_query($this->con, "SELECT FriendID FROM friends WHERE UserID = '$myid'"); 		
 		if(($result->num_rows)>0){
 			$num=$result->num_rows;
 			$result=mysqli_fetch_array($result);
			$i=0;
			while ($i < $num) {
				if($result[$i]==$frid) return true;				
				$i++;
			}		
 		}
 		
 		$result = mysqli_query($this->con, "SELECT FriendID FROM friends WHERE UserID = '$frid'"); 		
 		if(($result->num_rows)>0){
 			$num=$result->num_rows;
 			$result=mysqli_fetch_array($result);
			$i=0;
			while ($i < $num) {
				if($result[$i]==$frid) return true;				
				$i++;
			}		
 		}
 		
 		return false;			
		
 	}
 	
 	

 	public function storeUser($name,$email,$password){
 		$uuid=uniqid('',true);
 		$hash=$this->hashSSHA($password);
 		$encrypted_password=$hash["encrypted"]; //encrypte 列存密码的hash值
 		$salt=$hash["salt"]; //salt 分开hash？ 
 		$result = mysqli_query($this->con, "INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at) VALUES('$uuid', '$name', '$email', '$encrypted_password', '$salt', NOW())");//他这样需要存salt，是否会对安全性造成影响？
 		if($result){
 			$uid=mysqli_insert_id($this->con);  //上一次insert产生的auto imcremented id
 			$result=mysqli_query($this->con, "SELECT * FROM users WHERE uid='$uid'"); //资源标识符
 			return mysqli_fetch_array($result);
 			
 		}
 		else{
 			return false;  //insert fail
 		}
 	}
 	
 	public function getUserByEmailAndPassword($email,$password){
 		$result=mysqli_query($this->con, "SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
 		$no_of_rows=$result->num_rows;
 		if($no_of_rows>0){
 			$result=mysqli_fetch_array($result);
 			$salt=$result['salt'];
 			$encrypted_password=$result['encrypted_password'];
 			$hash=$this->checkhashSSHA($salt,$password);
 			if($encrypted_password==$hash){
 				return $result;		
 			}   //应该加个else？？密码不对
 			else{
 				return false;
 			}
 			
 		}
 		 else{
 				return false;
 			}
 	}
 			
 	public function isUserExisted($email){
 		$result = mysqli_query($this->con, "SELECT email from users WHERE email = '$email'");
 		$no_of_rows=$result->num_rows;
 		if($no_of_rows>0){
 			return true;	
 		}
 		else{
 			return false;
 		}

 	}
 	
 	public function hashSSHA($password){
 		//salt是随机数，先sha1算法之后再base64 encode。。。
 		$salt=sha1(rand());
 		$salt=substr($salt,0,10);
 		$encrypted=base64_encode(sha1($password.$salt, true).$salt);
 		$hash=array("salt"=>$salt, "encrypted"=>$encrypted);//把salt也存起来？
 		return $hash;
 	}
 	
 	public function checkhashSSHA($salt, $password){
 		$hash = base64_encode (sha1($password.$salt,true).$salt);
 		return $hash;	
 	}
 	
 	
 }
 
 
 
 
 
 
?>
