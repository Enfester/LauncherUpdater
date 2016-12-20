<?php

if (isset($_GET['file'])) {
	print md5_file($_GET['file']);
}



?>