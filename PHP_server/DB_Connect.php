<?php
/*
 * Created on 2012-7-22
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 class DB_Connect {
 
    // constructor
    function __construct() {
 
    }
 
    // destructor
    function __destruct() {
        // $this->close();
    }
 
    // Connecting to database
    public function connect() {
        require_once 'config.php';
        // connecting to mysql
        $con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
  		  if(!$con) { 
  			 die("Database connection failed: " . mysql_error()); 
			}
        // selecting database
        else{
    		$db_select=mysqli_select_db($con,DB_DATABASE);
    		 if (!$db_select) { 
     		  	die("Database selection failed: ". mysql_error()); 
  			 } 
        }
 
        // return database handler
        return $con;
    }
 
    // Closing database connection
    public function close() {
        mysql_close();
    }
 
}
 
 
?>
